/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author Jordy
 */
@Entity
public class Profile implements Serializable {

    @Id
    private String id;

    private String name;

    private String bio;

    private String date_joined;

    private String job;
    
    private String access_token;
    
    private String image;

    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="profile")
    @JsonManagedReference 
    private List<Post> posts;
    
    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="profile")
    @JsonManagedReference 
    private List<Vote> votes;
    
    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="profile")
    @JsonManagedReference 
    private List<Comment> comments;
    
    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="profile")
    @JsonManagedReference 
    private List<SubComment> subComments;

    public Profile() {
    }

    /**
     *
     * @param id id of the user
     * @param name name of the user
     * @param access_token access token provided by Slack
     * @param date_joined Date the profile was created
     */
    public Profile(String id, String name,
            String date_joined, 
            String access_token,
            String image) {
        setId(id);
        setName(name);
        setDate_joined(date_joined);
        setAccess_token(access_token);
        setImage(image);
        setPosts(new ArrayList<Post>());
        setVotes(new ArrayList<Vote>());
        setComments(new ArrayList<Comment>());
        setSubComments(new ArrayList<SubComment>());
    }

    /**
     * Get the id for the profile
     *
     * @return id of profile
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id for the profile
     *
     * @param id id for profile
     */
    public void setId(String id) {
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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<SubComment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<SubComment> subComments) {
        this.subComments = subComments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String profileImage) {
        this.image = profileImage;
    }
    
    /**
     * Add the post to the list
     *
     * @param p The post to be added
     */
    public void addPost(Post p) {
        this.posts.add(p);
        p.setProfile(this);
    }  
    
    public void addProfileVote(Vote v) {
        this.votes.add(v);
        v.setProfile(this);
    }
    
    public void addComment(Comment c) {
        this.comments.add(c);
        c.setProfile(this);
    }
    
    public void addSubComment(SubComment s) {
        this.subComments.add(s);
        s.setProfile(this);
    }
}
