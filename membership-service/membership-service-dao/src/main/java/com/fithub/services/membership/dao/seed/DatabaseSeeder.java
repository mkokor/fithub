package com.fithub.services.membership.dao.seed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.ClientRepository;
import com.fithub.services.membership.dao.repository.CoachRepository;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ClientRepository clientRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        UserEntity coachUserEntity = new UserEntity();
        UserEntity clientUserEntity = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            coachUserEntity.setUuid("john-doe-coach");
            coachUserEntity.setFirstName("John");
            coachUserEntity.setLastName("Doe");
            coachUserEntity.setUsername("johndoe");
            coachUserEntity.setEmail("johndoe@email.com");
            userRepository.save(coachUserEntity);

            clientUserEntity.setUuid("mary-ann-client");
            clientUserEntity.setFirstName("Mary");
            clientUserEntity.setLastName("Ann");
            clientUserEntity.setUsername("maryann");
            clientUserEntity.setEmail("maryann@email.com");
            userRepository.save(clientUserEntity);
        }

        CoachEntity coachEntity = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachEntity.setUser(coachUserEntity);
            coachEntity.setBiography("John Doe is the head coach.");
            coachRepository.save(coachEntity);
        }

        ClientEntity clientEntity = new ClientEntity();
        if (clientRepository.findAll().isEmpty()) {
            clientEntity.setUser(clientUserEntity);
            clientEntity.setCoach(coachEntity);
            clientRepository.save(clientEntity);
        }

        MembershipEntity membershipEntity = new MembershipEntity();
        if (membershipRepository.findAll().isEmpty()) {
            membershipEntity.setAmount(10000);
            membershipEntity.setClient(clientEntity);
            membershipRepository.save(membershipEntity);
        }

        PaymentRecordEntity jaunaryPaymentRecordEntity = new PaymentRecordEntity();
        PaymentRecordEntity februaryPaymentRecordEntity = new PaymentRecordEntity();
        if (paymentRecordRepository.findAll().isEmpty()) {
            jaunaryPaymentRecordEntity.setId(1L);
            jaunaryPaymentRecordEntity.setYear(2024);
            jaunaryPaymentRecordEntity.setMonth(Month.JANUARY.toString());
            jaunaryPaymentRecordEntity.setPaymentDate(LocalDateTime.now());
            jaunaryPaymentRecordEntity.setPaid(true);
            jaunaryPaymentRecordEntity.setMembership(membershipEntity);
            paymentRecordRepository.save(jaunaryPaymentRecordEntity);

            februaryPaymentRecordEntity.setId(2L);
            februaryPaymentRecordEntity.setYear(2024);
            februaryPaymentRecordEntity.setMonth(Month.FEBRUARY.toString());
            februaryPaymentRecordEntity.setPaid(false);
            februaryPaymentRecordEntity.setMembership(membershipEntity);
            paymentRecordRepository.save(februaryPaymentRecordEntity);

            List<PaymentRecordEntity> records = new ArrayList<>();
            records.add(jaunaryPaymentRecordEntity);
            records.add(februaryPaymentRecordEntity);
            membershipEntity.setPaymentRecord(records);
        }
    }

}