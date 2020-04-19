package io.github.oliviercailloux.j_voting.preferences.interfaces;

import java.util.Set;
import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable linear preference is a mutable antisymmetric complete preference. A mutable linear
 * preference represents a linear order, or equivalently an antisymmetric
 * complete order, or equivalently, the reduction of a weak-order.
 * 
 */

public interface MutableLinearPreference extends Preference{
	
	/**
	 * Change the order of the alternatives
	 * 
	 */
	public Set<Alternative> changeOrder(Set<Alternative> alternative);
	
	/**
	 * Delete an alternative from the preference
	 * 
	 */
	public void deleteAlternative(Alternative a);
	
	/**
	 * Add an alternative from the preference
	 * 
	 */
	
	public void addAlternative(Alternative a);
}
