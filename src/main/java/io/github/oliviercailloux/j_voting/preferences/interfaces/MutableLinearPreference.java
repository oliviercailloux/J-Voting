package io.github.oliviercailloux.j_voting.preferences.interfaces;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable linear preference is a mutable antisymmetric complete preference. A mutable linear
 * preference represents a linear order, or equivalently an antisymmetric
 * complete order, or equivalently, the reduction of a weak-order.
 * In this preference, it is possible to add alternatives and reorder them
 */

public interface MutableLinearPreference extends Preference {
	
	/**
	 * Moves the alternative to the desired rank.
	 * 
	 * @param alternative that we're going to move in the preference
	 * @param rank desired
	 */
	public void changeOrder(Alternative alternative, int rank);
	
	/**
	 * Remove an alternative to the Preference. This alternative is deleted as well 
	 * as the links between it and the other alternatives.
	 *
	 * @param alternative who belongs to the preference
	 */
	public void deleteAlternative(Alternative alternative);
	
	/**
	 * Adds an alternative to the Preference. Add a link between the "weakest" alternatives and the new
	 *
	 * @param alternative to add to the preference.
	 */
	public void addAlternative(Alternative alternative);
	
	/**
	 * This method enables to swap 2 different alternatives of the preference.
	 * 
	 * @param alternative1 that will change places with alternative2
	 * @param alternative2 that will change places with alternative1
	 */
	public void swap(Alternative alternative1, Alternative alternative2);
}
