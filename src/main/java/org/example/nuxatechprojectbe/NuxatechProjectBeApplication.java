package org.example.nuxatechprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.nuxatechprojectbe")
@EnableScheduling
public class NuxatechProjectBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuxatechProjectBeApplication.class, args);
    }

}
