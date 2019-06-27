package io.github.oliviercailloux.j_voting.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableAntiSymmetricPreferenceImpl;

class MutableAntiSymmetricPreferenceTest {

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
                        () -> MutableAntiSymmetricPreferenceImpl.given(graph,
                                        Voter.createVoter(1)));
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
     * Tests whether the preference expressed with a set of lists of sets of
     * alternatives is correctly represented as a graph
     */
    @Test
    void testPreferenceGraphMaker() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        Set<Alternative> C = Sets.newHashSet(a3);
        Set<Alternative> D = Sets.newHashSet(a4);
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest1);
        setTest.add(listTest2);
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        assertEquals(graph, MutableAntiSymmetricPreferenceImpl
                        .preferenceGraphMaker(setTest));
    }

    @Test
    void testAddAlternative() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        ArrayList<Set<Alternative>> listTest = Lists.newArrayList(A, B);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest);
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(MutableAntiSymmetricPreferenceImpl
                                        .preferenceGraphMaker(setTest),
                                        Voter.createVoter(1));
        // System.out.println(pref.asGraph());
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a1, a2);
        pref.addAlternative(a3);
        // System.out.println(graph);
        assertEquals(graph, pref.asGraph());
    }

    @Test
    void testPutEdge() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        Set<Alternative> C = Sets.newHashSet(a3);
        Set<Alternative> D = Sets.newHashSet(a4);
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest1);
        setTest.add(listTest2);
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(MutableAntiSymmetricPreferenceImpl
                                        .preferenceGraphMaker(setTest),
                                        Voter.createVoter(1));
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a3, a4);
        graph.putEdge(a3, a5);
        graph.putEdge(a5, a5);
        pref.putEdge(a3, a5);
        assertEquals(graph, pref.asGraph());
    }

    @Test
    void testAsGraph() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        Set<Alternative> C = Sets.newHashSet(a3);
        Set<Alternative> D = Sets.newHashSet(a4);
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest1);
        setTest.add(listTest2);
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(MutableAntiSymmetricPreferenceImpl
                                        .preferenceGraphMaker(setTest),
                                        Voter.createVoter(1));
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        assertEquals(graph, pref.asGraph());
    }

    @Test
    void testAsMutableGraph() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        Set<Alternative> C = Sets.newHashSet(a3);
        Set<Alternative> D = Sets.newHashSet(a4);
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest1);
        setTest.add(listTest2);
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(MutableAntiSymmetricPreferenceImpl
                                        .preferenceGraphMaker(setTest),
                                        Voter.createVoter(1));
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        graph.putEdge(a4, a4);
        graph.putEdge(a1, a2);
        graph.putEdge(a3, a4);
        assertEquals(graph, pref.asMutableGraph());
    }

    @Test
    void testGetAlternatives() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Set<Alternative> A = Sets.newHashSet(a1);
        Set<Alternative> B = Sets.newHashSet(a2);
        Set<Alternative> C = Sets.newHashSet(a3);
        Set<Alternative> D = Sets.newHashSet(a4);
        ArrayList<Set<Alternative>> listTest1 = Lists.newArrayList(A, B);
        ArrayList<Set<Alternative>> listTest2 = Lists.newArrayList(C, D);
        Set<List<Set<Alternative>>> setTest = Sets.newHashSet();
        setTest.add(listTest1);
        setTest.add(listTest2);
        MutableAntiSymmetricPreferenceImpl pref = MutableAntiSymmetricPreferenceImpl
                        .given(MutableAntiSymmetricPreferenceImpl
                                        .preferenceGraphMaker(setTest),
                                        Voter.createVoter(1));
        Set<Alternative> returnedSet = new HashSet<>();
        returnedSet.add(a1);
        returnedSet.add(a2);
        returnedSet.add(a3);
        returnedSet.add(a4);
        assertEquals(returnedSet, pref.getAlternatives());
    }
}
