package io.github.oliviercailloux.j_voting.preferences.classes;

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
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableAntiSymmetricPreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

public class MutableAntiSymmetricPreferenceImpl
                implements MutableAntiSymmetricPreference {

    protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(MutableAntiSymmetricPreference.class.getName());

    private MutableAntiSymmetricPreferenceImpl(Voter voter,
                    MutableGraph<Alternative> prefGraph) {
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
    public static MutableAntiSymmetricPreference given(Voter voter,
                    MutableGraph<Alternative> pref) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl given");
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(pref);
        if (Graphs.hasCycle(pref))
            throw new IllegalArgumentException(
                            "Must not contain ex-eaquo Alternative");
        return new MutableAntiSymmetricPreferenceImpl(voter, pref);
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
        return new MutableAntiSymmetricPreferenceImpl(voter, pref);
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
        return new MutableAntiSymmetricPreferenceImpl(pref.getVoter(),
                        Graphs.copyOf(pref.asGraph()));
    }

    @Override
    public void addAlternative(Alternative alternative) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl addAlternative");
        Preconditions.checkNotNull(alternative);
        graph.addNode(alternative);
    }

    @Override
    public void addStrictPreference(Alternative a1, Alternative a2) {
        LOGGER.debug("MutableAntiSymmetricPreferenceImpl addStrictPreference");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        if (Graphs.transitiveClosure(graph).hasEdgeConnecting(a2, a1))
            throw new IllegalArgumentException(
                            "Must not contain ex-eaquo Alternative");
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
        if (!(obj instanceof MutableAntiSymmetricPreferenceImpl)) {
            return false;
        }
        MutableAntiSymmetricPreferenceImpl other = (MutableAntiSymmetricPreferenceImpl) obj;
        return Objects.equals(voter, other.voter)
                        && Objects.equals(graph, other.graph);
    }
}
