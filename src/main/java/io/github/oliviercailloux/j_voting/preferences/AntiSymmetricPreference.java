package io.github.oliviercailloux.j_voting.preferences;

import com.google.common.graph.Graph;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An anti-symmetric preference is a preference who can't contains equal
 * alternatives.
 */
public interface AntiSymmetricPreference extends Preference {

	/**
	 * {@inheritDoc} This graph is anti-symmetric.
	 */
	@Override
	Graph<Alternative> asGraph();
}
