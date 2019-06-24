package io.github.oliviercailloux.j_voting.preferences.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.ImmutablePreference;

public class ImmutablePreferenceImpl implements ImmutablePreference {

    ImmutableGraph<Alternative> graph;
    ImmutableGraph<Alternative> graphIntransitivelyClosed;
    ImmutableSet<Alternative> alternatives;
    Voter voter;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ImmutablePreferenceImpl.class.getName());

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> directed graph with ordered
     *              Alternatives
     * @return new ImmutablePreference
     */
    public static ImmutablePreference asImmutablePreference(Voter voter,
                    Graph<Alternative> graph) {
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(graph);
        graph.isDirected();
        return new ImmutablePreferenceImpl(voter, graph);
    }

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> directed graph with ordered
     *              Alternatives
     */
    private ImmutablePreferenceImpl(Voter voter, Graph<Alternative> graph) {
        LOGGER.debug("ImmutablePreferenceImpl constructor from graph");
        this.graphIntransitivelyClosed = ImmutableGraph.copyOf(graph);
        this.graph = ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
        this.alternatives = ImmutableSet.copyOf(graph.nodes());
        this.voter = voter;
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return this.graph;
    }

    public ImmutableGraph<Alternative> asIntransitiveGraph() {
        return this.graphIntransitivelyClosed;
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives() {
        return this.alternatives;
    }

    @Override
    public Voter getVoter() {
        return this.voter;
    }
}
