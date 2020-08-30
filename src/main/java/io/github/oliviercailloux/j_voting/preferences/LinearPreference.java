package io.github.oliviercailloux.j_voting.preferences;

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
	 * Best alternatives coming first in the list.
	 *
	 * @return a sorted list of alternatives corresponding to the preference.
	 *
	 */
	public ImmutableList<Alternative> asList();
}
