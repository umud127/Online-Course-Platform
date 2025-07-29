package az.the_best.onlinecourseplatform.entities.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "videos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @Column(name = "`order`")
    private int order;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
