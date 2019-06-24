package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.base.Preconditions;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.ImmutableAntiSymmetricPreference;

public class ImmutableAntiSymmetricPreferenceImpl
                extends ImmutablePreferenceImpl
                implements ImmutableAntiSymmetricPreference {

    /**
     * 
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> directed graph with ordered
     *              Alternatives
     * @return new ImmutableAntiSymmetricPreference
     */
    public static ImmutableAntiSymmetricPreferenceImpl asImmutableAntiSymmetricPreference(
                    Voter voter, Graph<Alternative> graph) {
        Preconditions.checkNotNull(voter);
        Preconditions.checkNotNull(graph);
        return new ImmutableAntiSymmetricPreferenceImpl(voter, graph);
    }

    /**
     * @param voter <code> not null </code>
     * @param graph <code> not null </code> directed graph with ordered
     *              Alternatives
     */
    private ImmutableAntiSymmetricPreferenceImpl(Voter voter,
                    Graph<Alternative> graph) {
        super(voter, graph);
        for (EndpointPair<Alternative> edge : super.asGraph().edges()) {
            if (super.asGraph().hasEdgeConnecting(edge.nodeU(), edge.nodeV()))
                throw new IllegalArgumentException(
                                "Two Alternatives can't be equals");
        }
    }
}
