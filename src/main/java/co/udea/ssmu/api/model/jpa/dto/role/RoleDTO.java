package co.udea.ssmu.api.model.jpa.dto.role;

import co.udea.ssmu.api.model.jpa.model.user.User;

import java.util.List;

public class RoleDTO {

    private Long id;

    private String name;
    private List<User> users;

    //Constructors
    public RoleDTO() {
    }

    public RoleDTO(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public RoleDTO(Long id) {
        this.id = id;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
