package com.example.springauth.models.jpa;


import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.TableNameGiver;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name=TableNameGiver.userTableName, schema=SchemaNameGiver.userManagement)
public class AppUser {


    @Id
    private String id;
    private String username;
    private String password;


    public AppUser() {
    }

    public AppUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
