package com.fithub.services.membership.dao.seed;

import java.util.UUID;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.repository.CoachRepository;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.dao.repository.ClientRepository;

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
        UserEntity userEntity = new UserEntity();
        UserEntity userEntity1 = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
        	
            userEntity.setUuid(UUID.randomUUID().toString());
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");
            userRepository.save(userEntity);
            
            userEntity1.setUuid(UUID.randomUUID().toString());
            userEntity1.setFirstName("Johnny");
            userEntity1.setLastName("Doe");
            userRepository.save(userEntity1);
        }
        

        CoachEntity coachEntity = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachEntity.setUser(userEntity);
            coachEntity.setBiography("John Doe is the head coach.");
            coachRepository.save(coachEntity);
        }
        
        ClientEntity clientEntity = new ClientEntity();
        if (clientRepository.findAll().isEmpty()) {
            clientEntity.setUser(userEntity1);
            clientEntity.setCoach(coachEntity);
            clientRepository.save(clientEntity);
        }
        
        MembershipEntity membershipEntity = new MembershipEntity();
        if (membershipRepository.findAll().isEmpty()) {
        	membershipEntity.setAmount(10000);
        	membershipEntity.setClient(clientEntity);
            membershipRepository.save(membershipEntity);
        }
        
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
        if (paymentRecordRepository.findAll().isEmpty()) {
        	paymentRecordEntity.setPaid(false);
        	paymentRecordEntity.setMembership(membershipEntity);
            paymentRecordRepository.save(paymentRecordEntity);
        }
        
    }
}