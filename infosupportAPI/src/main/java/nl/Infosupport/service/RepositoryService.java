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
import nl.Infosupport.model.Vote;

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
    Profile getProfileFromId(String profileId);
    
    /**
     *
     * @param postId
     * @return
     */
    Post getPostFromId(int postId);
    
    Comment getCommentFromId(int commentId);
    
    Object getWriterOfComment(int commentId);
    
    /**
     * add a profile
     * @param profile profile to be added
     * @return 
     */
    Profile addProfile(Profile profile);  
    
    /**
     * edit a profile
     * @param updatedProfile
     * @param profile Profile to be added
     * @return profile
     */
    void editProfile(Profile updatedProfile, Profile profile);
    
    /**
     * add post to profile
     * @param profile profile to add the post to
     * @param post post to be added
     * @return 
     */
    Post addPost(Profile profile, Post post);
    
    /**
     * Add vote
     * @param profile profile that has voted
     * @param post post that is voted on
     * @param vote vote to be added
     * @return vote
     */
    Vote addVote(Profile profile, Post post, Vote vote);
    
    long getVotesCount(int postId);
    
    List<Vote> getVotesFromPost(int postId);
    
    List<Vote> getVotesFromProfile(String profileId);
    
    /**
     * Add a comment to a post
     * @param profile writer of the comment
     * @param post Post to add the comment to
     * @param comment Comment to add
     * @return
     */
    Comment addComment(Profile profile, Post post, Comment comment);
    
    /**
     * Add a subcomment to a comment
     * @param profile writer of subcomment
     * @param comment Comment to add the subcomment to
     * @param subComment subcomment to add
     * @return
     */
    SubComment addSubComment(Profile profile, Comment comment, SubComment subComment);
    
    /**
     * Get posts from a profile
     * @param profile profile to get posts from
     * @return List of posts from profile
     */
    List<Post> getPostsOffProfile(String profileId);

    /**
     * Get single post from profile
     * @param profile profile to get the posts from
     * @param postId id of post to return
     * @return post with specified id
     */
    Post getPostOffProfile(String profileId, int postId);
    
    /**
     * Get all the comments from a post
     * @param post The post to get the comments from
     * @return List of comments
     */
    List<Comment> getCommentsOfPost(int postId);
    
    /**
     *
     * @param post 
     * @param commentId
     * @return
     */
    Comment getCommentOfPost(int postId, int commentId);
    
    List<SubComment> getSubCommentsOfComment(int commentId);
}
