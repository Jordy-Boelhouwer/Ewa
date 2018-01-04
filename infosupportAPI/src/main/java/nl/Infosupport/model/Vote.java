/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author Jordy
 */
@Access(value=AccessType.FIELD)
@Entity
public class Vote implements Serializable {
    
    @Id
    @GeneratedValue
    private int id;
    
    private long voted;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profile;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    public Vote() {
    }

    public Vote(long voted) {
        this.voted = voted;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public void setPost(Post post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVoted() {
        return voted;
    }

    public void setVoted(long voted) {
        this.voted = voted;
    }
}
