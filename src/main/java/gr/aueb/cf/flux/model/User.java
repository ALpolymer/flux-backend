package gr.aueb.cf.flux.model;

import gr.aueb.cf.flux.core.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String uuid;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ColumnDefault("false")
    private Boolean isDeleted;

    @Getter(AccessLevel.PROTECTED)
    @OneToMany(mappedBy = "user")
    private Set<Wallet> wallets = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @OneToMany(mappedBy = "user")
    private Set<Category> categories = new HashSet<>();

    @PrePersist
    public void initializeUUID() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
    }
}
