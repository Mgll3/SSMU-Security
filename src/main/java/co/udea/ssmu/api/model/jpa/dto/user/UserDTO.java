package co.udea.ssmu.api.model.jpa.dto.user;

import co.udea.ssmu.api.model.jpa.model.role.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {


    private String email;
    private String password;
    private List<Role> roles;

    // Constructors
    public UserDTO() {
    }

    public UserDTO(String email, String password, List<Role> roles) {

        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO(String email,  String password) {

        this.email = email;
        this.password = password;
    }


    // Getters and setters



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
