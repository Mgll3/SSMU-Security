package co.udea.ssmu.api.model.jpa.dto.role;

import co.udea.ssmu.api.model.jpa.model.user.User;

import java.util.List;

public class RoleDTO {

    private Long id;

    private String name;


    //Constructors
    public RoleDTO() {
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
