package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.ImmutablePreference;

class ImmutablePreferenceImplTest {

    static ImmutablePreference getApreference() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(Alternative.withId(1), Alternative.withId(1));
        graph.putEdge(Alternative.withId(2), Alternative.withId(2));
        graph.putEdge(Alternative.withId(3), Alternative.withId(3));
        graph.putEdge(Alternative.withId(4), Alternative.withId(4));
        graph.putEdge(Alternative.withId(5), Alternative.withId(5));
        graph.putEdge(Alternative.withId(1), Alternative.withId(4));
        graph.putEdge(Alternative.withId(3), Alternative.withId(2));
        graph.putEdge(Alternative.withId(4), Alternative.withId(1));
        graph.putEdge(Alternative.withId(4), Alternative.withId(3));
        graph.putEdge(Alternative.withId(5), Alternative.withId(3));
        return ImmutablePreferenceImpl
                        .asImmutablePreference(Voter.createVoter(2), graph);
    }

    @Test
    void getAlternativesImmutablePreferenceTest() {
        ImmutablePreference toTestImmutablePreference = getApreference();
        assertEquals(ImmutableSet.of(Alternative.withId(1),
                        Alternative.withId(2), Alternative.withId(3),
                        Alternative.withId(4), Alternative.withId(5)),
                        toTestImmutablePreference.getAlternatives());
    }

    @Test
    void getVoterImmutablePreferenceTest() {
        ImmutablePreference toTestImmutablePreference = getApreference();
        assertEquals(Voter.createVoter(2),
                        toTestImmutablePreference.getVoter());
    }

    @Test
    void asIntransitiveGraph() {
        // Cast will be changed ASAP, need to add a new function in Preference
        // Interface
        ImmutablePreferenceImpl toTest = (ImmutablePreferenceImpl) getApreference();
        ImmutableGraph<Alternative> toTestImmutableGraph = toTest
                        .asIntransitiveGraph();
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(1),
                        Alternative.withId(4)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(1)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(5),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(3),
                        Alternative.withId(2)));
        assertFalse(toTestImmutableGraph.hasEdgeConnecting(
                        Alternative.withId(1), Alternative.withId(5)));
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(2))
                        .size() == 1);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(1))
                        .size() == 2);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(4))
                        .size() == 3);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(5))
                        .size() == 2);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(3))
                        .size() == 2);
    }

    @Test
    void asGraph() {
        ImmutableGraph<Alternative> toTestImmutableGraph = getApreference()
                        .asGraph();
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(1),
                        Alternative.withId(4)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(1)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(4),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(5),
                        Alternative.withId(3)));
        assertTrue(toTestImmutableGraph.hasEdgeConnecting(Alternative.withId(3),
                        Alternative.withId(2)));
        assertFalse(toTestImmutableGraph.hasEdgeConnecting(
                        Alternative.withId(1), Alternative.withId(5)));
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(2))
                        .size() == 1);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(1))
                        .size() == 4);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(4))
                        .size() == 4);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(5))
                        .size() == 3);
        assertTrue(toTestImmutableGraph.successors(Alternative.withId(3))
                        .size() == 2);
    }
}
