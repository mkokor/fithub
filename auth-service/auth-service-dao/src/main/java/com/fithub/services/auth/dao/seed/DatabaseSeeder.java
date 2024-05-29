package com.fithub.services.auth.dao.seed;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        if (userRepository.findAll().isEmpty()) {
            UserEntity coachJohnUserEntity = new UserEntity();
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setFirstName("John");
            coachJohnUserEntity.setLastName("Doe");
            coachJohnUserEntity.setUsername("johndoe");
            coachJohnUserEntity.setEmail("johndoe@fithub.com");
            coachJohnUserEntity.setEmailConfirmed(true);

            String passwordHash = BCrypt.hashpw("password123#", BCrypt.gensalt());
            coachJohnUserEntity.setPasswordHash(passwordHash);

            userRepository.save(coachJohnUserEntity);

            CoachEntity coachJohnEntity = new CoachEntity();
            coachJohnEntity.setBiography(
                    "John Doe is a dedicated and experienced fitness trainer with over a decade of experience in helping clients achieve their health and fitness goals. With certifications from the National Academy of Sports Medicine (NASM) and the National Strength and Conditioning Association (NSCA), John combines his extensive knowledge with a passion for fitness to create personalized, effective, and safe training programs.");
            coachJohnEntity.setClientCapacity(3);
            coachJohnEntity.setImagePath("/auth-service/images/coach/john-doe-coach.jpg");
            coachJohnEntity.setUser(coachJohnUserEntity);

            coachRepository.save(coachJohnEntity);

            UserEntity coachMaryUserEntity = new UserEntity();
            coachMaryUserEntity.setUuid("mary-ann-coach");
            coachMaryUserEntity.setFirstName("Mary");
            coachMaryUserEntity.setLastName("Ann");
            coachMaryUserEntity.setUsername("maryann");
            coachMaryUserEntity.setEmail("maryann@fithub.com");
            coachMaryUserEntity.setEmailConfirmed(true);

            passwordHash = BCrypt.hashpw("password123#", BCrypt.gensalt());
            coachMaryUserEntity.setPasswordHash(passwordHash);

            userRepository.save(coachMaryUserEntity);

            CoachEntity coachMaryEntity = new CoachEntity();
            coachMaryEntity.setBiography(
                    "Mary Ann is a passionate and experienced fitness trainer dedicated to helping clients achieve their health and fitness goals. With over a decade of experience in the industry, she combines her deep knowledge of exercise science with a motivational coaching style that inspires clients to push beyond their limits. Mary Ann specializes in weight loss, strength training, and functional fitness, creating personalized programs that are both effective and enjoyable. Her holistic approach to fitness ensures clients not only reach their physical goals but also improve their overall well-being. Mary Ann's supportive nature and expertise have made her a trusted guide for many on their fitness journeys.");
            coachMaryEntity.setClientCapacity(3);
            coachMaryEntity.setImagePath("/auth-service/images/coach/mary-ann-coach.jpg");
            coachMaryEntity.setUser(coachMaryUserEntity);

            coachRepository.save(coachMaryEntity);

            UserEntity coachJamesUserEntity = new UserEntity();
            coachJamesUserEntity.setUuid("james-martin-coach");
            coachJamesUserEntity.setFirstName("James");
            coachJamesUserEntity.setLastName("Martin");
            coachJamesUserEntity.setUsername("jamesmartin");
            coachJamesUserEntity.setEmail("jamesmartin@fithub.com");
            coachJamesUserEntity.setEmailConfirmed(true);

            passwordHash = BCrypt.hashpw("password123#", BCrypt.gensalt());
            coachJamesUserEntity.setPasswordHash(passwordHash);

            userRepository.save(coachJamesUserEntity);

            CoachEntity coachJamesEntity = new CoachEntity();
            coachJamesEntity.setBiography(
                    "James Martin is a dedicated and experienced fitness trainer with a passion for helping clients achieve their health and fitness goals. With over a decade in the industry, James has developed a reputation for his personalized approach and motivational coaching style. He specializes in weight loss, strength training, and functional fitness, ensuring that each program is tailored to the individual needs of his clients. James believes in a holistic approach to fitness, focusing on physical health, proper nutrition, and mental well-being. His supportive nature and expertise have helped many clients transform their lives and achieve lasting results.");
            coachJamesEntity.setClientCapacity(20);
            coachJamesEntity.setImagePath("/auth-service/images/coach/james-martin-coach.jpg");
            coachJamesEntity.setUser(coachJamesUserEntity);

            coachRepository.save(coachJamesEntity);
        }
    }

}