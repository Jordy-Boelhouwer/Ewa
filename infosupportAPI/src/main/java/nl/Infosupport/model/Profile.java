/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    private String first_name;

    private String last_name;

    private String bio;

    private String date_joined;

    private String job;

    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private List<Post> posts;

    public Profile() {
    }

    public Profile(String first_name, String last_name, String username,
            String password) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        setFirst_name(first_name);
        setLast_name(last_name);
        setDate_joined();
        setUsername(username);
        HashFunction hash = new HashFunction(password);
        setPassword(hash.getGeneratedSecuredPasswordHash());
        setPosts(new ArrayList<Post>());
    }

    /**
     *
     * @param first_name First name
     * @param last_name Last name
     * @param bio biography of profile
     * @param date_joined Date the profile was created
     * @param job Job description of profile
     * @param username Username for login
     * @param password Password for login
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public Profile(String first_name, String last_name, String bio,
            String date_joined, String job, String username, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        setFirst_name(first_name);
        setLast_name(last_name);
        setBio(bio);
        setDate_joined();
        setJob(job);
        setUsername(username);
        HashFunction hash = new HashFunction(password);
        setPassword(hash.getGeneratedSecuredPasswordHash());
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
     * Get the first name from profile
     *
     * @return First name of profile
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Set first name for profile
     *
     * @param first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Get last name from profile
     *
     * @return last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Set last name for profile
     *
     * @param last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    /**
     * Get username for profile
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username for profile
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password from profile
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password for profile
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
