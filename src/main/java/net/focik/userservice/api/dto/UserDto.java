package net.focik.userservice.api.dto;

import lombok.*;
import net.focik.userservice.domain.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private Date joinDate;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
//    private boolean isNotLocked;
    private boolean notLocked;


}
