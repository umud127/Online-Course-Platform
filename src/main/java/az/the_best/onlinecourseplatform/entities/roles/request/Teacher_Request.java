package az.the_best.onlinecourseplatform.entities.roles.request;

import az.the_best.onlinecourseplatform.entities.roles.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher_Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl; // Cloudinary URL və ya file name

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String messageToAdmin;

    private String subjects;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"teacherRequest", "hibernateLazyInitializer", "handler"})
    private User user;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
