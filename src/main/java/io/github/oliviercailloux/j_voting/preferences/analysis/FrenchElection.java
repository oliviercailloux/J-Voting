package io.github.oliviercailloux.j_voting.preferences.analysis;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

public class FrenchElection {

    ImmutableSet<Alternative> setAlternatives;
    ImmutableSet<Voter> setVoters;
    Map<Alternative, Integer> scores;
    Alternative winner;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(FrenchElection.class.getName());

    /**
     * 
     * @param linearPreferences <code> not null </code>
     * @return new FrenchElection
     */
    public static FrenchElection asFrenchElection(
                    Set<LinearPreference> linearPreferences) {
        LOGGER.debug("FrenchElection Factory");
        Preconditions.checkNotNull(linearPreferences);
        return new FrenchElection(linearPreferences);
    }

    private FrenchElection(Set<LinearPreference> linearPreferences) {
        LOGGER.debug("FrenchElection Constructor");
        Map<Alternative, Integer> tmpScores = Maps.newHashMap();
        Set<Voter> tmpVoters = Sets.newHashSet();
        for (LinearPreference linearPreference : linearPreferences) {
            if (!tmpVoters.add(linearPreference.getVoter()))
                throw new IllegalArgumentException(
                                "A voter can't vote two times in a same election");
            Alternative tmpAlternative = linearPreference.asList().get(0);
            Integer votes = (tmpScores.get(tmpAlternative) != null)
                            ? tmpScores.get(tmpAlternative)
                            : 0;
            tmpScores.put(tmpAlternative, votes + 1);
        }
        Entry<Alternative, Integer> bestAlternative = Collections.max(
                        tmpScores.entrySet(),
                        (Entry<Alternative, Integer> e1,
                                        Entry<Alternative, Integer> e2) -> e1
                                                        .getValue()
                                                        .compareTo(e2.getValue()));
        this.setAlternatives = ImmutableSet.copyOf(tmpScores.keySet());
        this.winner = bestAlternative.getKey();
        this.scores = tmpScores;
        this.setVoters = ImmutableSet.copyOf(tmpVoters);
    }

    public Alternative getWinner() {
        return winner;
    }

    public ImmutableSet<Alternative> getListAlternatives() {
        return setAlternatives;
    }

    public Map<Alternative, Integer> getScores() {
        return scores;
    }
}
