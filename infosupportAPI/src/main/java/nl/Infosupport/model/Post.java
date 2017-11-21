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
    private String username;
    private LocalDateTime dateTime;
    private List<Comment> comments;
    //private int currentId = 0;
    //Voter votes = new Voter();
    private int votes;
    private boolean voted;
    
    public Post(){};
    
    public Post(int id, String title, String content, String username){
        setId(id);
        setTitle(title);
        setContent(content);
        setDateTime(dateTime);
        setComments(new ArrayList<Comment>());
        setUsername(username);
        setVotes();
        setHasVoted();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }
    
    public LocalDateTime getDateTime(){
        return dateTime;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes() {
        votes = 0;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setHasVoted() {
        this.voted = false;
    }
    
    public boolean addComment(Comment c){
        if(checkDuplicates(c)) {
            return false;
        }
        getComments().add(c);
        return true;
    }
    
    public void upVote(){
        if(!voted){
            votes++;
            voted = true;
        }
    }
    
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
    
    public boolean checkDuplicates(Comment c) {
        for(Comment check : getComments()) {
            if(check.getId() == c.getId()) {
                return true;
            }
        }
        return false;
    }
}
