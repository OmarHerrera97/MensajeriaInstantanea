package com.uisrael.mensajeriainstantanea.Models;

public class User {
    private String id = "dddsfsdfsd";
    //private String id ="IAQijEFIJVVJjwXwMk8tXgpWNyL2";
    private String  username;
    private String imageURL;
    private String status;

    public User(String id, String username, String imageURL, String status) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.imageURL = imageURL;
    }

    public User(){

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
