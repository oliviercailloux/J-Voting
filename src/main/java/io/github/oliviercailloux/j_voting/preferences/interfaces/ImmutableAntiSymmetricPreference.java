package io.github.oliviercailloux.j_voting.preferences.interfaces;

import com.google.common.graph.ImmutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;

/**
 * An immutable antisymmetric preference is an antisymmetric preference
 * (without equal alternatives) we can't modify.
 */
public interface ImmutableAntiSymmetricPreference
                extends ImmutablePreference, AntiSymmetricPreference {
/* This interface combines the ImutablePreference and AntiSymetricPreference
*
* Add here more features if needed
*/
    /**
     * {@inheritDoc}
     * This graph is antisymmetric.
     */
    @Override ImmutableGraph<Alternative> asGraph();
}
