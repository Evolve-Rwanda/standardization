package com.example.springauth.dialects.postgres;


public record DatabaseCredentials(String url, String username, String password){
    public String getUrl(){
        return this.url;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
}