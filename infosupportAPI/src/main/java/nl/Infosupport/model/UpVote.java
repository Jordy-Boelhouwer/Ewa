/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.util.Date;

/**
 *
 * @author Jordy
 */
public class UpVote extends Vote {
    public UpVote(Date voteTime, Profile profile){
        super(voteTime, profile);
    }
    
    public UpVote(Profile p){
        super(p);
    }
}
