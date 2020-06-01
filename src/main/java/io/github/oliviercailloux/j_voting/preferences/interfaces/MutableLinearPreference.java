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
	 * Moves an existing alternative to the desired rank in the preference.
	 * 
	 * @param alternative that we're going to move in the preference
	 * @param rank desired. The first alternative is at the rank 1. 
	 */
	public void changeOrder(Alternative alternative, int rank);
	
	/**
	 * Removes an existing alternative to the Preference.
	 *
	 * @param alternative which belongs to the preference
	 * @return true 
	 */
	public boolean removeAlternative(Alternative alternative);
	
	/**
	 * Adds an non-existing alternative to the Preference at the last rank. 
	 *
	 * @param alternative to add to the preference.
	 * @return true
	 */
	public boolean addAlternative(Alternative alternative);
	
	/**
	 * This method enables to swap 2 existing alternatives of the preference.
	 * 
	 * @param alternative1 that will change places with alternative2
	 * @param alternative2 that will change places with alternative1
	 */
	public void swap(Alternative alternative1, Alternative alternative2);
	
}
