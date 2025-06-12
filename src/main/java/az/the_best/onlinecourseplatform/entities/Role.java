package az.the_best.onlinecourseplatform.entities;

import az.the_best.onlinecourseplatform.security.RoleName;
import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleName name;
}
