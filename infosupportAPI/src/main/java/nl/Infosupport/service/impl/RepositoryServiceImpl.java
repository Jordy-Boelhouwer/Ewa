/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.model.SubComment;
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
        try {
            instance.loadExamples();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RepositoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RepositoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //  Method to get a reference to the instance (singleton)
    public static RepositoryService getInstance() {
        return instance;
    }

    // An attribute that stores all cards (in memory)
    private Map<Integer, Profile> elements;

    private RepositoryServiceImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("infosupportPU");
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
    }

    @Override
    public Profile getProfileFromId(int profileId) {
        EntityManager em = getEntityManager();

        Profile p = em.find(Profile.class, profileId);

        em.close();

        return p;
    }

    @Override
    public Profile addProfile(Profile profile) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(profile);
        em.getTransaction().commit();

        em.close();

        return profile;
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
    }

    @Override
    public List<Comment> getCommentsOfPost(Post post) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId");
        query.setParameter("postId", post.getId());

        List<Comment> comments = query.getResultList();

        em.close();

        return comments;
    }

    private void loadExamples() throws NoSuchAlgorithmException, InvalidKeySpecException {

        File file = new File("C:\\smileyface.jpg");
        byte[] bFile = new byte[(int) file.length()];

        try {

            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);

            fileInputStream.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Profile p = new Profile("Jor", "Boelhouwer", "Super chille gast", 
                dateFormat.format(date), "back-end developer", "Jordybo123", "Jordy123");

        Post p1 = new Post("Titel1", "testie");
        p1.setImage(bFile);
        
        Comment c1 = new Comment("test1");
        p1.addComment(c1);
        
        Comment c2 = new Comment("test2");
        p1.addComment(c2);

        Post p2 = new Post("Titel2", "Ola!");
        
        Comment c3 = new Comment("Amigo!");
        p2.addComment(c3);

        p.addPost(p1);
        p.addPost(p2);

        addProfile(p);
    }

    @Override
    public Comment getCommentOfPost(Post post, int commentId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId AND"
                + " c.id = :id");

        query.setParameter("postId", post.getId());
        query.setParameter("id", commentId);

        Comment comment = null;
        try {
            comment = (Comment) query.getSingleResult();
        } catch (NoResultException e) {
            comment = null;
        }

        em.close();

        return comment;
    }
    
     @Override
    public int getVotesFromPost(int postId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT p.votes FROM Post p WHERE p.id = :postId");

        query.setParameter("postId", postId);

        int votes = 0;
        try {
            votes = (int) query.getSingleResult();
        } catch (NoResultException e) {
            votes = 0;
        }

        em.close();

        return votes;
    }

    @Override
    public List<SubComment> getSubCommentsOfComment(Comment comment) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT s FROM SubComment s WHERE s.comment.id = :commentId");
        query.setParameter("commentId", comment.getId());

        List<SubComment> subComments = query.getResultList();

        em.close();

        return subComments;
    }

    @Override
    public Comment addComment(Post post, Comment comment) {
        post.addComment(comment);

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();

        em.close();

        return comment;
    }

    @Override
    public SubComment addSubComment(Comment comment, SubComment subComment) {
        comment.addSubComment(subComment);

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(subComment);
        em.getTransaction().commit();

        em.close();

        return subComment;
    }

    @Override
    public void addUpvote(Post post) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("UPDATE Post p set p.votes = p.votes + 1 WHERE p.id = :postId");
        query.setParameter("postId", post.getId());
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void addDownVote(Post post) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("UPDATE Post p set p.votes = p.votes - 1 WHERE p.id = :postId");
        query.setParameter("postId", post.getId());
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void editProfile(Profile updatedProfile, Profile profile) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("UPDATE Profile p set p.first_name = :firstname, "
                + "p.last_name = :lastname, p.username = :user, p.password = :pass WHERE p.id = :profileId");
        query.setParameter("firstname", profile.getFirst_name());
        query.setParameter("lastname", profile.getLast_name());
        query.setParameter("user", profile.getUsername());
        query.setParameter("pass", profile.getPassword());
        query.setParameter("profileId", updatedProfile.getId());
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
