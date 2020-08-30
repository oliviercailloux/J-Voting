package io.github.oliviercailloux.j_voting.preferences;

import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;

public interface MutableAntiSymmetricPreference extends AntiSymmetricPreference {

	/**
	 * This graph is not necessarily transitively closed. This method might refuse
	 * that an edge be added that would result in the transitive closure violating
	 * anti-symmetry, though it will not necessarily check (but it is guaranteed to
	 * check if asGraph() has been called previously). If such a situation happens,
	 * and is not detected (thus implying that asGraph() has never been called), an
	 * exception will be raised the first time asGraph() will be called, unless
	 * possibly the transitive closure no more violates anti-symmetry at that time
	 * (behavior is undefined in such a case).
	 *
	 * This graph is irreflexive and may thus be considered as representing a
	 * (possibly partly reduced) strict preference relation. The convention that
	 * this graph be reflexive would not work: the method nodes().add() would
	 * possibly have to create a reflexive edge, which would be surprising.
	 *
	 */
	public MutableGraph<Alternative> asMutableGraph();

	/**
	 * Adds an alternative to the Preference if it does not exist already. This
	 * alternative is not preferred to any other of the preference, it is being
	 * added isolated.
	 *
	 * @param alternative to add to the preference.
	 */
	public boolean addAlternative(Alternative a);

	/**
	 * Adds an edge from a1 to a2, so that a1 is preferred to a2 (a1 > a2), if the
	 * edge does not exist yet. If one of them is not in the graph, they are added.
	 *
	 * @param a1 preferred alternative to a2
	 * @param a2 "lower" alternative
	 */
	public boolean addStrictPreference(Alternative a1, Alternative a2);
}
