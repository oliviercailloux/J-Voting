package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable linear preference is a mutable antisymmetric complete preference. A mutable linear
 * preference represents a linear order, or equivalently an antisymmetric
 * complete order, or equivalently, the reduction of a weak-order.
 * 
 */

public interface MutableLinearPreference extends Preference {
	
	/**
	 * Change the order of the alternatives. 
	 * 
	 * @param newGraph to change the old preference to the new one with an alternative graph.
	 */
	public void changeOrder(MutableGraph<Alternative> newGraph);
	
	/**
	 * Remove an alternative to the Preference. This alternative is deleted as well 
	 * as the links between it and the other alternatives.
	 *
	 * @param alternative to remove to the preference.
	 */
	public void deleteAlternative(Alternative alternative);
	
	/**
	 * Adds an alternative to the Preference. This alternative is not preferred to
	 * any other of the preference, it is being added isolated.
	 *
	 * @param alternative to add to the preference.
	 */
	
	public void addAlternative(Alternative alternative);
}
