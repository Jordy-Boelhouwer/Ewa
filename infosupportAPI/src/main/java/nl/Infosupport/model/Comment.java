/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Jordy
 */
public class Comment implements Serializable {
    private int id;
    private String content;
    private LocalDateTime dateTime;
    private int currentId = 0;
    
    public Comment(){};
    
    public Comment(String content){
        setId();
        setContent(content);
        setDateTime(dateTime);
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
}
