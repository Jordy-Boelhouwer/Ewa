/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jordy
 */
public class Profile implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private List<Post> posts;
    
    public Profile(){}
    
    public Profile(int id, String first_name, String last_name, String username, String password){
        setId(id);
        setFirst_name(first_name);
        setLast_name(last_name);
        setUsername(username);
        setPassword(password);
        setPosts(new ArrayList<Post>());
    }
    
    public Profile(String username) {
        setUsername(username);
    }

    public int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public final void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public final void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public final void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    /**
     *
     * @param p
     * @return
     */
    public boolean addPost(Post p){
         if(checkDuplicates(p)) {
            return false;
        }
        getPosts().add(p);
        return true;
    }
    
    public boolean checkDuplicates(Post p) {
        for(Post check : getPosts()) {
            if(check.getId() == p.getId()) {
                return true;
            }
        }
        return false;
    }

}
