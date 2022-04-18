package net.focik.userservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;
    private Date joinDate;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    @Column(name = "is_not_locked")
    private boolean notLocked;
//    private boolean isNotLocked;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
//    @JoinTable(
//            name = "users_privileges",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;


    public void addPrivilege(Privilege privilege){
        this.privileges.add(privilege);
    }

    public void deletePrivilege(Privilege privilege) {
        privileges.remove(privilege);
    }
}
