package az.the_best.onlinecourseplatform.entities;

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

    @ManyToOne
    @JoinColumn(name = "course", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user", insertable = false, updatable = false)
    private User user;
}
