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
public class Comment implements Serializable {
    private int id;
    private String content;
    private LocalDateTime dateTime;
    private int currentId = 0;
    private List<Comment> subComments;
    
    public Comment(){};
    
    public Comment(String content){
        setId();
        setContent(content);
        setDateTime(dateTime);
        setSubComments(new ArrayList<Comment>());
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId() {
        currentId++;
        id = currentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = LocalDateTime.now();
    }

    public List<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<Comment> subComments) {
        this.subComments = subComments;
    }
    
    public boolean addSubComment(Comment c){
        if(checkDuplicates(c)) {
            return false;
        }
        getSubComments().add(c);
        return true;
    }
    
    public boolean checkDuplicates(Comment c) {
        for(Comment check : getSubComments()) {
            if(check.getId() == c.getId()) {
                return true;
            }
        }
        return false;
    }
}
