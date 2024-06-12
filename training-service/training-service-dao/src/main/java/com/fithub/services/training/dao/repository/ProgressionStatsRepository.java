package com.fithub.services.training.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.services.training.dao.model.ProgressionStatsEntity;

@Repository
public interface ProgressionStatsRepository extends JpaRepository<ProgressionStatsEntity, Long> {

    @Query("SELECT ps FROM ProgressionStatsEntity ps WHERE ps.client.user.uuid = :clientUuid ORDER BY ps.createdAt DESC")
    List<ProgressionStatsEntity> findByClientUuid(@Param("clientUuid") final String clientUuid);

    @Query("SELECT ps FROM ProgressionStatsEntity ps WHERE ps.client.user.uuid = :clientUuid ORDER BY ps.createdAt DESC")
    List<ProgressionStatsEntity> findLatestByClientUuid(@Param("clientUuid") final String clientUuid);

    @Query("SELECT ps FROM ProgressionStatsEntity ps WHERE ps.id IN "
            + "(SELECT MAX(ps1.id) FROM ProgressionStatsEntity ps1 GROUP BY ps1.client.id) " + "ORDER BY ps.createdAt DESC")
    List<ProgressionStatsEntity> findByDistinctClientIds(Pageable pageable);

}