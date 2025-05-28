package az.the_best.onlinecourseplatform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "click_view", nullable = false)
    private int clickView = 0;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "user", insertable = false, updatable = false)
    private User user;
}