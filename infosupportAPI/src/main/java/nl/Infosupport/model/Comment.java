/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Jordy
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id")
    private List<SubComment> subComments;
    
    /**
     * No argument constructor for comment
     */
    public Comment(){};
    
    /**
     * Constructor for comment
     * @param content
     */
    public Comment(String content){
        setContent(content);
    }

    /**
     * Get the post the comment is for
     * @return post
     */
    public Post getPost() {
        return post;
    }

    /**
     * Set the post the comment is for
     * @param post
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Get the id from the comment
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the content from the comment
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the id for the comment
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Set the content for the comment
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public List<SubComment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<SubComment> subComments) {
        this.subComments = subComments;
    }
    
    public void addSubComment(SubComment s){
        s.setComment(this);
        getSubComments().add(s);
    }
}
