package com.udea.ssmu.model;

import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private Date createdAt;
    private List<Role> rol;
}
