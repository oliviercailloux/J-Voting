package io.github.oliviercailloux.j_voting.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;

class TestMutablePreferenceImpl {

    @Test
    void testGivenMutableGraphOfAlternativeVoter() {
        fail("Not yet implemented");
    }

    /**
     * Tests whether the preference expressed with a set of lists of sets of
     * alternatives is correctly represented as a graph
     */
    @Test
    void testPreferenceGraphMaker() {
        // Alternative a1 = Alternative.withId(1);
        // Alternative a2 = Alternative.withId(2);
        // Alternative a3 = Alternative.withId(3);
        // Alternative a4 = Alternative.withId(4);
        // Alternative a5 = Alternative.withId(5);
        // Set<Alternative> A = Sets.newHashSet(a1, a2);
        // Set<Alternative> B = Sets.newHashSet(a3);
        // Set<Alternative> C = Sets.newHashSet(a4);
        // Set<Alternative> D = Sets.newHashSet(a5);
        // ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        // ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        // Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        // setTest.add(listTest1);
        // setTest.add(listTest2);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(),
                        Voter.createVoter(1));
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
        assertEquals(graph, pref.preferenceGraphMaker());
    }

    @Test
    void testGivenPreference() {
        fail("Not yet implemented");
    }

    /**
     * Tests whether the preference is correctly expressed as a graph
     */
    @Test
    void testAsGraph() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3, a4);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Alternative a6 = Alternative.withId(6);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3, a4);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Alternative a6 = Alternative.withId(6);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3, a4);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> A = Sets.newHashSet(a1, a2);
        Set<Alternative> B = Sets.newHashSet(a3, a4);
        Set<Alternative> C = Sets.newHashSet(a5);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B, C);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutablePreferenceImpl pref = MutablePreferenceImpl.given(
                        MutablePreferenceImpl.preferenceGraphMaker(setTest),
                        Voter.createVoter(1));
        Set<Alternative> returnedSet = new HashSet<>();
        returnedSet.add(a1);
        returnedSet.add(a2);
        returnedSet.add(a3);
        returnedSet.add(a4);
        returnedSet.add(a5);
        assertEquals(returnedSet, pref.getAlternatives());
    }

    @Test
    void testGetVoter() {
        fail("Not yet implemented");
    }

    @Test
    void testToString() {
        fail("Not yet implemented");
    }
}
