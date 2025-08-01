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

        System.setProperty("MYSQL_URL", dotenv.get("MYSQL_URL"));
        System.setProperty("MYSQL_NAME", dotenv.get("MYSQL_NAME"));
        System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));

        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));

        System.setProperty("EMAIL_HOST", dotenv.get("EMAIL_HOST"));
        System.setProperty("EMAIL_PORT", dotenv.get("EMAIL_PORT"));
        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));

        SpringApplication.run(OnlineCoursePlatformApplication.class, args);
    }

}
