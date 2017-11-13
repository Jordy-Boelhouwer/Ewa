/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.rest.model.ClientError;
import nl.Infosupport.service.RepositoryService;
import nl.Infosupport.service.impl.RepositoryServiceImpl;

/**
 *
 * @author Jordy
 */
public class CommentResource {
    private RepositoryService service;
    
    public CommentResource() {
        service = RepositoryServiceImpl.getInstance();
    } 
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("profileId") int profileId,
            @PathParam("postId") int postId) {
        
        //Getting the Profile
        Profile profile = service.getProfileFromId(profileId);
        
        if(profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        //Getting the Post
        Post post = service.getPostOffProfile(profile, postId);
        
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        //Retrieving the comments
        List<Comment> comments = service.getAllCommentsOfPosts(post);
        
        return Response.status(Response.Status.OK).entity(comments).build();
    }

}
