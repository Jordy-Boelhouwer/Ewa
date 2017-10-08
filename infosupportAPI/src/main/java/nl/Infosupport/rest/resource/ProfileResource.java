/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import java.util.List;
import javax.ws.rs.GET;
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
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getAllProfiles(){
        return service.getAllProfiles();
    }
    
    /**
     *
     */
    @GET
    @Path("/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileFromId(@PathParam("profileId")int id){
        Profile profile = service.getProfileFromId(id);
        
        if(profile == null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity(new ClientError("Profile with id " + id + " was not found")).build();
        }
        return Response.status(Response.Status.OK).entity(profile).build();
    }
    @Path("/{profileId}/posts")
    public PostResource getPostResource(){
        return new PostResource();
    }
}
