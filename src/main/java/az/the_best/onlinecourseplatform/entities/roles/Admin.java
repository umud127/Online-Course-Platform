package az.the_best.onlinecourseplatform.entities.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, name = "boss_name")
    private String boss_name;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
