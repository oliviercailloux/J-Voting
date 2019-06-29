package io.github.oliviercailloux.j_voting.preferences.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableAntiSymmetricPreference;

class MutableAntiSymmetricPreferenceTest {

    /**
     * Tests whether an exception is thrown if there are ex-aequo alternatives
     * in the preference (or equivalently if there is any cycle in the graph)
     */
    @Test
    void givenTest() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a1);
        assertThrows(IllegalArgumentException.class,
                        () -> MutableAntiSymmetricPreferenceImpl
                                        .given(Voter.createVoter(1), graph));
    }

    /**
     * Tests whether the given method with the voter only returns a preference
     * with an empty graph
     */
    @Test
    void testGivenVoter() {
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1));
        assertEquals(pref.asGraph().nodes().size(), 0);
    }

    /**
     * Tests whether the single alternative is added to the preferences' graph
     */
    @Test
    void testAddAlternative() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        pref.addAlternative(a3);
        assertEquals(graph, pref.asGraph());
    }

    /**
     * Tests whether an ordered pair of alternatives is added to the
     * preference's graph
     */
    @Test
    void testAddStrictPreference() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a5, a5);
        graph.putEdge(a3, a5);
        pref.addStrictPreference(a3, a5);
        assertEquals(graph, pref.asGraph());
    }

    /**
     * Tests whether the preference is correctly expressed as a graph
     */
    @Test
    void testAsGraph() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a5, a5);
        graph.putEdge(a3, a5);
        assertEquals(graph, pref.asGraph());
    }

    @Test
    void testAsMutableGraph() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        assertEquals(graph, pref.asMutableGraph());
    }

    /**
     * Tests whether method getAlternatives returns a set with all the
     * alternatives of the preference
     */
    @Test
    void testGetAlternatives() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        Set<Alternative> returnedSet = new HashSet<>();
        returnedSet.add(a1);
        returnedSet.add(a2);
        returnedSet.add(a3);
        returnedSet.add(a4);
        returnedSet.add(a5);
        assertEquals(returnedSet, pref.getAlternatives());
    }
}
