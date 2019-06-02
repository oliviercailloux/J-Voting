package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An Immutable Preference is a preference that cannot be modified
 */
public interface ImmutablePreference extends Preference {

    /**
     * @return the Graph corresponding to the Preference
     */
    @Override
    ImmutableGraph<Alternative> asGraph();

    /**
     * @return alternatives's set
     */
    @Override
    ImmutableSet<Alternative> getAlternatives();
}
