/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
 * The Comment sub-resource Note that this is a sub-resource of Post
 *
 * @author Jordy
 */
public class CommentResource {

    private RepositoryService service;

    public CommentResource() {
        service = RepositoryServiceImpl.getInstance();
    }

    /**
     * Get all comments from a specific post
     *
     * @param profileId The profile which created the post
     * @param postId The post with comments
     * @return A response, either a client error or a 200 message
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComments(@PathParam("postId") int postId) {
        //Retrieving the comments
        List<Comment> comments = service.getCommentsOfPost(postId);
        return Response.status(Response.Status.OK).entity(comments).build();
    }

    /**
     * Get a specific comment
     *
     * @param profileId The profile which created the post
     * @param postId The post with comments
     * @param commentId The id of the comment to get
     * @return
     */
    @GET
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(
            @PathParam("profileId") String profileId,
            @PathParam("postId") int postId,
            @PathParam("commentId") int commentId) {
        Response resp;

        Comment comment = service.getCommentOfPost(postId, commentId);

        if (comment == null) {
            resp = Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Resource not found for post id " + commentId)).build();
        } else {
            resp = Response.status(Response.Status.OK).entity(comment).build();
        }

        return resp;
    }

    @GET
    @Path("/{commentId}/writer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWriterOfComment(
            @PathParam("commentId") int commentId) {
        Response resp;

        Profile profile = service.getWriterOfComment(commentId);

        if (profile == null) {
            resp = Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Resource not found for post id " + commentId)).build();
        } else {
            resp = Response.status(Response.Status.OK).entity(profile).build();
        }

        return resp;
    }

    /**
     *
     * @param profileId The profile which created the post
     * @param postId The post with comments
     * @param comment The id of the comment to get
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam("profileId") String profileId,
            @PathParam("postId") int postId,
            Comment comment) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        Post post = service.getPostFromId(postId);

        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Post not found for id " + postId)).build();
        }

        Comment c = service.addComment(profile, post, comment);

        return Response.status(Response.Status.CREATED).entity(c).build();

    }

    /**
     * Create a SubComment sub-resource
     *
     * @return
     */
    @Path("/{commentId}/subcomments")
    public SubCommentResource getSubComments() {
        return new SubCommentResource();
    }
}
