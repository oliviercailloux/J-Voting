package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 * Implements MutablePreference interface.
 * <p>
 * <p>
 * The structure of a MutablePreference is a MutableGraph in which an edge represent the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class MutablePreferenceImpl extends PreferenceImpl
                implements MutablePreference {
    
    protected MutableGraph<Alternative> graph;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(MutablePreference.class.getName());
    
    private MutablePreferenceImpl(Preference pref) {
        graph = GraphBuilder.from(pref.asGraph()).build();
        voter = pref.getVoter();
    }
    
    private MutablePreferenceImpl(MutableGraph<Alternative> prefGraph,
                    Voter voter) {
        this.voter = voter;
        graph = prefGraph;
    }
    
    /**
     * Static factory method creating a mutable preference from a setAlternatives of data. Those datas are implemented in a graph.
     *
     * @param setAlternatives is a setAlternatives of lists of sets of Alternative representing the preference.
     *                        In the first setAlternatives, every list is a linear comparison of sets of alternatives. (first in the least is preferred to next ones, etc.)
     *                        Those sets of alternatives contain ex-aequo alternatives.
     * @param voter           is the Voter associated to the Preference.
     * @return the mutable preference, implemented with a graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference#asGraph()
     */
    public static MutablePreferenceImpl of(
                    Set<List<Set<Alternative>>> setAlternatives, Voter voter) {
        LOGGER.debug("MutablePreferenceImpl of Factory");
        Preconditions.checkNotNull(setAlternatives);
        Preconditions.checkNotNull(voter);
        return new MutablePreferenceImpl(preferenceGraphMaker(setAlternatives),
                        voter);
    }
    
    /**
     * Factory method making new MutablePreference from an other Preference.
     * It creates a new similar graph instance (mutable).
     * The voter instance of the created preference is the same as the copied preference.
     *
     * @param pref a Preference
     * @return a copy of this preference as a MutablePreference, with the same voter.
     */
    public static MutablePreferenceImpl of(Preference pref) {
        Preconditions.checkNotNull(pref);
        return new MutablePreferenceImpl(pref);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public MutableGraph<Alternative> asGraph() {
        return graph;
    }
}
