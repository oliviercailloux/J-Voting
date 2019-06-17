package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public static void main(String[] args) {
        System.out.println("test");
        HashSet<Alternative> set = new HashSet<>();
        HashSet<Alternative> set2 = new HashSet<>();
        set.add(Alternative.withId(1));
        set.add(Alternative.withId(2));
        set2.add(Alternative.withId(3));
        ImmutableSet<Alternative> immutableSet = ImmutableSet.copyOf(set);
        ImmutableSet<Alternative> immutableSet2 = ImmutableSet.copyOf(set2);
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>();
        list.add(immutableSet);
        list.add(immutableSet2);
        ImmutableList<ImmutableSet<Alternative>> prefImmutableList = ImmutableList
                        .copyOf(list);
        createCompletePreferenceImpl(prefImmutableList, Voter.createVoter(3));
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
            Alternative remeberAlternative = null;
            for (Alternative alternative : set) {
                if (!Objects.isNull(lastSetLinker)) {
                    newGraph.putEdge(lastSetLinker, alternative);
                    lastSetLinker = null;
                }
                newGraph.putEdge(alternative, alternative);
                if (!Objects.isNull(remeberAlternative)) {
                    newGraph.putEdge(remeberAlternative, alternative);
                    newGraph.putEdge(alternative, remeberAlternative);
                }
                remeberAlternative = alternative;
            }
            lastSetLinker = remeberAlternative;
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
        return preference.indexOf(a);
    }

    @Override
    public ImmutableSet<Alternative> getAlternative(int n) {
        return preference.get(n);
    }

    @Override
    public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
        return preference;
    }

    @Override
    public void addAlternative(Alternative a, int rank) {
        preference.get(rank).add(a);
        createGraph(preference);
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return graph;
    }
}
