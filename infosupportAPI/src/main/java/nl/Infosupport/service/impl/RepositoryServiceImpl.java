/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.model.SubComment;
import nl.Infosupport.model.Vote;
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

    private RepositoryServiceImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("infosupportPU");
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Profile> getAllProfiles() {
        EntityManager em = getEntityManager();
        List<Profile> profiles = em.createQuery("SELECT p FROM Profile p").getResultList();
        em.close();
        return profiles;
    }

    @Override
    public Profile getProfileFromId(String profileId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT p FROM Profile p WHERE p.id = :profileId");

        query.setParameter("profileId", profileId);

        Profile profile;
        try {
            profile = (Profile) query.getSingleResult();
        } catch (NoResultException e) {
            profile = null;
        }

        em.close();

        return profile;
    }
    
    @Override
    public Post getPostFromId(int postId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT p FROM Post p WHERE p.id = :postId");

        query.setParameter("postId", postId);

        Post post;
        try {
            post = (Post) query.getSingleResult();
        } catch (NoResultException e) {
            post = null;
        }

        em.close();

        return post;
    }
    
    
    @Override
    public Comment getCommentFromId(int commentId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.id = :commentId");

        query.setParameter("commentId", commentId);

        Comment comment;
        try {
            comment = (Comment) query.getSingleResult();
        } catch (NoResultException e) {
            comment = null;
        }

        em.close();

        return comment;
    }
    
        @Override
    public Profile getWriterOfComment(int commentId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT c.profile FROM Comment c WHERE c.id = :commentId");

        query.setParameter("commentId", commentId);

        Profile profile;
        try {
            profile = (Profile) query.getSingleResult();
        } catch (NoResultException e) {
            profile = null;
        }

        em.close();

        return profile;
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
    public Vote addVote(Profile profile, Post post, Vote vote) {
        post.addPostVote(vote);
        profile.addProfileVote(vote);
        
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(vote);
        em.getTransaction().commit();

        em.close();

        return vote;
        
    }

    @Override
    public List<Post> getPostsOffProfile(String profileId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery(
                "SELECT p FROM Post p WHERE p.profile.id = :profileId");

        query.setParameter("profileId", profileId);

        List<Post> result = query.getResultList();

        em.close();

        return result;
    }

    @Override
    public Post getPostOffProfile(String profileId, int postId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT p FROM Post p WHERE p.profile.id = :profileId AND"
                + " p.id = :id");

        query.setParameter("profileId", profileId);
        query.setParameter("id", postId);

        Post post;
        try {
            post = (Post) query.getSingleResult();
        } catch (NoResultException e) {
            post = null;
        }

        em.close();

        return post;
    }

    @Override
    public List<Comment> getCommentsOfPost(int postId) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId");
        query.setParameter("postId", postId);

        List<Comment> comments = query.getResultList();

        em.close();

        return comments;
    }

    @Override
    public Comment getCommentOfPost(int postId, int commentId) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId AND"
                + " c.id = :id");

        query.setParameter("postId", postId);
        query.setParameter("id", commentId);

        Comment comment;
        try {
            comment = (Comment) query.getSingleResult();
        } catch (NoResultException e) {
            comment = null;
        }

        em.close();

        return comment;
    }
    
    @Override
    public long getVotesCount(int postId) {
        EntityManager em = getEntityManager();
        
        Query query = em.createQuery("SELECT sum(v.voted) FROM Vote v WHERE v.post.id = :postId");
        
        query.setParameter("postId", postId);
        
        long votes;
        try {
            votes = (long) query.getSingleResult();
        } catch (NoResultException e) {
            votes = 0;
        }

        em.close();
        
        return votes;
    }
    
    @Override
    public List<Vote> getVotesFromPost(int postId) {
        EntityManager em = getEntityManager();
        
        Query query = em.createQuery("SELECT v FROM Vote v WHERE v.post.id = :postId");
        
        query.setParameter("postId", postId);
        
        List<Vote> votes = query.getResultList();


        em.close();
        
        return votes;
    }
    
    @Override
    public List<Vote> getVotesFromProfile(String profileId) {
        EntityManager em = getEntityManager();
        
        Query query = em.createQuery("SELECT v FROM Vote v WHERE v.profile.id = :profileId");
        
        query.setParameter("profileId", profileId);
        
        List<Vote> votes = query.getResultList();

        em.close();
        
        return votes;
    }

    @Override
    public List<SubComment> getSubCommentsOfComment(int commentId) {
        EntityManager em = getEntityManager();

        Query query = getEntityManager().createQuery("SELECT s FROM SubComment s WHERE s.comment.id = :commentId");
        query.setParameter("commentId", commentId);

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
    public Comment addComment(Profile profile, Post post, Comment comment) {
        post.addComment(comment);
        profile.addComment(comment);

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();

        em.close();

        return comment;
    }

    @Override
    public SubComment addSubComment(Profile profile, Comment comment, SubComment subComment) {
        comment.addSubComment(subComment);
        profile.addSubComment(subComment);

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(subComment);
        em.getTransaction().commit();

        em.close();

        return subComment;
    }

    @Override
    public void editProfile(Profile updatedProfile, Profile profile) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("UPDATE Profile p set p.bio = :bio, "
                + "p.job = :job "
                + "WHERE p.id = :profileId");
        query.setParameter("bio", profile.getBio());
        query.setParameter("job", profile.getJob());
        query.setParameter("profileId", updatedProfile.getId());
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
