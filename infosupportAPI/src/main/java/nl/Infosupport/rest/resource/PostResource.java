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
    public Response UpVote(@PathParam("profileId") int profileId, @PathParam("postId") int postId) {
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
        
        post.upVote();
        if(post.isVoted()){
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/{postId}/downvote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DownVote(@PathParam("profileId") int profileId, @PathParam("postId") int postId) {
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
        
        post.downVote();
        if(post.isVoted()){
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

//    @POST
//    @Path("/file/upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadFile(
//            @FormDataParam("file") InputStream uploadedInputStream,
//            @FormDataParam("file") FormDataContentDisposition fileDetail) {
//
//        String uploadedFileLocation = "D:\\Users\\Jordy\\Documents\\HvA\\Jaar 2\\ewa\\infosupportAPI\\src\\main\\webapp\\images"
//                + fileDetail.getFileName();
//
//        // save it
//        writeToFile(uploadedInputStream, uploadedFileLocation);
//
//        String output = "File uploaded to : " + uploadedFileLocation;
//
//        return Response.status(200).entity(output).build();
//
//    }
//
//    // save uploaded file to new location
//    private void writeToFile(InputStream uploadedInputStream,
//            String uploadedFileLocation) {
//
//        try {
//            OutputStream out = new FileOutputStream(new File(
//                    uploadedFileLocation));
//            int read = 0;
//            byte[] bytes = new byte[1024];
//
//            out = new FileOutputStream(new File(uploadedFileLocation));
//            while ((read = uploadedInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//    }
    @Path("/{postId}/comments")
    public CommentResource getComments() {
        return new CommentResource();
    }
}
