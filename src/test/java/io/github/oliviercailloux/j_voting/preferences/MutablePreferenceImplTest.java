package io.github.oliviercailloux.j_voting.preferences;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a34;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a6;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;

class MutablePreferenceImplTest {

    @Test
    void givenTest() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12, a34,
                        ImmutableSet.of(a5));
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        Graph g = pref.asGraph();
        assertEquals(g.nodes().containsAll(Sets.newHashSet(a1, a2, a3, a4, a5)),
                        true);
        assertEquals(g.hasEdgeConnecting(a1, a1) && g.hasEdgeConnecting(a1, a2)
                        && g.hasEdgeConnecting(a1, a3)
                        && g.hasEdgeConnecting(a1, a5)
                        && g.hasEdgeConnecting(a2, a1)
                        && g.hasEdgeConnecting(a2, a2)
                        && g.hasEdgeConnecting(a2, a3)
                        && g.hasEdgeConnecting(a2, a4)
                        && g.hasEdgeConnecting(a2, a5)
                        && g.hasEdgeConnecting(a3, a3)
                        && g.hasEdgeConnecting(a3, a4)
                        && g.hasEdgeConnecting(a3, a5)
                        && g.hasEdgeConnecting(a4, a3)
                        && g.hasEdgeConnecting(a4, a4)
                        && g.hasEdgeConnecting(a4, a5)
                        && !g.hasEdgeConnecting(a5, a1)
                        && !g.hasEdgeConnecting(a5, a2)
                        && !g.hasEdgeConnecting(a5, a3)
                        && !g.hasEdgeConnecting(a5, a4)
                        && !g.hasEdgeConnecting(a4, a1)
                        && !g.hasEdgeConnecting(a4, a2)
                        && !g.hasEdgeConnecting(a3, a2)
                        && !g.hasEdgeConnecting(a3, a1)
                        && g.hasEdgeConnecting(a5, a5), true);
    }

    /**
     * Tests whether the given method with the voter only returns a preference
     * with an empty graph
     */
    @Test
    void testGivenVoter() {
        MutablePreferenceImpl pref = MutablePreferenceImpl
                        .given(Voter.createVoter(1));
        assertEquals(pref.asGraph().nodes().size(), 0);
    }

    /**
     * Tests whether the preference expressed with a set of lists of sets of
     * alternatives is correctly represented as a graph
     */
    @Test
    void testPreferenceGraphMaker() {
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(a12,
                        ImmutableSet.of(a3));
        ArrayList<Set<Alternative>> listTest2 = Lists
                        .newArrayList(ImmutableSet.of(a4), ImmutableSet.of(a5));
        Set<List<Set<Alternative>>> setTest = Set.of(listTest1, listTest2);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a5, a5);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
        graph.putEdge(a1, a3);
        graph.putEdge(a2, a3);
        graph.putEdge(a4, a5);
        assertEquals(graph,
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
    }

    /**
     * Tests whether the preference is correctly copied
     */
    @Test
    void testGivenPreference() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        MutablePreference pref = MutablePreferenceImpl
                        .given(Voter.createVoter(1), graph);
        MutablePreferenceImpl pref1 = MutablePreferenceImpl.given(pref);
        assertEquals(pref, pref1);
    }

    /**
     * Tests whether the preference is correctly expressed as a graph
     */
    @Test
    void testAsGraph() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12,
                        ImmutableSet.of(a3));
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
        graph.putEdge(a1, a3);
        graph.putEdge(a2, a3);
        assertEquals(graph, pref.asGraph());
    }

    @Test
    void testAsMutableGraph() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12,
                        ImmutableSet.of(a3));
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
        graph.putEdge(a1, a3);
        graph.putEdge(a2, a3);
        assertEquals(graph, pref.asMutableGraph());
    }

    /**
     * Tests whether the single alternative is added to the preferences' graph
     */
    @Test
    void testAddAlternative() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12, a34);
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        MutableGraph<Alternative> graph = Graphs.copyOf(pref.asGraph());
        graph.putEdge(a5, a5);
        System.out.print(graph);
        pref.addAlternative(a5);
        assertEquals(graph, pref.asGraph());
    }

    /**
     * Tests whether a couple of ex-aequo alternatives is added to the
     * preferences' graph
     */
    @Test
    void testAddEquivalence() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12, a34);
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        MutableGraph<Alternative> graph = Graphs.copyOf(pref.asGraph());
        graph.putEdge(a5, a5);
        graph.putEdge(a6, a6);
        graph.putEdge(a6, a5);
        graph.putEdge(a5, a6);
        System.out.print(graph);
        pref.addEquivalence(a5, a6);
        assertEquals(graph, pref.asGraph());
    }

    /**
     * Tests whether an ordered pair of alternatives is added to the
     * preference's graph
     */
    @Test
    void testPutEdge() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12, a34);
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        MutableGraph<Alternative> graph = Graphs.copyOf(pref.asGraph());
        graph.putEdge(a5, a5);
        graph.putEdge(a6, a6);
        graph.putEdge(a5, a6);
        System.out.print(graph);
        pref.putEdge(a5, a6);
        assertEquals(graph, pref.asGraph());
    }

    /**
     * Tests whether method getAlternatives returns a set with all the
     * alternatives of the preference
     */
    @Test
    void testGetAlternatives() {
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(a12, a34,
                        ImmutableSet.of(a5));
        Set<List<Set<Alternative>>> setTest = Set.of(listTest);
        MutablePreference pref = MutablePreferenceImpl.given(
                        Voter.createVoter(1),
                        MutablePreferenceImpl.preferenceGraphMaker(setTest));
        ImmutableSet<Alternative> expected = ImmutableSet.of(a1, a2, a3, a4,
                        a5);
        assertEquals(expected, pref.getAlternatives());
    }
}
