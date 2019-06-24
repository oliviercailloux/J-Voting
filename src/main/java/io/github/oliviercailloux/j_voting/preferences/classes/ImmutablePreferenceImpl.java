package io.github.oliviercailloux.j_voting.preferences.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

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
     * @param graph <code> not null </code> graph with ordered Alternatives
     * @return new ImmutablePreference
     */
    public static ImmutablePreference asImmutablePreference(Voter voter,
                    Graph<Alternative> graph) {
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(graph);
        return new ImmutablePreferenceImpl(voter, graph);
    }

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> graph with ordered Alternatives
     */
    private ImmutablePreferenceImpl(Voter voter, Graph<Alternative> graph) {
        LOGGER.debug("ImmutablePreferenceImpl constructor from graph");
        this.graphIntransitivelyClosed = ImmutableGraph
                        .copyOf(createGraph(graph));
        this.graph = ImmutableGraph.copyOf(Graphs
                        .transitiveClosure(this.graphIntransitivelyClosed));
        this.alternatives = ImmutableSet.copyOf(graph.nodes());
        this.voter = voter;
    }

    /**
     * Ensure the graph is directed and selfLooped
     * 
     * @param graph <code> not null </code> graph with ordered Alternatives
     * @return Graph<Alternative> directed and selfLooped
     */
    private Graph<Alternative> createGraph(Graph<Alternative> graph) {
        MutableGraph<Alternative> tmpGraph = Graphs.copyOf(graph);
        tmpGraph.isDirected();
        for (Alternative alternative : graph.nodes())
            tmpGraph.putEdge(alternative, alternative);
        return tmpGraph;
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
