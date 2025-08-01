package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.iu.DTOChapterIU;
import az.the_best.onlinecourseplatform.dto.iu.DTOVideoIU;
import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.iu.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.course.Chapter;
import az.the_best.onlinecourseplatform.entities.course.Course;
import az.the_best.onlinecourseplatform.entities.course.Enrollment;
import az.the_best.onlinecourseplatform.entities.course.Video;
import az.the_best.onlinecourseplatform.entities.roles.Student;
import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.exception.BaseException;
import az.the_best.onlinecourseplatform.exception.ErrorMessage;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.repo.course.RestCourseRepo;
import az.the_best.onlinecourseplatform.repo.course.RestEnrollmentRepo;
import az.the_best.onlinecourseplatform.repo.role.RestStudentRepo;
import az.the_best.onlinecourseplatform.repo.role.RestTeacherRepo;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import az.the_best.onlinecourseplatform.security.RoleName;
import az.the_best.onlinecourseplatform.service.interfaces.IRestCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static az.the_best.onlinecourseplatform.entities.BaseEntity.notOk;
import static az.the_best.onlinecourseplatform.entities.BaseEntity.ok;

@Service
@RequiredArgsConstructor
public class RestCourseServiceIMPL implements IRestCourseService {

    private final RestCourseRepo restCourseRepo;

    private final RestUserRepo userRepo;

    private final RestTeacherRepo teacherRepo;

    private final RestStudentRepo studentRepo;

    private final RestEnrollmentRepo enrollmentRepo;


    private final CloudinaryServiceIMPL cloudinaryServiceIMPL;

    private final JWTService jwtService;


    @Override
    public BaseEntity<DTOCourse> addCourse(
            DTOCourseIU dtoCourseIU,
            MultipartFile coverPhoto,
            List<MultipartFile> videoFiles,
            String token
    ) {
        Long userId = jwtService.extractId(token);

        if (userId == null) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "null");
        }

        Optional<User> optionalDbUser = userRepo.findById(userId);
        if (optionalDbUser.isEmpty()) {
            return BaseEntity.notOk(MessageType.USER_NOT_FOUND, userId.toString());
        }

        Optional<Teacher> optionalUser = teacherRepo.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return BaseEntity.notOk(MessageType.TEACHER_NOT_FOUND, null);
        }

        Teacher teacher = optionalUser.get();

        Course course = new Course();
        course.setName(dtoCourseIU.getName());
        course.setDescription(dtoCourseIU.getDescription());

        String coverPhotoUrl = cloudinaryServiceIMPL.uploadImage(coverPhoto);
        course.setCoverPhoto(coverPhotoUrl);

        List<Chapter> chapters = new ArrayList<>();
        List<DTOChapterIU> chaptersIU = dtoCourseIU.getChapters();

        int videoIndex = 0;

        for (DTOChapterIU chapterIU : chaptersIU) {
            Chapter chapter = new Chapter();
            chapter.setTitle(chapterIU.getTitle());
            chapter.setOrder(chapterIU.getOrder());
            chapter.setCourse(course);

            List<Video> videos = new ArrayList<>();
            List<DTOVideoIU> dbVideos = chapterIU.getVideos();

            for (DTOVideoIU videoIU : dbVideos) {
                Video video = new Video();
                video.setTitle(videoIU.getTitle());
                video.setOrder(videoIU.getOrder());

                if (videoIndex < videoFiles.size()) {
                    MultipartFile file = videoFiles.get(videoIndex++);
                    String videoUrl = cloudinaryServiceIMPL.uploadVideo(file);
                    video.setUrl(videoUrl);
                } else {
                    return BaseEntity.notOk(MessageType.INTERNAL_SERVER_ERROR, "Video file mismatch");
                }

                video.setChapter(chapter);
                videos.add(video);
            }

            chapter.setVideos(videos);
            chapters.add(chapter);
        }

        course.setChapters(chapters);
        course.setTeacher(teacher);

        Course savedCourse = restCourseRepo.save(course);

        DTOCourse dtoCourse = new DTOCourse();
        BeanUtils.copyProperties(savedCourse, dtoCourse);

        return ok(dtoCourse);
    }

    @Override
    public BaseEntity<DTOCourse> getCourseById(Long id) {
        Course course = restCourseRepo.findById(id).orElse(null);
        DTOCourse dtoCourse = new DTOCourse();

        if(course == null) {
            return notOk(MessageType.NO_DATA_EXIST,id.toString());
        }

        BeanUtils.copyProperties(course, dtoCourse);

        List<DTOChapterIU> chapters = new ArrayList<>();

        for (Chapter chapter : course.getChapters()) {
            DTOChapterIU dtoChapter = new DTOChapterIU();
            BeanUtils.copyProperties(chapter, dtoChapter);

            List<DTOVideoIU> videos = new ArrayList<>();

            for (Video video : chapter.getVideos()) {
                DTOVideoIU dtoVideo = new DTOVideoIU();
                BeanUtils.copyProperties(video, dtoVideo);
                videos.add(dtoVideo);
            }

            dtoChapter.setVideos(videos);
            chapters.add(dtoChapter);
        }

        dtoCourse.setChapters(chapters);

        return ok(dtoCourse);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getCoursesByName(String name) {
        List<Course> courses = restCourseRepo.findByNameStartingWithIgnoreCase(name);

        if(courses.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,name));
        }

        List<DTOCourse> filteredCourses = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            filteredCourses.add(dtoCourse);
        }

        return ok(filteredCourses);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getTop5Courses() {
        List<Course> courses = restCourseRepo.findTop5ByOrderByClickCountDesc();
        List<DTOCourse> dtos = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dto = new DTOCourse();
            BeanUtils.copyProperties(course, dto);
            dtos.add(dto);
        }

        return ok(dtos);
    }

    @Override
    public BaseEntity<DTOCourse> editCourse(DTOCourseIU dtoCourseIU, MultipartFile file, Long id) {
        Course course = restCourseRepo.findById(id).orElse(null);

        if(course != null) {
            BeanUtils.copyProperties(dtoCourseIU, course);
            restCourseRepo.save(course);

            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            return ok(dtoCourse);
        }

        return notOk(MessageType.NO_DATA_EXIST,id.toString());
    }

    @Override
    public void deleteCourse(Long id) {
        restCourseRepo.deleteById(id);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getAllCourses() {
        List<Course> courses = restCourseRepo.findAll();

        List<DTOCourse> coursesDTO = new ArrayList<>(courses.size());

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            coursesDTO.add(dtoCourse);
        }
        return ok(coursesDTO);
    }

    @Override
    public void increaseClickCount(Long courseId) {
        restCourseRepo.increaseClickCount(courseId);
    }

    @Override
    public BaseEntity<String> getToEnroll(Long courseId, String token) {
        if (token == null || token.isBlank()) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "Token is missing or empty");
        }

        Long userId = jwtService.extractId(token);

        if (userId == null) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "Token is not correct");
        }

        Optional<User> dbUser = userRepo.findById(userId);
        User user;

        if (dbUser.isEmpty()) {
            return BaseEntity.notOk(MessageType.USER_NOT_FOUND, userId.toString());
        } else {
            user = dbUser.get();
        }

        Enrollment enrollment = new Enrollment();

        Course course = restCourseRepo.findById(courseId).orElse(null);

        if(course == null) {
            return BaseEntity.notOk(MessageType.NO_DATA_EXIST, courseId.toString());
        } else {
            enrollment.setCourse(course);
        }

        Student student = studentRepo.findByUserId(userId).orElse(null);

        if (student == null) {
            Student newStudent = new Student();

            newStudent.setUser(user);
            studentRepo.save(newStudent);

            enrollment.setStudent(newStudent);
        } else {
            //check if enrollment exists
            if (enrollmentRepo.existsByStudentIdAndCourseId(student.getId(), courseId)) {
                return BaseEntity.notOk(MessageType.ALREADY_EXIST, "You Already Enrolled");
            }
            enrollment.setStudent(student);
        }


        enrollmentRepo.save(enrollment);
        return BaseEntity.ok("Success");
    }
}
