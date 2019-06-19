package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable preference is a preference in which some alternatives can be added,
 * which means some edges (pair of alternatives) can be added.
 * <p>
 * If an alternative a is strictly better than an alternative b, it is
 * impossible to change this afterwards.
 */
public interface MutablePreference extends Preference {
    
    /**
     * {@inheritDoc}
     * <p>
     * The returned graph can't be modified, though is not necessarily immutable.
     *
     * @see MutablePreference#asMutableGraph()
     */
    @Override public ImmutableGraph<Alternative> asGraph();
    
    /**
     * This graph is not necessarily transitively closed and not necessarily reflexive
     *
     * @see MutablePreference#asGraph()
     *  TODO : should forbid removing alternatives from the set of nodes (surprising results when a > b > c and removing b as it might disconnect the graph)
     */
    public MutableGraph<Alternative> asMutableGraph();
}
