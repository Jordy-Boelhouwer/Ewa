/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jordy
 */
public class Post implements Serializable {
    private int id;
    private String title;
    private String content;
    private Profile profile;
    private LocalDateTime dateTime;
    private String imagePath;
    private List<Comment> comments;
    //private int currentId = 0;
    Voter votes = new Voter();
    
    public Post(){};
    
    public Post(int id, String title, String content, Profile profile){
        setId(id);
        setTitle(title);
        setContent(content);
        setDateTime(dateTime);
        setComments(new ArrayList<Comment>());
    }
    
    public Post(String title, String content, String imagePath) {
        setId(id);
        setTitle(title);
        setContent(content);
        setDateTime(dateTime);
        setImagePath(imagePath);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getContent() {
        return content;
    }
    
    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public final void setId(int id) {
        //currentId++;
        this.id = id;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final void setContent(String content) {
        this.content = content;
    }

    private void setDateTime(LocalDateTime dateTime) {
        this.dateTime = LocalDateTime.now();
    } 

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public boolean addComment(Comment c){
        if(checkDuplicates(c)) {
            return false;
        }
        getComments().add(c);
        return true;
    }
    
    public boolean checkDuplicates(Comment c) {
        for(Comment check : getComments()) {
            if(check.getId() == c.getId()) {
                return true;
            }
        }
        return false;
    }

    public Voter getVotes() {
        return votes;
    }
}
