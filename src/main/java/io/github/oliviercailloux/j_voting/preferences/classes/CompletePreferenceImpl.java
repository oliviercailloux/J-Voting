package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

public class CompletePreferenceImpl implements CompletePreference {

    ImmutableList<ImmutableSet<Alternative>> preference;
    Voter voter;
    ImmutableGraph<Alternative> graph;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(CompletePreferenceImpl.class.getName());

    /**
     * 
     * @param preference : <code> not null </code>
     * @param voter      : <code> not null </code>
     * @return new CompletePreferenceImpl
     */
    public static CompletePreferenceImpl createCompletePreferenceImpl(
                    ImmutableList<ImmutableSet<Alternative>> preference,
                    Voter voter) {
        LOGGER.debug("Factory CompletePreferenceImpl");
        Preconditions.checkNotNull(preference);
        Preconditions.checkNotNull(voter);
        return new CompletePreferenceImpl(preference, voter);
    }

    /**
     * 
     * @param preference : <code> not null </code>
     * @param voter      : <code> not null </code>
     */
    private CompletePreferenceImpl(
                    ImmutableList<ImmutableSet<Alternative>> preference,
                    Voter voter) {
        LOGGER.debug("Constructor CompletePreferenceImpl");
        this.voter = voter;
        this.preference = preference;
        this.graph = createGraph(preference);
    }

    private ImmutableGraph<Alternative> createGraph(
                    ImmutableList<ImmutableSet<Alternative>> preference) {
        MutableGraph<Alternative> newGraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        Alternative lastSetLinker = null;
        for (ImmutableSet<Alternative> set : preference) {
            Alternative rememberAlternative = null;
            for (Alternative alternative : set) {
                if (!Objects.isNull(lastSetLinker)) {
                    newGraph.putEdge(lastSetLinker, alternative);
                    lastSetLinker = null;
                }
                newGraph.putEdge(alternative, alternative);
                if (!Objects.isNull(rememberAlternative)) {
                    newGraph.putEdge(rememberAlternative, alternative);
                    newGraph.putEdge(alternative, rememberAlternative);
                }
                rememberAlternative = alternative;
            }
            lastSetLinker = rememberAlternative;
        }
        ;
        System.out.println(Graphs.transitiveClosure(newGraph).toString());
        return ImmutableGraph.copyOf(newGraph);
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives() {
        Set<Alternative> returnedSet = new HashSet<>();
        for (ImmutableSet<Alternative> set : preference) {
            for (Alternative alternative : set) {
                returnedSet.add(alternative);
            }
        }
        return ImmutableSet.copyOf(returnedSet);
    }

    @Override
    public Voter getVoter() {
        return this.voter;
    }

    @Override
    public int getRank(Alternative a) {
        Preconditions.checkNotNull(a);
        for (ImmutableSet<Alternative> set : preference) {
            if (set.contains(a))
                return preference.indexOf(set) + 1;
        }
        throw new NotFoundException("Alternative not found");
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives(int rank) {
        Preconditions.checkArgument(rank > 0);
        return preference.get(rank - 1);
    }

    @Override
    public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
        return preference;
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return graph;
    }
}
