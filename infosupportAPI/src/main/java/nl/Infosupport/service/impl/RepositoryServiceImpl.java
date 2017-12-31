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
    public Profile getProfileFromId(String profileId) {
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

    /**
     *
     * @param profile profile to be checked
     * @return true or false
     */
    public boolean accessTokenExists(Profile profile) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT p FROM Profile p WHERE p.access_token = :accessToken");
        query.setParameter("accessToken", profile.getAccess_token());

        Profile profileWithToken = null;
        try {
            profileWithToken = (Profile) query.getSingleResult();
            em.close();
            return true;
        } catch (NoResultException e) {
            profileWithToken = null;
            em.close();
            return false;
        }
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
        Query query = em.createQuery("UPDATE Profile p set p.name = :name, "
                + " p.bio = :bio, p.job = :job, "
                + "p.username = :user, p.password = :pass "
                + "WHERE p.id = :profileId");
        query.setParameter("name", profile.getName());
        query.setParameter("bio", profile.getBio());
        query.setParameter("job", profile.getJob());
        query.setParameter("profileId", updatedProfile.getId());
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
