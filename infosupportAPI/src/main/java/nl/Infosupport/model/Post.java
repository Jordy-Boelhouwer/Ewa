/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Jordy
 */
public class Post implements Serializable {
    private int id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    
    public Post() {}
    
    public Post(int id, String title, String content){
        setId(id);
        setTitle(title);
        setContent(content);
        setDateTime(dateTime);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public final void setId(int id) {
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
}
