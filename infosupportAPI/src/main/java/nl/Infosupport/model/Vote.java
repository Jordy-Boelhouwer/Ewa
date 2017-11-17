/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jordy
 */
public class Vote implements Serializable {
    private Date voteTime;
    private Profile profile;
    
    public Vote(){
        
    }
    
    public Vote(Profile p){
        this.setProfile(p);
        voteTime = new Date();
    }
    
    public Vote(Date voteTime, Profile profile){
        setVoteTime(voteTime);
        setProfile(profile);
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
