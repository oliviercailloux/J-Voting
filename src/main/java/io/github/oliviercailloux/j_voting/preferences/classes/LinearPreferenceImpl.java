package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

public class LinearPreferenceImpl implements LinearPreference {

    private ImmutableList<Alternative> equivalenceClasses;
    private Voter voter;
    private ImmutableGraph<Alternative> graph;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(LinearPreferenceImpl.class.getName());

    /**
     * 
     * @param voter              <code> not null </code>
     * @param equivalenceClasses <code> not null </code> the best equivalence
     *                           class must be in first position. An alternative
     *                           must be unique
     * @return new LinearPreference
     * @throws DuplicateValueException if an Alternative is duplicated
     * 
     */
    public static LinearPreference asLinearPreference(Voter voter,
                    List<Alternative> equivalenceClasses)
                    throws DuplicateValueException {
        LOGGER.debug("Factory LinearPreferenceImpl");
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(equivalenceClasses);
        for (Alternative alter : equivalenceClasses) {
            Boolean frequence = Collections.frequency(equivalenceClasses,
                            alter) > 1;
            if (frequence)
                throw new DuplicateValueException(
                                "You can't duplicate alternatives");
        }
        return new LinearPreferenceImpl(voter, equivalenceClasses);
    }

    /**
     * 
     * @param voter              <code> not null </code>
     * @param equivalenceClasses <code> not null </code>
     */
    private LinearPreferenceImpl(Voter voter,
                    List<Alternative> equivalenceClasses) {
        LOGGER.debug("Constructor LinearPreferenceImpl");
        this.voter = voter;
        this.equivalenceClasses = ImmutableList.copyOf(equivalenceClasses);
        this.graph = createGraph(equivalenceClasses);
    }

    private ImmutableGraph<Alternative> createGraph(
                    List<Alternative> equivalenceClasses) {
        MutableGraph<Alternative> newGraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        Alternative rememberAlternative = null;
        for (Alternative alternative : equivalenceClasses) {
            newGraph.putEdge(alternative, alternative);
            if (!Objects.isNull(rememberAlternative)) {
                newGraph.putEdge(rememberAlternative, alternative);
            }
            rememberAlternative = alternative;
        }
        return ImmutableGraph.copyOf(Graphs.transitiveClosure(newGraph));
    }

    @Override
    public int getRank(Alternative a) {
        Preconditions.checkNotNull(a);
        if (equivalenceClasses.contains(a))
            return equivalenceClasses.indexOf(a) + 1;
        throw new NoSuchElementException("Alternative not found");
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives(int rank) {
        return ImmutableSet.of(equivalenceClasses.get(rank));
    }

    @Override
    public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
        List<ImmutableSet<Alternative>> returnedList = Lists.newArrayList();
        for (Alternative alternative : equivalenceClasses) {
            returnedList.add(ImmutableSet.of(alternative));
        }
        return ImmutableList.copyOf(returnedList);
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return this.graph;
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives() {
        return ImmutableSet.copyOf(equivalenceClasses);
    }

    @Override
    public Voter getVoter() {
        return this.voter;
    }

    @Override
    public ImmutableList<Alternative> asList() {
        return this.equivalenceClasses;
    }
}
