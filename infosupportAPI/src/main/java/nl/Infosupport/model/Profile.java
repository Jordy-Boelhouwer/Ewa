/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Jordy
 */
@Entity
public class Profile implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String bio;

    private String date_joined;

    private String job;
    
    private String access_token;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private List<Post> posts;

    public Profile() {
    }

    /**
     *
     * @param name name of the user
     * @param access_token access token provided by Slack
     * @param date_joined Date the profile was created
     */
    public Profile(String name,
            String date_joined, 
            String access_token) {
        setName(name);
        setDate_joined();
        setAccess_token(access_token);
        setPosts(new ArrayList<Post>());
    }

    /**
     * Get the id for the profile
     *
     * @return id of profile
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id for the profile
     *
     * @param id id for profile
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the bio of the profile
     *
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set the bio of the profile
     *
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Get the date the profile was created
     *
     * @return
     */
    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.date_joined = dateFormat.format(date);
    }

    /**
     * Get the job of the profile
     *
     * @return job
     */
    public String getJob() {
        return job;
    }

    /**
     * Set the job of the profile
     *
     * @param job
     */
    public void setJob(String job) {
        this.job = job;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get posts from profile
     *
     * @return posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Set the list of posts
     *
     * @param posts The list op posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * Add the post to the list
     *
     * @param p The post to be added
     */
    public void addPost(Post p) {
        getPosts().add(p);
        p.setProfile(this);
    }    
}
