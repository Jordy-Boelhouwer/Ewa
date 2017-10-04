/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jordy
 */
@Path("hello")
public class HelloResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello";
    }
}
