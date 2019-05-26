package io.github.oliviercailloux.j_voting.profiles.management;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfile;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfile;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfileI;
import io.github.oliviercailloux.j_voting.profiles.Profile;
import io.github.oliviercailloux.j_voting.profiles.ProfileI;
import io.github.oliviercailloux.j_voting.profiles.StrictProfile;
import io.github.oliviercailloux.j_voting.profiles.StrictProfileI;

/**
 * 
 * This class is a builder for profiles.
 *
 */
public class ProfileBuilder {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ProfileBuilder.class.getName());
    protected Map<Voter, CompletePreferenceImpl> votes;
    protected int nextVoterId = 1;

    protected ProfileBuilder() {
        LOGGER.debug("constructor empty:");
        this.votes = new HashMap<>();
    }

    private ProfileBuilder(ProfileI prof) {
        LOGGER.debug("parameter prof : {}", prof);
        this.votes = castMapExtendsToRegularVoterPref(prof.getProfile());
    }

    /**
     * 
     * @param v    not <code> null </code>
     * @param pref not <code> null </code>
     * 
     *             adds the preference pref for the voter v in the map.
     */
    public void addVote(Voter v, CompletePreferenceImpl pref) {
        LOGGER.debug("addProfile:");
        Preconditions.checkNotNull(v);
        Preconditions.checkNotNull(pref);
        LOGGER.debug("parameters: voter {} pref {}", v, pref);
        votes.put(v, pref);
    }

    /**
     * Adds several votes (the given number) for the given preference to the
     * profile.
     * 
     * @param pref     <code>not null</code> a StrictPreference
     * @param nbVoters <code>not null</code> the number of voters that voted for
     *                 the preference as parameter
     */
    public void addVotes(CompletePreferenceImpl pref, int nbVoters) {
        LOGGER.debug("AddVotes");
        Preconditions.checkNotNull(pref);
        Preconditions.checkNotNull(nbVoters);
        LOGGER.debug("parameters : nbVoters {} for the preference {}", nbVoters,
                        pref);
        for (int m = 0; m < nbVoters; m++) {// we create as many profiles as
                                            // voters
            Voter v = Voter.createVoter(nextVoterId);
            LOGGER.debug("adds the voter {} and the pref as parameter to the profile",
                            nextVoterId);
            nextVoterId++;
            addVote(v, pref);
        }
    }

    /**
     * 
     * @return a ProfileI created from the builder.
     */
    public ProfileI createProfileI() {
        LOGGER.debug("createProfileI:");
        return ImmutableProfileI.createImmutableProfileI(votes);
    }

    /**
     * 
     * @return a Profile created from the builder if it is complete, otherwise
     *         throws an exception.
     */
    public Profile createProfile() {
        LOGGER.debug("createProfile:");
        if (!createProfileI().isComplete()) {
            throw new IllegalArgumentException(
                            "The built profile is not complete.");
        }
        return ImmutableProfile.createImmutableProfile(votes);
    }

    /**
     * 
     * @return a StrictProfileI created from the builder if it is strict,
     *         otherwise throws an exception.
     */
    public StrictProfileI createStrictProfileI() {
        LOGGER.debug("createStrictProfileI:");
        if (!createProfileI().isStrict()) {
            throw new IllegalArgumentException(
                            "The built profile is not strict.");
        }
        return ImmutableStrictProfileI.createImmutableStrictProfileI(votes);
    }

    /**
     * 
     * @return a StrictProfile created from the builder if it is strict and
     *         complete, otherwise throws an exception.
     */
    public StrictProfile createStrictProfile() {
        if (!createProfileI().isComplete()) {
            throw new IllegalArgumentException(
                            "The built profile is not complete.");
        }
        if (!createProfileI().isStrict()) {
            throw new IllegalArgumentException(
                            "The built profile is not strict.");
        }
        return ImmutableStrictProfile.createImmutableStrictProfile(votes);
    }

    /**
     * 
     * @param map a map of Voters and preferences (or an extension of
     *            preference)
     * @return a map of voters and preferences (cast to the most general class :
     *         Preference)
     */
    public static Map<Voter, CompletePreferenceImpl> castMapExtendsToRegularVoterPref(
                    Map<Voter, ? extends CompletePreferenceImpl> map) {
        LOGGER.debug("castMapToRegularVoterPref");
        Preconditions.checkNotNull(map);
        Map<Voter, CompletePreferenceImpl> result = new HashMap<>();
        for (Voter v : map.keySet()) {
            LOGGER.debug("adds the voter {} and his preference {}", v,
                            map.get(v));
            result.put(v, map.get(v));
        }
        return result;
    }

    /**
     * Factory method without parameter
     * 
     * @return new ProfileBuilder
     */
    public static ProfileBuilder createProfileBuilder() {
        return new ProfileBuilder();
    }

    /**
     * Factory method with parameter prof
     * 
     * @param prof
     * @return new ProfileBuilder
     */
    public static ProfileBuilder createProfileBuilder(ProfileI prof) {
        if (Objects.equals(prof, null))
            return createProfileBuilder();
        return new ProfileBuilder(prof);
    }
}
