/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.Infosupport.model.Comment;
import nl.Infosupport.model.Post;
import nl.Infosupport.model.Profile;
import nl.Infosupport.service.RepositoryService;

/**
 *
 * @author Jordy
 */
public class RepositoryServiceImpl implements RepositoryService {

    //a singleton reference
    private static RepositoryServiceImpl instance;

    static {
        instance = new RepositoryServiceImpl();
        instance.loadExamples();
    }

    public static RepositoryService getInstance() {
        return instance;
    }

    private Map<Integer, Profile> elements;

    private RepositoryServiceImpl() {
        elements = new LinkedHashMap<>();
    }

    @Override
    public List<Profile> getAllProfiles() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public Profile getProfileFromId(int profileId) {
        return elements.get(profileId);
    }

    @Override
    public void addProfile(Profile profile) {
        elements.put(profile.getId(), profile);
    }

    @Override
    public boolean addPost(Profile profile, Post post) {
        return profile.addPost(post);
    }

    @Override
    public List<Post> getPostsOffProfile(Profile profile) {
        return profile.getPosts();
    }

    @Override
    public Post getPostOffProfile(Profile profile, int postId) {
        List<Post> posts = getPostsOffProfile(profile);
        
        if (posts == null) {
            return null;
        }
        
        Post found = null;
        
        for (Post p : posts) {
            if (p.getId() == postId) {
                found = p;
                break;
            }
        }
        return found;
    }
    
    @Override
    public List<Comment> getCommentsOfPost(Post post) {
        return post.getComments();
    }
    
    private void loadExamples(){
        Profile p = new Profile(1, "Jordy", "Boelhouwer", "Jordybo", "123");
        addProfile(p);
        Post post1 = new Post("titel1", "Hallo!", p);
        Post post2 = new Post("titel2", "Weer hallo!", p);
        Post post3 = new Post("titel3", "doei!", p);
        Comment comment1 = new Comment("Goede post!");
        Comment sub = new Comment("Vind ik ook!");
        
        p.addPost(post1);
        post1.addComment(comment1);
        comment1.addSubComment(sub);
        p.addPost(post2);
        p.addPost(post3);
        
        Profile p2 = new Profile(2, "Mohamed", "Boujou", "Mobo", "456");
        addProfile(p2);
        Post post4 = new Post("titel4", "Ola!", p2);
        p2.addPost(post4);
    }

    @Override
    public Comment getCommentOfPost(Post post, int commentId) {
        List<Comment> comments = getCommentsOfPost(post);
        
        if(comments == null) {
            return null;
        }
        
        Comment found = null;
        
        for(Comment c : comments) {
            if(c.getId() == commentId) {
                found = c;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean addComment(Post post, Comment comment) {
        return post.addComment(comment);
    }
}
