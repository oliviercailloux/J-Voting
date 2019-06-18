package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graphs;
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
 * The structure of a MutablePreference is a MutableGraph in which an edge represents the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class MutablePreferenceImpl implements MutablePreference {
    
    protected Voter voter;
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
     * Static factory method creating a mutable preference from a set of List of sets of Alternatives of data.
     * Those datas are implemented in a graph.
     *
     * @param setAlternatives is a set of lists of sets of Alternatives representing the preference.
     *                        In the first set, every list is a linear comparison of sets of alternatives.
     *                        (first in the list is preferred to next ones, etc.)
     *                        Those sets of alternatives contain ex-aequo alternatives.
     * @param voter           is the Voter associated to the Preference.
     * @return the mutable preference, implemented with a transitively closed graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference#asGraph()
     */
    public static MutablePreferenceImpl of(
                    Set<List<Set<Alternative>>> pref, Voter voter) {
        LOGGER.debug("MutablePreferenceImpl of Factory");
        Preconditions.checkNotNull(pref);
        Preconditions.checkNotNull(voter);
        return new MutablePreferenceImpl(preferenceGraphMaker(pref),
                        voter);
    }
    
    /**
     * Static factory method creating a graph of preference from a set of lists of sets of Alternatives of data.
     *
     * @param pref is a set of lists of sets of Alternatives representing the preference.
     *             In the first set, every list is a linear comparison of sets of alternatives.
     *             (first in the list is preferred to next ones, etc.)
     *             Those sets of alternatives contain ex-aequo alternatives.
     * @return the mutable preference, implemented with a transitively closed.
     * @see Voter
     * @see Preference
     * @see MutablePreference
     * @see MutablePreferenceImpl#asGraph()
     */
    public static MutableGraph<Alternative> preferenceGraphMaker(
                    Set<List<Set<Alternative>>> pref) {
        LOGGER.debug("PreferenceImpl preferenceGraphMaker");
        Preconditions.checkNotNull(pref);
        MutableGraph<Alternative> currentgraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        for (List<Set<Alternative>> array : pref) {
            ArrayList<Alternative> tmp = new ArrayList<>();
            for (Set<Alternative> set : array) {
                // in a set of equality, adding every node to the graph
                // and in TMP list
            	for (Alternative alt : set) {
            		tmp.add(alt);
            	}
            	//create edges from every node in TMP to every node in current equality set
            	//If one of them is not in the graph, they are added.
            	for (Alternative alt : tmp) {
            		 for (Alternative alt2 : set) {
            			 currentgraph.putEdge(alt, alt2);
            		 }
            	}
            }
        }
        if (currentgraph.nodes().isEmpty())
            throw new IllegalArgumentException(
                            "Must contain at least one alternative");
        return currentgraph;
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
    
    /**
     * Adds an alternative to the Preference.
     * This alternative is not preferred to any other of the preference, it is being added isolated.
     *
     * @param alternative to add to the preference.
     */
    public void addAlternative(Alternative alternative) {
        LOGGER.debug("MutablePreferenceImpl addAlternative");
        Preconditions.checkNotNull(alternative);
        graph.putEdge(alternative, alternative);
    }
    
    /**
     * Adds an edge from a1 to a2 and from a2 to a1. If one of them is not in the graph, they are added.
     * a1 and a2 are ex-aequo.
     * <p>
     * * Graph is rearranged : a transitive closure is applied to it/
     *
     * @param a1 first alternative
     * @param a2 second alternative
     */
    public void addExAequo(Alternative a1, Alternative a2) {
        LOGGER.debug("MutablePreferenceImpl addExAequo");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        addAlternative(a1);
        addAlternative(a2);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
        graph = Graphs.copyOf(Graphs.transitiveClosure(graph));
    }
    
    /**
     * Adds an edge from a1 to a2, so that a1 is preferred to a2 (a1 > a2).
     * If one of them is not in the graph, they are added.
     * <p>
     * Graph is rearranged : a transitive closure is applied to it/
     *
     * @param a1 preferred alternative to a2
     * @param a2 "lower" alternative
     */
    public void addStrictPreference(Alternative a1, Alternative a2) {
        LOGGER.debug("MutablePreferenceImpl addExAequo");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        addAlternative(a1);
        addAlternative(a2);
        graph.putEdge(a1, a2);
        graph = Graphs.copyOf(Graphs.transitiveClosure(graph));
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
        return asGraph().toString() + "\n" + voter.toString();
    }
    
    /**
     * @return a set of lists of sets :
     * Sets containing alternatives describe ex-aequo alternatives
     * There are ordered by preference in a List.
     * Those lists are in a set if some alternatives are not preferred to other and are not in the same lists
     */
    public Set<List<Set<Alternative>>> asSetlistSet() {
        return null; // TO-DO
    }
}
