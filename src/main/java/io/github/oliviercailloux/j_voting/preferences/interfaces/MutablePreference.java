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
	 * The returned graph can't be modified, though is not necessarily immutable.
	 *
	 *
	 */
	@Override
	public MutableGraph<Alternative> asGraph();

	/**
	 * The returned graph reads through this object: this object is mutable so any
	 * modification to this object modifies the returned graph, and conversely. The
	 * graph is not necessarily transitively closed, neither not necessarily
	 * reflexive
	 *
	 *
	 * This graph is mutable.
	 */
	// public MutableGraph<Alternative> asMutableGraph();

}
