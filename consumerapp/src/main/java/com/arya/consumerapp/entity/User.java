package com.arya.consumerapp.entity;

import java.util.ArrayList;

public class User {

    public static String EXTRA_USER = "extra_user";

    private int id;
    private String type;
    private String login;
    private String name;
    private String location;
    private int public_repos;
    private String company;
    private int followers;
    private int following;
    private String avatar_url;
    private ArrayList<User> items = new ArrayList<>();

    public User(String login, String name, String location, String company, String avatar_url, int followers, int following, int public_repos) {
        this.login = login;
        this.name = name;
        this.location = location;
        this.public_repos = public_repos;
        this.company = company;
        this.followers = followers;
        this.following = following;
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getPublic_repos() {
        return public_repos;
    }
    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public int getFollowers() {
        return followers;
    }
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }
    public void setFollowing(int following) {
        this.following = following;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public ArrayList<User> getItems() {
        return items;
    }
    public void setItems(ArrayList<User> items) {
        this.items = items;
    }
}
