package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

class ImmutableAntiSymmetricPreferenceImplTest {

    @Test
    void asImmutableAntisymmetricPreferenceException() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(Alternative.withId(1), Alternative.withId(1));
        graph.putEdge(Alternative.withId(4), Alternative.withId(4));
        graph.putEdge(Alternative.withId(1), Alternative.withId(4));
        graph.putEdge(Alternative.withId(4), Alternative.withId(1));
        assertThrows(IllegalArgumentException.class,
                        () -> ImmutableAntiSymmetricPreferenceImpl
                                        .asImmutableAntiSymmetricPreference(
                                                        Voter.createVoter(2),
                                                        graph));
    }

    @Test
    void asImmutableAntisymmetricPreferenceReflexiveException() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(Alternative.withId(1), Alternative.withId(4));
        graph.putEdge(Alternative.withId(4), Alternative.withId(2));
        graph.putEdge(Alternative.withId(2), Alternative.withId(1));
        assertThrows(IllegalArgumentException.class,
                        () -> ImmutableAntiSymmetricPreferenceImpl
                                        .asImmutableAntiSymmetricPreference(
                                                        Voter.createVoter(2),
                                                        graph));
    }

    @Test
    void asImmutableAntisymmetricPreferenceLoopTest() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(Alternative.withId(1), Alternative.withId(1));
        assertDoesNotThrow(() -> ImmutableAntiSymmetricPreferenceImpl
                        .asImmutableAntiSymmetricPreference(
                                        Voter.createVoter(2), graph));
        graph.putEdge(Alternative.withId(1), Alternative.withId(2));
        graph.putEdge(Alternative.withId(2), Alternative.withId(1));
        assertThrows(IllegalArgumentException.class,
                        () -> ImmutableAntiSymmetricPreferenceImpl
                                        .asImmutableAntiSymmetricPreference(
                                                        Voter.createVoter(2),
                                                        graph));
    }
}
