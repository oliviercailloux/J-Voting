package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A linear preference is an antisymmetric complete preference. A linear
 * preference represents a linear order, or equivalently an antisymmetric
 * complete order, or equivalently, the reduction of a weak-order.
 * 
 */
public interface LinearPreference extends CompletePreference {

    /**
     * 
     * @return a sorted list of alternatives corresponding to the preference.
     *
     * Best alternatives coming first in the list.
     */
    public ImmutableList<Alternative> asList();
}
