package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.base.Preconditions;
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
 *
 *
 * The structure of a MutablePreference is a MutableGraph in which an edge represent the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class MutablePreferenceImpl implements MutablePreference {
    
    protected MutableGraph<Alternative> graph;
    protected Voter voter;
    private static final Logger LOGGER = LoggerFactory.getLogger(MutablePreference.class.getName());
    
    
    private MutablePreferenceImpl(Preference pref) {
        graph = GraphBuilder.from(pref.asGraph()).build();
        voter = pref.getVoter();
    }
    
    private MutablePreferenceImpl(Set<List<Set<Alternative>>> pref, Voter voter) {
        this.voter = voter;
        graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        for (List<Set<Alternative>> array : pref) {
            ArrayList<Alternative> tmp = new ArrayList<>();
            for (Set<Alternative> set : array) {
                
                // in a set of equality, adding every node to the graph
                // and in TMP list
                for (Alternative alt : set) {
                    if (!graph.nodes().contains(alt))
                        graph.addNode(alt);
                    tmp.add(alt);
                }
                // then create edges from every node in TMP to every node in current equality set
                for (Alternative alt : tmp) {
                    for (Alternative alt2 : set) {
                        graph.putEdge(alt, alt2);
                    }
                }
            }
        }
    }
    
    /**
     * Static factory method creating a mutable preference from a setAlternatives of data. Those datas are implemented in a graph.
     *
     *
     * @param setAlternatives is a setAlternatives of lists of sets of Alternative representing the preference.
     *          In the first setAlternatives, every list is a linear comparison of sets of alternatives. (first in the least is preferred to next ones, etc.)
     *          Those sets of alternatives contain ex-aequo alternatives.
     * @param voter is the Voter associated to the Preference.
     * @return the mutable preference, implemented with a graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference#asGraph()
     */
    public static MutablePreferenceImpl of(Set<List<Set<Alternative>>> setAlternatives, Voter voter) {
        LOGGER.debug("MutablePreferenceImpl of Factory");
        Preconditions.checkNotNull(setAlternatives);
        Preconditions.checkNotNull(voter);
        return new MutablePreferenceImpl(setAlternatives, voter);
    }
    
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
    
    /**
     * {@inheritDoc}
     */
    @Override public Set<Alternative> getAlternatives() {
        return graph.nodes();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public Voter getVoter() {
        return voter;
    }
    
    @Override public String toString() {
        return graph.toString() + "\n" + voter.toString();
    }
}
