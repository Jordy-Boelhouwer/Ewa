/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

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
import nl.Infosupport.model.Vote;
import nl.Infosupport.rest.model.ClientError;
import nl.Infosupport.service.RepositoryService;
import nl.Infosupport.service.impl.RepositoryServiceImpl;

/**
 *
 * @author Jordy
 */
public class VoteResource {
    private final RepositoryService service;

    /**
     * Constructor which initializes RepositoryService
     */
    public VoteResource() {
        service = RepositoryServiceImpl.getInstance();
    }
    
    /**
     * Add a vote
     * @param profileId id of profile who voted
     * @param postId id of post voted on
     * @param vote vote to be added
     * @return The response, either a client error or a 200 message
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response vote(@PathParam("profileId") String profileId, @PathParam("postId") int postId, Vote vote) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        Post post = service.getPostOffProfile(profile, postId);

        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }

        Vote v = service.addVote(profile, post, vote);

        return Response.status(Response.Status.CREATED).entity(v).build();

    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotesCount(@PathParam("profileId") String profileId, @PathParam("postId") int postId) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + profileId + " not found")).build();
        }
        
        Post post = service.getPostOffProfile(profile, postId);
        
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + postId + " not found")).build();
        }
        
        long voteCount = service.getVotesCount(post);

        return Response.status(Response.Status.OK).entity(voteCount).build();
    }
    
    @GET
    @Path("/getPostVotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotesFromPosts(@PathParam("profileId") String profileId, @PathParam("postId") int postId) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + profileId + " not found")).build();
        }
        
        Post post = service.getPostOffProfile(profile, postId);
        
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + postId + " not found")).build();
        }
        
        List<Vote> votes = service.getVotesFromPost(post);

        return Response.status(Response.Status.OK).entity(votes).build();
    }
    
    @GET
    @Path("/getProfileVotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotesFromProfile(@PathParam("profileId") String profileId) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + profileId + " not found")).build();
        }
        
        List<Vote> votes = service.getVotesFromProfile(profile);

        return Response.status(Response.Status.OK).entity(votes).build();
    }
}
