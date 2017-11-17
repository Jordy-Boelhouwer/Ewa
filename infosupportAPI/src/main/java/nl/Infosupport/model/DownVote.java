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
public class DownVote extends Vote {
    public DownVote(Date voteTime, Profile profile){
        super(voteTime, profile);
    }
    
    public DownVote(Profile p) {
        super(p);
    }
}
