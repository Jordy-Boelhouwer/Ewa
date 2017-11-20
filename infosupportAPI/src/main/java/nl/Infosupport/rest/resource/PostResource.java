/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.rest.model.ClientError;
import nl.Infosupport.service.RepositoryService;
import nl.Infosupport.service.impl.RepositoryServiceImpl;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import nl.Infosupport.model.DownVote;
import nl.Infosupport.model.UpVote;
import nl.Infosupport.model.Voter;


/**
 *
 * @author Jordy
 */
public class PostResource {

    private RepositoryService service;

    public PostResource() {
        service = RepositoryServiceImpl.getInstance();
        
    }

    public String test() {
        return "It works";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts(@PathParam("profileId") int profileId) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + profileId + " not found")).build();
        }

        List<Post> posts = service.getPostsOffProfile(profile);

        return Response.status(Response.Status.OK).entity(posts).build();
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(
            @PathParam("profileId") int profileId,
            @PathParam("postId") int postId) {
        Response resp;

        //Getting the profile
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        Post post = service.getPostOffProfile(profile, postId);

        if (post == null) {
            resp = Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Resource not found for post id " + postId)).build();
        } else {
            resp = Response.status(Response.Status.OK).entity(post).build();
        }

        return resp;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(@PathParam("profileId") int profileId, Post post
    ) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        boolean created = service.addPost(profile, post);

        if (created) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new ClientError("post already exists for id " + post.getId())).build();
        }
    }
    @POST
    @Path("/{postId}/upvote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUpVote(@PathParam("profileId") int profileId,
            @PathParam("postId") int postId, int votingProfileId){
        Profile profile = service.getProfileFromId(profileId);
        Profile votingProfile = service.getProfileFromId(votingProfileId);
        Post post = service.getPostOffProfile(profile, postId);
        
        if(profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        if(votingProfile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Voting profile not found for id " + votingProfile)).build();
        }
        
        post.getVotes().upVote(votingProfile);
        
        if(!post.getVotes().hasProfileUpvoted(votingProfile)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Path("/{postId}/upvote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUpVotes(@PathParam("profileId") int profileId,
            @PathParam("postId") int postId) {
        Profile profile = service.getProfileFromId(profileId);
        
        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        Post post = service.getPostOffProfile(profile, postId);
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        List<UpVote> upVotes = post.getVotes().getUpVotes();
        
        return Response.status(Response.Status.OK).entity(upVotes).build();  
    }
    
    @POST
    @Path("/{postId}/downvote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDownVote(@PathParam("profileId") int profileId,
            @PathParam("postId") int postId, Profile votingProfile){
        Profile profile = service.getProfileFromId(profileId);
        Post post = service.getPostOffProfile(profile, postId);
        if(profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        if(votingProfile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + votingProfile)).build();
        }
        
        post.getVotes().downVote(votingProfile);
        
        if(!post.getVotes().hasProfileDownvoted(votingProfile)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Path("/{postId}/downvote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDownVotes(@PathParam("profileId") int profileId,
            @PathParam("postId") int postId) {
        Profile profile = service.getProfileFromId(profileId);
        
        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }
        
        Post post = service.getPostOffProfile(profile, postId);
        
        if(post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }
        
        List<DownVote> downVotes = post.getVotes().getDownVotes();
        
        return Response.status(Response.Status.OK).entity(downVotes).build();  
    }

    @Path("/{postId}/comments")
    public CommentResource getComments() {
        return new CommentResource();
    }
}
