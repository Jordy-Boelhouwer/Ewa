/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service;

import java.util.List;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;

/**
 *
 * @author Jordy Boelhouwer
 */
public interface RepositoryService {
    
    /**
     * Get all profiles
     * @return List with all profiles
     */
    List<Profile> getAllProfiles();
    
    
    /**
     * get a specific profile
     * @param profileId the id of the specific profile
     * @return profile with specified id
     */
    Profile getProfileFromId(int profileId);
    
    
    /**
     * add a profile
     * @param profile profile to be added
     */
    Profile addProfile(Profile profile);
    
    
    /**
     * add post to profile
     * @param profile profile to add the post to
     * @param post post to be added
     * @return 
     */
    Post addPost(Profile profile, Post post);
    
    /**
     * Get posts from a profile
     * @param profile profile to get posts from
     * @return List of posts from profile
     */
    List<Post> getPostsOffProfile(Profile profile);

    /**
     * Get single post from profile
     * @param profile profile to get the posts from
     * @param postId id of post to return
     * @return post with specified id
     */
    Post getPostOffProfile(Profile profile, int postId);
    
    List<Comment> getCommentsOfPost(Post post);
    
    Comment getCommentOfPost(Post post, int commentId);
}
