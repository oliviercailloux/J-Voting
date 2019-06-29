package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

/**
 * Implements MutablePreference interface.
 *
 * The structure of a MutablePreference is a MutableGraph in which an edge
 * represents the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class MutablePreferenceImpl implements MutablePreference {

    protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(MutablePreference.class.getName());

    private MutablePreferenceImpl(Voter voter,
                    MutableGraph<Alternative> prefGraph) {
        this.voter = voter;
        graph = prefGraph;
        alternatives = graph.nodes();
    }

    /**
     * @param pref  is a mutable graph of alternatives representing the
     *              preference. Each node of the graph contains a set, if this
     *              set contains several alternatives, it means that those
     *              alternatives are ex-aequo
     * @param voter is the Voter associated to the Preference.
     * @return the mutable preference
     * @see Voter
     */
    public static MutablePreferenceImpl given(Voter voter,
                    MutableGraph<Alternative> pref) {
        LOGGER.debug("MutablePreferenceImpl given");
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(pref);
        return new MutablePreferenceImpl(voter, pref);
    }

    /**
     * @param voter is the Voter associated to the Preference.
     * @return a MutablePreference with the same voter and an empty graph
     */
    public static MutablePreferenceImpl given(Voter voter) {
        LOGGER.debug("MutablePreferenceImpl given with voter");
        Preconditions.checkNotNull(voter);
        MutableGraph<Alternative> pref = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        return new MutablePreferenceImpl(voter, pref);
    }

    /**
     * @param pref is a set of lists of sets of Alternatives representing the
     *             preference. In the first set, every list is a linear
     *             comparison of sets of alternatives. (first in the list is
     *             preferred to next ones, etc.) Those sets of alternatives
     *             contain ex-aequo alternatives.
     * @return the mutable preference, implemented with a transitively closed
     *         graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference
     * @see MutablePreferenceImpl#asGraph()
     */
    public static MutableGraph<Alternative> preferenceGraphMaker(
                    Set<List<Set<Alternative>>> pref) {
        LOGGER.debug("MutablePreferenceImpl preferenceGraphMaker");
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
                // create edges from every node in TMP to every node in current
                // equality set
                // If one of them is not in the graph, they are added.
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
     * Factory method making new MutablePreference from an other Preference. It
     * creates a new similar graph instance (mutable). The voter instance of the
     * created preference is the same as the copied preference.
     *
     * @param pref a Preference
     * @return a copy of this preference as a MutablePreference, with the same
     *         voter.
     */
    public static MutablePreferenceImpl given(Preference pref) {
        Preconditions.checkNotNull(pref);
        return new MutablePreferenceImpl(pref.getVoter(),
                        Graphs.copyOf(pref.asGraph()));
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
    }

    @Override
    public MutableGraph<Alternative> asMutableGraph() {
        return graph;
    }

    @Override
    public void addAlternative(Alternative alternative) {
        LOGGER.debug("MutablePreferenceImpl addAlternative");
        Preconditions.checkNotNull(alternative);
        graph.addNode(alternative);
    }

    @Override
    public void addEquivalence(Alternative a1, Alternative a2) {
        LOGGER.debug("MutablePreferenceImpl addEquivalence");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
    }

    @Override
    public void putEdge(Alternative a1, Alternative a2) {
        LOGGER.debug("MutablePreferenceImpl putEdge");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        graph.putEdge(a1, a2);
    }

    /**
     * This method updates the alternatives corresponding graph
     * 
     * @return the set of all alternatives of the preference
     * 
     */
    @Override
    public Set<Alternative> getAlternatives() {
        return ImmutableSet.copyOf(alternatives);
    }

    @Override
    public Voter getVoter() {
        return voter;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("voter", voter)
                        .add("graph", graph).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MutablePreferenceImpl)) {
            return false;
        }
        MutablePreferenceImpl other = (MutablePreferenceImpl) obj;
        return Objects.equals(voter, other.voter)
                        && Objects.equals(graph, other.graph);
    }
}
