package io.github.oliviercailloux.j_voting.profiles.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;
import io.github.oliviercailloux.j_voting.profiles.ProfileI;

public class Borda implements SocialWelfareFunction {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(Borda.class.getName());
    private Multiset<Alternative> scores;

    public Borda(Multiset<Alternative> tempscores) {
        LOGGER.debug("Borda");
        scores = tempscores;
    }

    public Borda() {
        LOGGER.debug("emptyBorda");
        scores = HashMultiset.create();
    }

    /**
     * 
     * @return the multiset of alternatives of the object Borda.
     */
    public Multiset<Alternative> getMultiSet() {
        LOGGER.debug("getMultiSet:");
        return scores;
    }

    /**
     * @param profile a ProfileI <code>not null</code>
     * @return a Preference with the alternatives sorted
     */
    @Override
    public CompletePreferenceImpl getSocietyPreference(ImmutableProfileI profile) {
        LOGGER.debug("getSocietyStrictPreference");
        Preconditions.checkNotNull(profile);
        LOGGER.debug("parameter SProfile : {}", profile);
        setScores(profile);
        LOGGER.debug("return AScores : {}", scores);
        List<Set<Alternative>> al = new ArrayList<>();
        Set<Alternative> s;
        Multiset<Alternative> tempscores = scores;
        while (!tempscores.isEmpty()) {
            s = getMax(tempscores);
            al.add(s);
            for (Alternative a : s) {
                tempscores.remove(a, tempscores.count(a));
            }
        }
        CompletePreferenceImpl pref = new CompletePreferenceImpl(al);
        LOGGER.debug("return AScores : {}", pref);
        return pref;
    }

    /**
     * assigns a score to each alternative of a StrictPreference
     * 
     * @param sPref a StrictPreference <code>not null</code> adds the scores of
     *              the preference to the multiset for the alternatives in this
     *              StrictPreference
     */
    public void setScores(CompletePreferenceImpl pref) {
        LOGGER.debug("getScorePref");
        Preconditions.checkNotNull(pref);
        LOGGER.debug("parameter SPref : {}", pref);
        int size = pref.getPreferencesNonStrict().size();
        for (Alternative a : CompletePreferenceImpl
                        .toAlternativeSet(pref.getPreferencesNonStrict())) {
            scores.add(a, size - pref.getAlternativeRank(a) + 1);
        }
        LOGGER.debug("return score : {}", scores);
    }

    /**
     * 
     * @param profile a ProfileI <code>not null</code> sets the scores in the
     *                multiset for the profile (if an alternative has a score of
     *                3, it ill appear three times in the multiset)
     */
    public void setScores(ProfileI profile) {
        LOGGER.debug("getScoreProf");
        Preconditions.checkNotNull(profile);
        LOGGER.debug("parameter SProfile : {}", profile);
        Iterable<Voter> allVoters = profile.getAllVoters();
        for (Voter v : allVoters) {
            setScores(profile.getPreference(v));
        }
        LOGGER.debug("return scores : {}", scores);
    }

    /**
     * @param tempscores a multiset <code>not null</code>
     * @return the alternatives with the maximum score in the multiset
     */
    public Set<Alternative> getMax(Multiset<Alternative> tempscores) {
        LOGGER.debug("getMax");
        Preconditions.checkNotNull(tempscores);
        Set<Alternative> set = new HashSet<>();
        Iterable<Alternative> alternativesList = tempscores.elementSet();
        Alternative alternativeMax = new Alternative(0);
        boolean first = true;
        for (Alternative a : alternativesList) {
            if (first) {
                alternativeMax = a;
                set.add(a);
                first = false;
            } else {
                if (tempscores.count(a) > tempscores.count(alternativeMax)) {
                    alternativeMax = a;
                    set = new HashSet<>();
                    set.add(a);
                }
                if (tempscores.count(a) == tempscores.count(alternativeMax)) {
                    set.add(a);
                }
            }
        }
        LOGGER.debug("Max : {} ", set);
        return set;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scores);
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // Check not null
        if (o == null)
            return false;
        // Check class type and cast o
        if (this.getClass() != o.getClass())
            return false;
        Borda borda = (Borda) o;
        // check field
        return this.scores.equals(borda.scores);
    }
}
