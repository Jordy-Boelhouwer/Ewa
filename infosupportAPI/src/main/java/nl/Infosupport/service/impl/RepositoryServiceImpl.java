/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.service.RepositoryService;

/**
 *
 * @author Jordy
 */
public class RepositoryServiceImpl implements RepositoryService {

    private EntityManagerFactory entityManagerFactory;

    //a singleton reference
    private static RepositoryServiceImpl instance;
    
    // An instance of the service is created during class initialisation
    static {
        instance = new RepositoryServiceImpl();
        instance.loadExamples();
    }
    
    //  Method to get a reference to the instance (singleton)
    public static RepositoryService getInstance() {
        return instance;
    }
    
    // An attribute that stores all cards (in memory)
    private Map<Integer, Profile> elements;

    private RepositoryServiceImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("infosupportPU");
        //elements = new LinkedHashMap<>();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Profile> getAllProfiles() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Profile> profiles = em.createQuery("SELECT p FROM Profile p").getResultList();
        em.close();
        return profiles;
        //return new ArrayList<>(elements.values());
    }

    @Override
    public Profile getProfileFromId(int profileId) {
        EntityManager em = getEntityManager();

        Profile p = em.find(Profile.class, profileId);

        em.close();

        return p;

        //return elements.get(profileId);
    }

    @Override
    public Profile addProfile(Profile profile) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(profile);
        em.getTransaction().commit();

        em.close();
        
        return profile;

        //elements.put(profile.getId(), profile);
    }

    @Override
    public Post addPost(Profile profile, Post post) {
        profile.addPost(post);

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(post);
        em.getTransaction().commit();

        em.close();

        return post;

        //return profile.addPost(post);
    }

    @Override
    public List<Post> getPostsOffProfile(Profile profile) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery(
                "SELECT p FROM Post p WHERE p.profile.id = :profileId");

        query.setParameter("profileId", profile.getId());

        List<Post> result = query.getResultList();

        em.close();

        return result;

        //return profile.getPosts();
    }

    @Override
    public Post getPostOffProfile(Profile profile, int postId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT p FROM Post p WHERE p.profile.id = :profileId AND"
                + " p.id = :id");

        query.setParameter("profileId", profile.getId());
        query.setParameter("id", postId);

        Post post = null;
        try {
            post = (Post) query.getSingleResult();
        } catch (NoResultException e) {
            post = null;
        }

        em.close();

        return post;

//        List<Post> posts = getPostsOffProfile(profile);
//
//        if (posts == null) {
//            return null;
//        }
//
//        Post found = null;
//
//        for (Post p : posts) {
//            if (p.getId() == postId) {
//                found = p;
//                break;
//            }
//        }
//        return found;
    }

    @Override
    public List<Comment> getCommentsOfPost(Post post) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId");
        query.setParameter("postId", post.getId());

        List<Comment> comments = query.getResultList();

        em.close();

        return comments;

        //return post.getComments();
    }

//    @Override
//    public Comment getCommentOfPost(Post post, int commentId) {
//        EntityManager em = getEntityManager();
//        
//        Query query = em.createQuery("SELECT c FROM Comment c WHERE "
//                + "c.post.id = :postId");
//        query.setParameter("postId", post.getId());
//        
//        List<Comment> comments = query.getResultList();
//        
//        em.close();
//        
////        List<Comment> comments = getCommentsOfPost(post);
////
////        if (comments == null) {
////            return null;
////        }
////
////        Comment found = null;
////
////        for (Comment c : comments) {
////            if (c.getId() == commentId) {
////                found = c;
////                break;
////            }
////        }
////        return found;
//   }
    
    private void loadExamples() {

        Profile p = new Profile("Jordy", "Boelhouwer", "Jordybo123", "Jordy1995");

        Post p1 = new Post("Titel1", "Hallo!");
        p1.addComment(new Comment("Hey!"));
        p1.addComment(new Comment("Hi!"));

        Post p2 = new Post("Titel2", "Ola!");
        p2.addComment(new Comment("Amigo!"));

        p.addPost(p1);
        p.addPost(p2);

        addProfile(p);
    }


    @Override
    public Comment getCommentOfPost(Post post, int commentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
