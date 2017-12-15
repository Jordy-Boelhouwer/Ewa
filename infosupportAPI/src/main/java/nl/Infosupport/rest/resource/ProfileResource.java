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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.Infosupport.model.Profile;
import nl.Infosupport.rest.model.ClientError;
import nl.Infosupport.service.RepositoryService;
import nl.Infosupport.service.impl.RepositoryServiceImpl;

/**
 * The Profile REST resource
 *
 * @author Jordy
 */
@Path("profiles")
public class ProfileResource {

    private RepositoryService service;

    public ProfileResource() {
        service = RepositoryServiceImpl.getInstance();
    }

    /**
     * Get all profiles
     *
     * @return a JSON representation of a list of profiles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getAllProfiles() {
        return service.getAllProfiles();
    }

    public Response addProfile(@PathParam("profileId") int profileId, Profile profile) {

        Profile p = service.addProfile(profile);

        return Response.status(Response.Status.OK).entity(p).build();
    }

    /**
     * Get a specific profile
     *
     * @param id
     * @return a JSON representation of the specified profile
     */
    @GET
    @Path("/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileFromId(@PathParam("profileId") int id) {
        Profile profile = service.getProfileFromId(id);

        if (profile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile with id " + id + " was not found")).build();
        }
        return Response.status(Response.Status.OK).entity(profile).build();
    }

    /**
     * Add a profile to the list
     *
     * @param profile
     * @return a JSON representation of the saved profile
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProfile(Profile profile) {
        Profile p = service.addProfile(profile);
        return Response.status(Response.Status.CREATED).entity(p).build();
    }

    @PUT
    @Path("/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editProfile(@PathParam("profileId") int profileId, Profile profile) {
        Profile updatedProfile = service.getProfileFromId(profileId);
        if (updatedProfile == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile with id " + profileId + " was not found")).build();
        }
        
        service.editProfile(updatedProfile, profile);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Get the post sub-resource
     *
     * @return
     */
    @Path("/{profileId}/posts")
    public PostResource getPostResource() {
        return new PostResource();
    }
}
