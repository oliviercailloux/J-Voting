package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
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
     * @param voter            <code> not null </code>
     * @param preferencesLists <code> not null </code> Set of listed comparison
     *                         of Alternatives
     * @return new ImmutablePreference
     */
    public static ImmutablePreference asImmutablePreference(Voter voter,
                    Set<List<Alternative>> preferencesLists) {
        LOGGER.debug("ImmutablePreferenceImpl Factory");
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(preferencesLists);
        return new ImmutablePreferenceImpl(voter, preferencesLists);
    }

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> graph with Alternatives ordered
     * @return new ImmutablePreference
     */
    public static ImmutablePreference graphToImmutablePreferenceImpl(
                    Voter voter, Graph<Alternative> graph) {
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(graph);
        return new ImmutablePreferenceImpl(voter, graph);
    }

    /**
     * 
     * @param voter            <code> not null </code>
     * @param preferencesLists <code> not null </code> Set of listed comparison
     *                         of Alternatives
     */
    private ImmutablePreferenceImpl(Voter voter,
                    Set<List<Alternative>> preferencesLists) {
        LOGGER.debug("ImmutablePreferenceImpl constructor");
        MutableGraph<Alternative> tmpGraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        Set<Alternative> tmpSet = Sets.newHashSet();
        for (List<Alternative> list : preferencesLists) {
            if (list.size() != 2)
                throw new IllegalArgumentException(
                                "You must compare 2 Alternatives");
            Alternative a1 = list.get(0);
            Alternative a2 = list.get(1);
            tmpGraph.putEdge(a1, a2);
            tmpGraph.putEdge(a1, a1);
            tmpGraph.putEdge(a2, a2);
            tmpSet.add(a1);
            tmpSet.add(a2);
        }
        this.alternatives = ImmutableSet.copyOf(tmpSet);
        this.graphIntransitivelyClosed = ImmutableGraph.copyOf(tmpGraph);
        this.graph = ImmutableGraph.copyOf(Graphs
                        .transitiveClosure(this.graphIntransitivelyClosed));
        this.voter = voter;
    }

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> graph with Alternatives ordered
     */
    private ImmutablePreferenceImpl(Voter voter, Graph<Alternative> graph) {
        LOGGER.debug("ImmutablePreferenceImpl constructor from graph");
        this.graph = ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
        this.alternatives = ImmutableSet.copyOf(graph.nodes());
        this.graphIntransitivelyClosed = ImmutableGraph.copyOf(graph);
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
