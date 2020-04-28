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
	 * Change the order of the alternatives. Check if there is a cycle, if all the alternatives are comparable two-by-two
	 * and if all alternatives are not equals.
	 * 
	 * @param alternative that we're going to move in the preference
	 * @param rank of the alternative
	 */
	public void changeOrder(Alternative alternative, int rank);
	
	/**
	 * Remove an alternative to the Preference. This alternative is deleted as well 
	 * as the links between it and the other alternatives.
	 *
	 * @param alternative to remove to the preference.
	 */
	public void deleteAlternative(Alternative alternative);
	
	/**
	 * Adds an alternative to the Preference. Add a link between the "weakest" alternatives and the new
	 *
	 * @param alternative to add to the preference.
	 */
	public void addAlternative(Alternative alternative);
	
	/**
	 * This method enables to swap 2 alternatives in the LinkedList<Alternative> and the MutableGraph<Alternative>
	 * 
	 * @param alternative1 that will change places with alternative2
	 * @param alternative2 that will change places with alternative1
	 */
	public void swap(Alternative alternative1, Alternative alternative2);
}
