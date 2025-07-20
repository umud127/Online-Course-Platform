package az.the_best.onlinecourseplatform.entities.course;

import az.the_best.onlinecourseplatform.entities.roles.Student;
import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cover_photo")
    private String coverPhoto;

    @Column(name = "click_count", nullable = false)
    private BigInteger clickCount = BigInteger.valueOf(0);


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<Student> enrolledStudent;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Submission> submission;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}