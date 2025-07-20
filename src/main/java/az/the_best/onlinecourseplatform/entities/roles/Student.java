package az.the_best.onlinecourseplatform.entities.roles;

import az.the_best.onlinecourseplatform.entities.course.Comment;
import az.the_best.onlinecourseplatform.entities.course.Course;
import az.the_best.onlinecourseplatform.entities.course.Submission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "enrollments",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> enrolledCourses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Submission> submission;
}
