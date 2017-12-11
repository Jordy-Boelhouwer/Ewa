/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
<<<<<<< Updated upstream
    public Profile addProfile(Profile profile) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(profile);
        em.getTransaction().commit();

        em.close();

<<<<<<< Updated upstream
        return profile;
=======
        //elements.put(profile.getId(), profile);
=======
    public boolean addProfile(Profile profile) {
        elements.put(profile.getId(), profile);
>>>>>>> Stashed changes
>>>>>>> Stashed changes
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

    private void loadExamples() {

        File file = new File("C:\\smileyface.jpg");
        byte[] bFile = new byte[(int) file.length()];

        try {

            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);

            fileInputStream.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        Profile p = new Profile("Jor", "Boelhouwer", "Jordybo123", "Jordy1995");

        Post p1 = new Post("Titel1", "testie");
        p1.setImage(bFile);
        p1.addComment(new Comment("test1"));
        p1.addComment(new Comment("test2"));

        Post p2 = new Post("Titel2", "Ola!");
        p2.addComment(new Comment("Amigo!"));

        p.addPost(p1);
        p.addPost(p2);

        addProfile(p);

        try {

            //FileOutputStream fos = new FileOutputStream("images\\output.jpg");  //windows
            FileOutputStream fos = new FileOutputStream("C:\\test.jpg");

            fos.write(p1.getImage());

            fos.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
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
}
