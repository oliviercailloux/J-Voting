package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable preference is a preference in which some alternatives can be added,
 * which means some edges (pair of alternatives) can be added to the
 * corresponding graph.<br><br>
 * If an alternative a is strictly better than an alternative b,
 * it is impossible to change this afterwards.
 */
public interface MutablePreference extends Preference {

    /**
     * 
     * @return a MutableGraph of alternatives who represents the preference.
     */
    @Override
    public MutableGraph<Alternative> asGraph();
}
