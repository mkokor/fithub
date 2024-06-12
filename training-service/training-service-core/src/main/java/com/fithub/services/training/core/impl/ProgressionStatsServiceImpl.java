package com.fithub.services.training.core.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.ProgressionStatsService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.UnauthorizedException;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsPageable;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ProgressionStatsEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.ProgressionStatsRepository;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.ProgressionStatsMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressionStatsServiceImpl implements ProgressionStatsService {

    private final ProgressionStatsRepository progressionStatsRepository;
    private final UserRepository userRepository;
    private final ProgressionStatsMapper progressionStatsMapper;
    private final Validator validator;

    private UserEntity getAuthenticatedUser() {
        return UserContext.getCurrentContext().getUser();
    }

    private ClientEntity validateClientCoachRelationship(final String clientUuid) throws ApiException {
        final Optional<UserEntity> clientUser = userRepository.findById(clientUuid);
        if (clientUser.isEmpty()) {
            throw new NotFoundException("The client with the provided UUID could not be found.");
        }

        if (Objects.nonNull(clientUser.get().getCoach())) {
            throw new BadRequestException("The provided UUID is associated with coach user.");
        }

        final ClientEntity clientEntity = clientUser.get().getClient();
        final UserEntity authenticatedUser = getAuthenticatedUser();
        if (!clientEntity.getCoach().getUser().getUuid().equals(authenticatedUser.getUuid())) {
            throw new UnauthorizedException("The requested client and the authenticated coach are not associated.");
        }

        return clientEntity;
    }

    @Override
    public ProgressionStatsResponse create(ProgressionStatsCreateRequest progressionStatCreateRequest) throws Exception {
        Set<ConstraintViolation<ProgressionStatsCreateRequest>> violations = validator.validate(progressionStatCreateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final CoachEntity authenticatedCoach = getAuthenticatedUser().getCoach();
        final ClientEntity clientEntity = validateClientCoachRelationship(progressionStatCreateRequest.getClientUuid());

        ProgressionStatsEntity progressionStatsEntity = progressionStatsMapper
                .progressionStatCreateRequestToEntity(progressionStatCreateRequest);
        progressionStatsEntity.setClient(clientEntity);
        progressionStatsEntity.setCreatedAt(LocalDateTime.now());
        progressionStatsEntity.setCreatedBy(authenticatedCoach);

        progressionStatsRepository.save(progressionStatsEntity);

        return progressionStatsMapper.entityToDto(progressionStatsEntity);
    }

    @Override
    public List<ProgressionStatsResponse> getProgressionStats(String clientUuid) throws ApiException {
        validateClientCoachRelationship(clientUuid);

        final List<ProgressionStatsEntity> progressionStatsEntities = progressionStatsRepository.findByClientUuid(clientUuid);
        return progressionStatsMapper.entitiesToDtos(progressionStatsEntities);
    }

    @Override
    public List<ProgressionStatsResponse> getRangList(ProgressionStatsPageable progressionStatsPageable) throws ApiException {
        if (!("createdAt".equals(progressionStatsPageable.getSortFilter()) || "benchPr".equals(progressionStatsPageable.getSortFilter())
                || "treadmillPr".equals(progressionStatsPageable.getSortFilter())
                || "squatPr".equals(progressionStatsPageable.getSortFilter())
                || "deadliftPm".equals(progressionStatsPageable.getSortFilter()))) {
            throw new BadRequestException("The sort filter is invalid.");
        }

        PageRequest pageable = PageRequest.of(progressionStatsPageable.getPageNumber(), progressionStatsPageable.getPageSize(),
                Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc(progressionStatsPageable.getSortFilter())));

        List<ProgressionStatsEntity> progressionStatsEntities = progressionStatsRepository.findByDistinctClientIds(pageable);
        return progressionStatsMapper.entitiesToDtos(progressionStatsEntities);
    }

    @Override
    public ProgressionStatsResponse getLatestStats() throws ApiException {
        final UserEntity clientUser = getAuthenticatedUser();

        List<ProgressionStatsEntity> progressionStatsEntity = progressionStatsRepository.findLatestByClientUuid(clientUser.getUuid());

        if (progressionStatsEntity.isEmpty()) {
            throw new NotFoundException("The client does not have any progression stats yet.");
        }

        return progressionStatsMapper.entityToDto(progressionStatsEntity.get(0));
    }

    @Override
    public void generateExcelFromProgressionStats(HttpServletResponse response) throws IOException {
        final ClientEntity client = getAuthenticatedUser().getClient();
        final List<ProgressionStatsEntity> progressionStatsList = progressionStatsRepository.findByClientUuid(client.getUser().getUuid());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Progression Stats");

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Created At");
        headerRow.createCell(2).setCellValue("Created By");
        headerRow.createCell(3).setCellValue("Weight");
        headerRow.createCell(4).setCellValue("Height");
        headerRow.createCell(5).setCellValue("Deadlift PR");
        headerRow.createCell(6).setCellValue("Squat PR");
        headerRow.createCell(7).setCellValue("Bench PR");
        headerRow.createCell(8).setCellValue("Treadmill PR");

        int rowNum = 1;
        for (ProgressionStatsEntity stats : progressionStatsList) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stats.getId());
            row.createCell(1).setCellValue(stats.getCreatedAt().format(dateFormatter));
            row.createCell(2)
                    .setCellValue(stats.getCreatedBy().getUser().getFirstName() + " " + stats.getCreatedBy().getUser().getLastName());
            row.createCell(3).setCellValue(stats.getWeight());
            row.createCell(4).setCellValue(stats.getHeight());
            row.createCell(5).setCellValue(stats.getDeadliftPr());
            row.createCell(6).setCellValue(stats.getSquatPr());
            row.createCell(7).setCellValue(stats.getBenchPr());
            row.createCell(8).setCellValue(stats.getTreadmillPr());
        }

        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=progression-stats.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}