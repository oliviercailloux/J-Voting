package io.github.oliviercailloux.j_voting.preferences;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An immutable preference which represents a complete pre-order, also called a
 * weak order. for each couple of alternatives (a,b) we can find an order a ≥ b
 * or b ≥ a.
 */
public interface ImmutableCompletePreference extends ImmutablePreference {

	/**
	 * The rank of an alternative is one plus the number of alternatives strictly
	 * preferred to it.
	 *
	 * @param a is an <code>Alternative</code>
	 * @return the rank of this alternative (a number between 1 and <i>n</i>) where
	 *         <i>n</i> is the total number of <code>Alternative</code> instances.
	 *
	 * @throws IllegalArgumentException if a is not contained in this preference
	 */
	public int getRank(Alternative a);

	/**
	 *
	 * @param rank at least 1.
	 * @return the <code>Aternative</code> set at this rank. Empty set id there is
	 *         no alternative at this rank.
	 * @throws IllegalArgumentException if rank is less than one.
	 */
	public ImmutableSet<Alternative> getAlternatives(int rank);

	/**
	 *
	 * @return Same data but in an Immutable list object A set of alternative is
	 *         strictly prefered to next sets.
	 *
	 *         All the alternatives in a set are considered ex-aequo.
	 */
	public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses();
}
