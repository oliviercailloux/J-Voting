package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable preference is a preference in which some alternatives can be added.
 * However, It's not possible to modify the alternatives' order.
 */
public interface MutablePreference extends Preference {

    /**
     * 
     * @return a MutableGraph of alternatives who represents the preference.
     */
    @Override
    public MutableGraph<Alternative> asGraph();
}
