package az.the_best.onlinecourseplatform.starter;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "az.the_best.onlinecourseplatform")
@EntityScan(basePackages = "az.the_best.onlinecourseplatform")
@ComponentScan(basePackages = "az.the_best.onlinecourseplatform")
public class OnlineCoursePlatformApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
        System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));

        SpringApplication.run(OnlineCoursePlatformApplication.class, args);
    }

}
