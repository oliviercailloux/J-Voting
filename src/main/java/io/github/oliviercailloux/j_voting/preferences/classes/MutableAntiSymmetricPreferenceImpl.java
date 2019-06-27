package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableAntiSymmetricPreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

public class MutableAntiSymmetricPreferenceImpl
                implements MutableAntiSymmetricPreference {

    protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(MutableAntiSymmetricPreference.class.getName());

    private MutableAntiSymmetricPreferenceImpl(
                    MutableGraph<Alternative> prefGraph, Voter voter) {
        this.voter = voter;
        graph = prefGraph;
        alternatives = graph.nodes();
    }

    /**
     * @param pref  is a mutable graph of alternatives representing the
     *              preference. Each node of the graph contains a set, and this
     *              set must contain only one element, to respect the
     *              antisymmetric condition (there s no ex aequo alternatives)
     * @param voter is the Voter associated to the Preference.
     * @return the mutable antisymmetric preference
     * @see Voter
     */
    public static MutableAntiSymmetricPreferenceImpl given(
                    MutableGraph<Alternative> pref, Voter voter) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl given");
        Preconditions.checkNotNull(pref);
        Preconditions.checkNotNull(voter);
        if (Graphs.hasCycle(pref))
            throw new IllegalArgumentException(
                            "Must not contain ex-eaquo Alternative");
        return new MutableAntiSymmetricPreferenceImpl(pref, voter);
    }

    /**
     * @param voter is the Voter associated to the Preference.
     * @return a MutableAntiSymmetricPreference with the same voter and an empty
     *         graph
     */
    public static MutableAntiSymmetricPreferenceImpl given(Voter voter) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl given with voter");
        Preconditions.checkNotNull(voter);
        MutableGraph<Alternative> pref = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        return new MutableAntiSymmetricPreferenceImpl(pref, voter);
    }

    /**
     * @param pref is a set of lists of sets of Alternatives representing the
     *             preference. In the first set, every list is a linear
     *             comparison of sets of alternatives. (first in the list is
     *             preferred to next ones, etc.) Those sets of alternatives
     *             contain ex-aequo alternatives.
     * @return the mutable antisymmetric preference, implemented with a
     *         transitively closed graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference
     * @see MutablePreferenceImpl#asGraph()
     */
    public static MutableGraph<Alternative> preferenceGraphMaker(
                    Set<List<Set<Alternative>>> pref) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl preferenceGraphMaker");
        Preconditions.checkNotNull(pref);
        MutableGraph<Alternative> currentgraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        for (List<Set<Alternative>> array : pref) {
            ArrayList<Alternative> tmp = new ArrayList<>();
            for (Set<Alternative> set : array) {
                // in a set of equality, adding every node to the graph
                // and in TMP list
                if (set.size() != 1)
                    throw new IllegalArgumentException(
                                    "Must not contain ex-eaquo Alternative");
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
     * Factory method making new MutableAntiSymmetricPreference from an other
     * Preference. It creates a new similar graph instance (mutable and
     * anti-symmetric). The voter instance of the created preference is the same
     * as the copied preference.
     *
     * @param pref a Preference
     * @return a copy of this preference as a MutablePreference, with the same
     *         voter.
     */
    public static MutableAntiSymmetricPreferenceImpl given(Preference pref) {
        Preconditions.checkNotNull(pref);
        return new MutableAntiSymmetricPreferenceImpl(
                        Graphs.copyOf(pref.asGraph()), pref.getVoter());
    }

    @Override
    public void addAlternative(Alternative alternative) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl addAlternative");
        Preconditions.checkNotNull(alternative);
        graph.addNode(alternative);
    }

    @Override
    public void putEdge(Alternative a1, Alternative a2) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl putEdge");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        if (Graphs.transitiveClosure(graph).hasEdgeConnecting(a2, a1))
            throw new IllegalArgumentException(
                            "Must not contain ex-eaquo Alternative");
        if (a1.equals(a2))
            throw new IllegalArgumentException(
                            "Must not contain reflexive alternative");
        graph.putEdge(a1, a2);
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        if (Graphs.hasCycle(graph))
            throw new IllegalStateException(
                            "Must not contain ex-eaquo Alternative");
        return ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
    }

    @Override
    public MutableGraph<Alternative> asMutableGraph() {
        if (Graphs.hasCycle(graph))
            throw new IllegalStateException(
                            "Must not contain ex-eaquo Alternative");
        return graph;
    }

    /**
     * This method updates the alternatives corresponding graph
     * 
     * @return the set of all alternatives of the preference
     * 
     */
    @Override
    public Set<Alternative> getAlternatives() {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl getAlternatives");
        if (Graphs.hasCycle(graph))
            throw new IllegalStateException(
                            "Must not contain ex-eaquo Alternative");
        if (alternatives.size() < graph.nodes().size())
            throw new IllegalStateException(
                            "Must not remove an alternative from the set");
        if (alternatives.size() > graph.nodes().size()) {
            if (!alternatives.containsAll(graph.nodes()))
                throw new IllegalStateException(
                                "Must not remove an alternative from the set");
            for (Alternative a : alternatives) {
                if (!graph.nodes().contains(a))
                    graph.addNode(a);
            }
        } else if (alternatives.equals(graph.nodes())
                        && (!alternatives.containsAll(graph.nodes())))
            throw new IllegalStateException(
                            "Must not remove an alternative from the set");
        return alternatives;
    }

    @Override
    public Voter getVoter() {
        return voter;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("graph", graph)
                        .add("voter", voter).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        MutableAntiSymmetricPreferenceImpl pref = (MutableAntiSymmetricPreferenceImpl) obj;
        return (this.asGraph().equals(pref.asGraph())
                        && this.getVoter().equals(pref.getVoter())
                        && this.alternatives.equals(pref.alternatives));
    }
}
