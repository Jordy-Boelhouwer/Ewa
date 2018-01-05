/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jordy
 */
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    private String title;
    
    private String content;
    
    @Lob
    @Column(columnDefinition="longblob")
    private byte[] image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profile;
    
    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="post")
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private Set<Comment> comments;
    
    @OneToMany(cascade = CascadeType.ALL, 
            orphanRemoval = true, 
            fetch = FetchType.EAGER,
            mappedBy="post")
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference 
    private Set<Vote> votes;
    
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
        setComments(new HashSet<Comment>());
        setVotes(new HashSet<Vote>());
    }

    public Post(String title, String content, byte[] image) {
        setTitle(title);
        setContent(content);
        setImage(image);
        setVotes(new HashSet<Vote>());
    }

    /**
     * Set profile for post
     * @param profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
    
    /**
     * Add a comment to the post
     * @param c Comment to be added 
     */
    public void addComment(Comment c){
        c.setPost(this);
        this.comments.add(c);
    }
    
    public void addPostVote(Vote v) {
        v.setPost(this);
        this.votes.add(v);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        return this.id == other.id;
    }
    
    
}
