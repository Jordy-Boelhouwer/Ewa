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
import nl.Infosupport.model.SubComment;

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
     * Add a comment to a post
     * @param post Post to add the comment to
     * @param comment Comment to add
     * @return
     */
    Comment addComment(Post post, Comment comment);
    
    /**
     * Add a subcomment to a comment
     * @param comment Comment to add the subcomment to
     * @param subComment subcomment to add
     * @return
     */
    SubComment addSubComment(Comment comment, SubComment subComment);
    
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
    
    /**
     * Get all the comments from a post
     * @param post The post to get the comments from
     * @return List of comments
     */
    List<Comment> getCommentsOfPost(Post post);
    
    /**
     *
     * @param post 
     * @param commentId
     * @return
     */
    Comment getCommentOfPost(Post post, int commentId);
    
    List<SubComment> getSubCommentsOfComment(Comment comment);
    
    void addUpvote(Post post);
    
    void addDownVote(Post post);
    
}
