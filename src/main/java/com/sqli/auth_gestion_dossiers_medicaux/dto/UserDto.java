package com.sqli.auth_gestion_dossiers_medicaux.dto;

import com.sqli.auth_gestion_dossiers_medicaux.model.Role;
import com.sqli.auth_gestion_dossiers_medicaux.model.Site;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String site ;
    private String name;
    private String businessTitle;
    private Role role;

    public User getUserFromDto(){
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        user.setSite(Site.valueOf(site));
        user.setBusinessTitle(businessTitle);
        user.setRole(role);
        return user;
    }

}
