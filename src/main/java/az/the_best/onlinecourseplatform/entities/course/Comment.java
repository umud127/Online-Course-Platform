package az.the_best.onlinecourseplatform.entities.course;

import az.the_best.onlinecourseplatform.entities.roles.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "video_id")
    private Long video;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;
}
