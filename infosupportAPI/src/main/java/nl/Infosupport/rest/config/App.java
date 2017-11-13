/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import nl.Infosupport.rest.resource.PostResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 *
 * @author Jordy
 */
@ApplicationPath("services/rest")
public class App extends ResourceConfig {
    public App() {
        final ResourceConfig resourceConfig = new ResourceConfig(MultiPartFeature.class);
       resourceConfig.register(MultiPartFeature.class);
    }
       
}
