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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Jordy
 */
@Entity
public class Post implements Serializable {
    @Column(name = "post_id")
    @Id
    @GeneratedValue
    private int id;
    
    private String title;
    
    private String content;
    
    private int votes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private Profile profile;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;
    //private int currentId = 0;
    //Voter votes = new Voter();
   
    @Transient
    private boolean voted;
    
    /**
     * No argument post constructor
     */
    public Post(){};
    
    /**
     * Post constructor
     * @param title
     * @param content
     */
    public Post(String title, String content){
        setTitle(title);
        setContent(content);
        setComments(new ArrayList<Comment>());
        setVotes();
    }

    /**
     * Set profile for post
     * @param profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Get profile which posted the post
     * @return profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Get id from post
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get title of post
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get content of post
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set id for post
     * @param id
     */
    public void setId(int id) {
        //currentId++;
        this.id = id;
    }

    /**
     * Set title for post
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set content for post
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get list of comments from post
     * @return comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Set the list of comments for post
     * @param comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Get the votes from a profile
     * @return votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Set the votes for a profile
     */
    public void setVotes() {
        votes = 0;
    }

    /**
     * Check if profile has already voted
     * @return voted
     */
    public boolean isVoted() {
        return voted;
    }
    
    /**
     * Add a comment to the post
     * @param c Comment to be added 
     */
    public void addComment(Comment c){
        c.setPost(this);
        getComments().add(c);
    }
    
    /**
     * Add a upcote to a post
     */
    public void upVote(){
        if(!voted){
            votes++;
            voted = true;
        }
    }
    
    /**
     * Add a downvote to a post
     */
    public void downVote(){
        if(!voted){
            if(votes == 0){
            votes = 0;
        } else {
            votes--;
            voted = true;
        }
        }
    }
}
