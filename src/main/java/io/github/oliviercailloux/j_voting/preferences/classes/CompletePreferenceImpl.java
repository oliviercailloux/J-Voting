package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

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
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

public class CompletePreferenceImpl implements CompletePreference {

    private ImmutableList<ImmutableSet<Alternative>> equivalenceClasses;
    private Voter voter;
    private ImmutableGraph<Alternative> graph;
    // Je suis pour passer ca en private on a un getter dessus
    ImmutableSet<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(CompletePreferenceImpl.class.getName());

    /**
     * 
     * @param equivalenceClasses <code> not null </code> the best equivalence
     *                           class must be in first position. An alternative
     *                           must be unique
     * @param voter              <code> not null </code>
     * @return new CompletePreference
     * @throws DuplicateValueException if an Alternative is duplicate
     * @throws EmptySetException       if a Set is empty
     */
    public static CompletePreference asCompletePreference(Voter voter,
                    List<? extends Set<Alternative>> equivalenceClasses)
                    throws DuplicateValueException, EmptySetException {
        LOGGER.debug("Factory CompletePreferenceImpl");
        Preconditions.checkNotNull(equivalenceClasses);
        Preconditions.checkNotNull(voter);
        return new CompletePreferenceImpl(voter, equivalenceClasses);
    }

    /**
     * 
     * @param equivalenceClasses <code> not null </code>
     * @param voter              <code> not null </code>
     * @throws EmptySetException
     * @throws DuplicateValueException
     */
    protected CompletePreferenceImpl(Voter voter,
                    List<? extends Set<Alternative>> equivalenceClasses)
                    throws EmptySetException, DuplicateValueException {
        LOGGER.debug("Constructor CompletePreferenceImpl");
        this.voter = voter;
        List<ImmutableSet<Alternative>> listImmutableSets = Lists
                        .newArrayList();
        for (Set<Alternative> set : equivalenceClasses) {
            listImmutableSets.add(ImmutableSet.copyOf(set));
        }
        this.equivalenceClasses = ImmutableList.copyOf(listImmutableSets);
        this.graph = createGraph(equivalenceClasses);
        this.alternatives = ImmutableSet.copyOf(this.graph.nodes());
    }

    private ImmutableGraph<Alternative> createGraph(
                    List<? extends Set<Alternative>> equivalenceClasses)
                    throws EmptySetException, DuplicateValueException {
        List<Alternative> listAlternatives = Lists.newArrayList();
        MutableGraph<Alternative> newGraph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        Alternative lastSetLinker = null;
        for (Set<Alternative> equivalenceClasse : equivalenceClasses) {
            if (equivalenceClasse.isEmpty())
                throw new EmptySetException("A Set can't be empty");
            Alternative rememberAlternative = null;
            for (Alternative alternative : equivalenceClasse) {
                if (listAlternatives.contains(alternative))
                    throw new DuplicateValueException(
                                    "you can't duplicate Alternatives");
                listAlternatives.add(alternative);
                if (lastSetLinker != null) {
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
        return ImmutableGraph.copyOf(Graphs.transitiveClosure(newGraph));
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives() {
        return this.alternatives;
    }

    @Override
    public Voter getVoter() {
        return this.voter;
    }

    @Override
    public int getRank(Alternative a) {
        Preconditions.checkNotNull(a);
        // Pour le coup je mettrais plutot ImmutableSet à la place de Set ici
        for (Set<Alternative> equivalenceClasse : equivalenceClasses) {
            if (equivalenceClasse.contains(a))
                return equivalenceClasses.indexOf(equivalenceClasse) + 1;
        }
        throw new NoSuchElementException("Alternative not found");
    }

    @Override
    public ImmutableSet<Alternative> getAlternatives(int rank) {
        return ImmutableSet.copyOf(equivalenceClasses.get(rank - 1));
    }

    @Override
    public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses() {
        return equivalenceClasses;
    }

    @Override
    public ImmutableGraph<Alternative> asGraph() {
        return graph;
    }
    
    
    /**
     * @param alternative <code>not null</code>
     * @return whether the preference contains the alternative given as
     *         parameter
     */
    public boolean contains(Alternative alternative) {
        Preconditions.checkNotNull(alternative);
        return (this.alternatives.contains(alternative));
    }
    
    /**
     * @param otherInstance <code>not null</code>
     * @return whether the preferences are about the same alternatives exactly
     *         (not necessarily in the same order).
     */
    /*
    public boolean hasSameAlternatives(CompletePreferenceImpl otherInstance) {
        Preconditions.checkNotNull(otherInstance);
        return (this.isIncludedIn(otherInstance) && otherInstance.isIncludedIn(this));
    } //necessite isIncludedIn pour tourner
    */

    /**
     * @param p <code>not null</code>
     * @return whether all the alternatives of the calling preference are included in the given preference.
     */
    public boolean isIncludedIn(CompletePreferenceImpl p) {
        Preconditions.checkNotNull(p);
        Set inputPrefAlternatives = p.getAlternatives();
        for (Alternative alter : this.alternatives) {
            if (!inputPrefAlternatives.contains(alter)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return the preference number of alternatives
     */
    // Alors ca me parait un peu facile lol
    public int alternativeNumber() {
        return this.alternatives.size();
    }

    /**
     *
     * @return true if the Preference is Strict (without several alternatives
     *         having the same rank)
     */
    public boolean isStrict() {
        return (this.equivalenceClasses.size() == this.alternativeNumber());
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(equivalenceClasses, graph, voter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompletePreferenceImpl)) {
            return false;
        }
        CompletePreferenceImpl other = (CompletePreferenceImpl) obj;
        return Objects.equals(equivalenceClasses, other.equivalenceClasses)
                        && Objects.equals(graph, other.graph)
                        && Objects.equals(voter, other.voter);
    }
}
