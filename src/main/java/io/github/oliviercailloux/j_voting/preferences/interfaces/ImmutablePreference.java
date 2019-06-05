package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An Immutable Preference is a preference that cannot be modified
 */
public interface ImmutablePreference extends Preference {
    
    /**
     * {@inheritDoc}
     *
     * This graph is immutable.
     *
     * @return the Graph corresponding to the Preference.
     */
    @Override
    ImmutableGraph<Alternative> asGraph();
    
    /**
     * {@inheritDoc}
     * This set is immutable.
     */
    @Override
    ImmutableSet<Alternative> getAlternatives();
}
