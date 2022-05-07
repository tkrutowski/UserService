package net.focik.userservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrivilegeDto {
    String name;
    String value;
}
