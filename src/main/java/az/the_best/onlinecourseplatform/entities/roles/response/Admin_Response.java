package az.the_best.onlinecourseplatform.entities.roles.response;

import az.the_best.onlinecourseplatform.entities.roles.Admin;
import az.the_best.onlinecourseplatform.entities.roles.request.Teacher_Request;
import az.the_best.onlinecourseplatform.security.Decision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin_Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Decision decision; // ACCEPTED / REJECTED

    @Column(columnDefinition = "TEXT")
    private String messageToTeacher; // reject edilirsə, səbəb

    private LocalDateTime decisionDate;


    @OneToOne
    @JoinColumn(name = "admin")
    private Admin admin_id; // bu qərarı verən adminin id-si

    @OneToOne
    @JoinColumn(name = "teacher_request_id")
    private Teacher_Request teacherRequestId;

    @PrePersist
    public void setDecisionDate() {
        this.decisionDate = LocalDateTime.now();
    }
}
