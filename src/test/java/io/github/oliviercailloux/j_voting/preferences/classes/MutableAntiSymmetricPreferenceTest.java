package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12345;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.MutableAntiSymmetricPreference;

class MutableAntiSymmetricPreferenceTest {

    /**
     * Tests whether an exception is thrown if there are ex-aequo alternatives
     * in the preference (or equivalently if there is any cycle in the graph)
     */
    @Test
    void givenTest() {
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
     * Tests whether an exception is thrown if we add an edge in order to have
     * ex-aequo alternatives
     */
    @Test
    void testAddStrictPreferenceException() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        graph.putEdge(a3, a5);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        assertThrows(IllegalArgumentException.class,
                        () -> pref.addStrictPreference(a5, a3));
    }

    /**
     * Tests whether the preference is correctly expressed as a graph
     */
    @Test
    void testAsGraph() {
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
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableAntiSymmetricPreference pref = MutableAntiSymmetricPreferenceImpl
                        .given(Voter.createVoter(1), Graphs.copyOf(graph));
        ImmutableSet<Alternative> expected = a12345;
        assertEquals(expected, pref.getAlternatives());
    }
}
