package com.arya.githubuserapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

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

    protected User(Parcel in) {
        login = in.readString();
        name = in.readString();
        location = in.readString();
        public_repos = in.readInt();
        company = in.readString();
        followers = in.readInt();
        following = in.readInt();
        avatar_url = in.readString();
        items = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public String toString() {
        return "{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", public_repos=" + public_repos +
                ", company='" + company + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeInt(public_repos);
        parcel.writeString(company);
        parcel.writeInt(followers);
        parcel.writeInt(following);
        parcel.writeString(avatar_url);
        parcel.writeTypedList(items);
    }
}
