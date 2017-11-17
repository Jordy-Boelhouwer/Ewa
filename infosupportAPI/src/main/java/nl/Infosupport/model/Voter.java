/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.model;

import java.util.ArrayList;

/**
 *
 * @author Jordy
 */
public class Voter {

    private ArrayList<UpVote> upVotes = new ArrayList<>();
    private ArrayList<DownVote> downVotes = new ArrayList<>();

    public Voter() {
    }
    
    public boolean hasProfileUpvoted(Profile profile) {
        if (profile == null) {
            return false;
        }

        for (UpVote vote : getUpVotes()) {
            if (vote.getProfile().getUsername() == profile.getUsername()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasProfileDownvoted(Profile profile) {
        if (profile == null) {
            return false;
        }

        for (DownVote vote : getDownVotes()) {
            if (vote.getProfile().getUsername() == profile.getUsername()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<UpVote> getUpVotes() {
        return upVotes;
    }

    public void addUpVote(UpVote upVotes) {
        this.upVotes.add(upVotes);
    }

    public ArrayList<DownVote> getDownVotes() {
        return downVotes;
    }

    public void addDownVote(DownVote downVotes) {
        this.downVotes.add(downVotes);
    }

    public int getVotesCount() {
        return upVotes.size() - downVotes.size();
    }

    private void removeVoteFromProfile(Profile p) {
        if (p != null) {
            UpVote up = getUpVoteFromProfile(p);
            DownVote dn = getDownVoteFromProfile(p);
            if (up != null) {
                if (upVotes.contains(up)) {
                    upVotes.remove(up);
                }
            }
            if (dn != null) {
                if (downVotes.contains(dn)) {
                    downVotes.remove(dn);
                }
            }
        }
    }

    public UpVote getUpVoteFromProfile(Profile p) {
        for (UpVote v : upVotes) {
            if (v.getProfile().getUsername().equals(p.getUsername())) {
                return v;
            }
        }
        return null;
    }

    public DownVote getDownVoteFromProfile(Profile p) {
        for (DownVote v : downVotes) {
            if (v.getProfile().getUsername().equals(p.getUsername())) {
                return v;
            }
        }
        return null;
    }

    public void upVote(Profile votingProfile) {
        if (votingProfile == null) {
            throw new IllegalArgumentException("Cannot vote without a Profile");
        }

        this.removeVoteFromProfile(votingProfile);
        addUpVote(new UpVote(votingProfile));
    }

    public void downVote(Profile votingProfile) {
        if (votingProfile == null) {
            throw new IllegalArgumentException("Cannot vote without a profile");
        }

        this.removeVoteFromProfile(votingProfile);
        addDownVote(new DownVote(votingProfile));
    }
}
