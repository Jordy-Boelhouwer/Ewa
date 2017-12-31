/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.model.SubComment;
import nl.Infosupport.rest.model.ClientError;
import nl.Infosupport.service.RepositoryService;
import nl.Infosupport.service.impl.RepositoryServiceImpl;

/**
 * The SubComment sub-resource
 * Note that this is a sub-resource of Comment
 * @author Jordy
 */
public class SubCommentResource {
    private RepositoryService service;

    public SubCommentResource() {
        service = RepositoryServiceImpl.getInstance();
    }
    
    /**
     *
     * @param profileId
     * @param postId
     * @param commentId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubComments(@PathParam("profileId") String profileId,
            @PathParam("postId") int postId,
            @PathParam("commentId") int commentId){
        
        //Getting the Profile
        Profile profile = service.getProfileFromId(profileId);
        
        if(profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        //Get the post from the profile
        Post post = service.getPostOffProfile(profile, postId);
        
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        Comment comment = service.getCommentOfPost(post, commentId);
        
        //Retrieving the comments
        List<SubComment> subComments = service.getSubCommentsOfComment(comment);
        
        return Response.status(Response.Status.OK).entity(subComments).build();
    }
    
    /**
     *
     * @param profileId The profile which created the post
     * @param postId The post with comments
     * @param commentId The id of the comment to add a subcomment to
     * @param subComment The subcomment to add
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubComment(@PathParam("profileId") String profileId, @PathParam("postId") int postId, 
            @PathParam("commentId") int commentId, SubComment subComment) {
        Profile profile = service.getProfileFromId(profileId);
        
        if(profile == null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        Post post = service.getPostOffProfile(profile, postId);
        
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        Comment comment = service.getCommentOfPost(post, commentId);
        
        if(comment == null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Comment not found for id " + commentId)).build();
        }
        
        SubComment s = service.addSubComment(comment, subComment);
        //comment.addSubComment(subComment);
        
        return Response.status(Response.Status.CREATED).entity(s).build();
        
    }
    
    
}
