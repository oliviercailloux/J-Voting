package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.Graph;
import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An antisymmetric preference is a preference who can't contains equal
 * alternatives.
 */
public interface AntiSymmetricPreference extends Preference {

	/**
	 * {@inheritDoc} This graph is antisymmetric.
	 */
	@Override
	Graph<Alternative> asGraph();
}
