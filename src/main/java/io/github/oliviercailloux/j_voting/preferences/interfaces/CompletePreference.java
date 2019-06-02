package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A Complete Preference is is an immutable preference. A complete preference
 * represents a complete pre-order, also called a weak order. for each couple of
 * alternatives (a,b) we can find an order a>=b or b>=a.
 */
public interface CompletePreference extends ImmutablePreference {

    /**
     *
     * @param a is an alternative
     * @return the rank of this alternative.
     */
    public int getRank(Alternative a);

    /**
     *
     * @param n is a rank
     * @return the alternative at this rank
     */
    public Alternative getAlternative(int n);

    /**
     *
     * @return same data but in an Immutable list object
     */
    public ImmutableList<ImmutableSet<Alternative>> asEquivalenceClasses();

    /**
     *
     * @param a    is an alternative to add at position rank
     * @param rank is the rank at which A is added.
     */
    public void addAlternative(Alternative a, int rank);
}
