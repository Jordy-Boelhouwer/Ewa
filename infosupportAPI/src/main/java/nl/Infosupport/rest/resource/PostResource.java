/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * The Post REST resource Note that this is a sub-resource of Profile
 *
 * @author Jordy
 */
public class PostResource {

    private final RepositoryService service;

    public PostResource() {
        service = RepositoryServiceImpl.getInstance();
    }

    /**
     * Get all the posts from a profile
     *
     * @param profileId
     * @return A list of Posts
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts(@PathParam("profileId") String profileId) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("resource " + profileId + " not found")).build();
        }

        List<Post> posts = service.getPostsOffProfile(profile);

        return Response.status(Response.Status.OK).entity(posts).build();
    }

    /**
     * Get a single post
     *
     * @param profileId
     * @param postId
     * @return The response, either a client error or a 200 message
     */
    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(
            @PathParam("profileId") String profileId,
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

    /**
     * Get the writer of a post
     *
     * @param profileId id of the writer
     * @return profile
     */
    @GET
    @Path("/{postId}/writer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWriter(
            @PathParam("profileId") String profileId) {

        //Getting the profile
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        return Response.status(Response.Status.OK).entity(profile.getName()).build();
    }

    /**
     * Get the votes from a post
     *
     * @param postId id of the post
     * @return votes
     */
    @GET
    @Path("/{postId}/votes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotes(@PathParam("postId") int postId) {
        int votes = service.getVotesFromPost(postId);
        return Response.status(Response.Status.OK).entity(votes).build();
    }

    /**
     * Add a post
     *
     * @param profileId
     * @param post
     * @return A response, either a client error or a 200 message
     */
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addPost(@PathParam("profileId") String profileId, Post post
//    ) {
//        Profile profile = service.getProfileFromId(profileId);
//
//        if (profile == null) {
//            return Response.status(Response.Status.NOT_FOUND).
//                    entity(new ClientError("Profile not found for id " + profileId)).build();
//        }
//
//        Post p = service.addPost(profile, post);
//
//        return Response.status(Response.Status.CREATED).entity(p).build();
//    }

    @POST
    @Path("/{postId}/upvote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpVote(@PathParam("profileId") String profileId, @PathParam("postId") int postId) {
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

        service.addUpvote(post);

        return Response.status(Response.Status.OK).build();

    }

    @POST
    @Path("/{postId}/downvote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DownVote(@PathParam("profileId") String profileId, @PathParam("postId") int postId) {
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

        service.addDownVote(post);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(
            @PathParam("profileId") String profileId,
            @FormDataParam("title") String title,
            @FormDataParam("content") String content,
            @FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("image") FormDataContentDisposition fileDetail) {
        Profile profile = service.getProfileFromId(profileId);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile not found for id " + profileId)).build();
        }

        String uploadedFileLocation = "D:\\" + fileDetail.getFileName();

        writeToFile(uploadedInputStream, uploadedFileLocation);
        
        //save image into database
    	File file = new File(uploadedFileLocation);
        byte[] bFile = new byte[(int) file.length()];
        
        try {
            //convert file into array of bytes
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                //convert file into array of bytes
                fileInputStream.read(bFile);
            }
        } catch (IOException e) {
        }
     
        Post post = new Post(title, content, bFile);

        Post p = service.addPost(profile, post);

        return Response.status(Response.Status.CREATED).entity(p).build();
    }

//    private byte[] getBytesFromInputStream(InputStream is) throws IOException {
//        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
//            byte[] buffer = new byte[0xFFFF];
//
//            for (int len; (len = is.read(buffer)) != -1;) {
//                os.write(buffer, 0, len);
//            }
//
//            os.flush();
//
//            return os.toByteArray();
//        }
//    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    /**
     * Create a comment sub-resource
     *
     * @return
     */
    @Path("/{postId}/comments")
    public CommentResource getComments() {
        return new CommentResource();
    }
}
