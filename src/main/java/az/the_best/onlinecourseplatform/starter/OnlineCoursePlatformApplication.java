package az.the_best.onlinecourseplatform.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "az.the_best.onlinecourseplatform")
@EntityScan(basePackages = "az.the_best.onlinecourseplatform")
@ComponentScan(basePackages = "az.the_best.onlinecourseplatform")
public class OnlineCoursePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineCoursePlatformApplication.class, args);
    }

}
