/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.util.List;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.service.RepositoryService;

/**
 *
 * @author Jordy
 */
public class RepositoryServiceImpl implements RepositoryService {
    
    //a singleton reference
    private static RepositoryServiceImpl instance;
    
    static {
        instance = new RepositoryServiceImpl();
    }

    @Override
    public List<Profile> getAllProfiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profile getProfileFromId(int profileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addProfile(Profile profile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPost(Profile profile, Post post) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> getPostsOffProfile(Profile profile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Post getPostOffProfile(Profile profile, int postId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
