package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable preference is a preference in which some alternatives can be added,
 * which means some edges (pair of alternatives) can be added.
 * 
 * If an alternative a is strictly better than an alternative b, it is
 * impossible to change this afterwards.
 */
public interface MutablePreference extends Preference {

	/**
	 * This graph is not necessarily transitively closed and not necessarily
	 * reflexive
	 *
	 * @see MutablePreference#asGraph() TODO : should forbid removing alternative
	 *      from the set of nodes (surprising results when a > b > c and removing b
	 *      as it might disconnect the graph)
	 */
	public MutableGraph<Alternative> asMutableGraph();

	/**
	 * Adds an alternative to the Preference. This alternative is not preferred to
	 * any other of the preference, it is being added isolated.
	 *
	 * @param alternative to add to the preference.
	 */
	public void addAlternative(Alternative alternative);

	/**
	 * Adds an edge from an alternative a1 to an alternative a2 and from a2 to a1.
	 * If one of them is not in the graph, they are added. a1 and a2 are ex-aequo.
	 * 
	 * * Graph is rearranged : a transitive closure is applied to it
	 *
	 * @param a1 first alternative
	 * @param a2 second alternative
	 */
	public void addEquivalence(Alternative a1, Alternative a2);

	/**
	 * Adds an edge from an alternative a1 to an alternative a2, so that a1 is as
	 * least as good as a2 (a1 >= a2). If one of them is not in the graph, they are
	 * added.
	 * 
	 * Graph is rearranged : a transitive closure is applied to it
	 *
	 * @param a1 preferred alternative to a2
	 * @param a2 "lower" alternative
	 */
	public void setAsLeastAsGood(Alternative a1, Alternative a2);
}
