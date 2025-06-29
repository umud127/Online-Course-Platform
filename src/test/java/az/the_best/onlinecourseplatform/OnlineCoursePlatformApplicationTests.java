package az.the_best.onlinecourseplatform;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.service.CourseService;
import az.the_best.onlinecourseplatform.starter.OnlineCoursePlatformApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {OnlineCoursePlatformApplication.class})
class CourseServiceTests {

    @Autowired
    CourseService courseService;

    @BeforeAll
    public static void setup(){
        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("MYSQL_URL", dotenv.get("MYSQL_URL"));
        System.setProperty("MYSQL_NAME", dotenv.get("MYSQL_NAME"));
        System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));

        System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
        System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));

        System.out.println("Setup is Completed");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Testing is started");
        System.out.println();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testCourseName(){
        BaseEntity<DTOCourse> baseEntity = courseService.getCourseById(2L);
        assertEquals("delphin",baseEntity.getData().getName());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L,4L,5L})
    public void testGetCourseById(Long id){
        BaseEntity<DTOCourse> baseEntity = courseService.getCourseById(id);

        if(baseEntity!=null){
            System.out.println(baseEntity.getData());
        }
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("Testing is finished");
    }
}
