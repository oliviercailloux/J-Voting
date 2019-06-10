package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implements a Preference as a graph and associates it with a Voter.
 *
 * To get an instance of this, see MutablePreference.
 *
 * This class contains all the common methods of all different Preferences.
 *
 * @see Preference
 * @see MutablePreference
 */
public abstract class PreferenceImpl implements Preference {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceImpl.class.getName());
    protected MutableGraph<Alternative> graph;
    protected Voter voter;
    
    /**
     * Static factory method creating a graph of preference from a setAlternatives of data.
     *
     *
     * @param pref is a setAlternatives of lists of sets of Alternative representing the preference.
     *          In the first setAlternatives, every list is a linear comparison of sets of alternatives. (first in the least is preferred to next ones, etc.)
     *          Those sets of alternatives contain ex-aequo alternatives.
     * @return the mutable preference, implemented with a graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference
     * @see PreferenceImpl#asGraph()
     */
    private static MutableGraph<Alternative> preferenceGraphMaker(Set<List<Set<Alternative>>> pref) {
        LOGGER.debug("PreferenceImpl preferenceGraphMaker");
        Preconditions.checkNotNull(pref);
        MutableGraph<Alternative> currentgraph = GraphBuilder.directed().allowsSelfLoops(true).build();
        for (List<Set<Alternative>> array : pref) {
            ArrayList<Alternative> tmp = new ArrayList<>();
            for (Set<Alternative> set : array) {
                
                // in a set of equality, adding every node to the graph
                // and in TMP list
                for (Alternative alt : set) {
                    if (!currentgraph.nodes().contains(alt))
                        currentgraph.addNode(alt);
                    tmp.add(alt);
                }
                // then create edges from every node in TMP to every node in current equality set
                for (Alternative alt : tmp) {
                    for (Alternative alt2 : set) {
                        currentgraph.putEdge(alt, alt2);
                    }
                }
            }
        }
        if (currentgraph.nodes().isEmpty()) throw new IllegalArgumentException("Must contain at least one alternative");
        return currentgraph;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public Graph<Alternative> asGraph() {
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
