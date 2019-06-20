package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * A mutable antisymmetric preference is an antisymmetric preference (without
 * ex-aquo alternatives) in which some alternatives and edges can be added.
 */
public interface MutableAntiSymmetricPreference extends AntiSymmetricPreference {

	/**
	 * This graph is not necessarily transitively closed and not necessarily
	 * reflexive
	 *
	 * @see MutableAntiSymmetricPreference#asGraph() TODO : should forbid removing
	 *      alternative from the set of nodes (surprising results when a > b > c and
	 *      removing b as it might disconnect the graph)
	 */
	public MutableGraph<Alternative> asMutableGraph();

	/**
	 * Adds an alternative to the Preference. This alternative is not preferred to
	 * any other of the preference, it is being added isolated.
	 *
	 * @param alternative to add to the preference.
	 */
	public void addAlternative(Alternative a);

	/**
	 * Adds an edge from a1 to a2, so that a1 is preferred to a2 (a1 > a2). If one
	 * of them is not in the graph, they are added.
	 *
	 * Graph is rearranged : a transitive closure is applied to it
	 *
	 * @param a1 preferred alternative to a2
	 * @param a2 "lower" alternative
	 */
	public void putEdge(Alternative a1, Alternative a2);
}
