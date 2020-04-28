package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12345;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableLinearPreferenceImplTest {
	
	/**
     * Tests whether the preference is correctly expressed as a graph
     */
	@Test
    void testAsGraph() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        
        graph.putEdge(a1,a3);
        
        assertEquals(Graphs.transitiveClosure(graph), pref.asGraph());
    }
	
	/**
     * Tests whether the single alternative is added to the preferences' graph
     */
    @Test
    void testAddAlternative() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        pref.addAlternative(a4);
        graph.putEdge(a3, a4);
        assertEquals(Graphs.transitiveClosure(graph), pref.asGraph());
    }
    
    /**
     * Tests whether the single alternative is removed to the preferences' graph
     */
	@Test
    void testDeleteAlternative() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        
        graph.putEdge(a1, a2);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        pref.addAlternative(a3);
        pref.deleteAlternative(a3);
        assertEquals(Graphs.transitiveClosure(graph), pref.asGraph());
    }
	
//	/**
//     * Tests of the changeOrder method which returns the new preference well
//     */
//	@Test
//    void testChangeOrder() {
//	
//		MutableGraph<Alternative> graph1 = GraphBuilder.directed()
//                .allowsSelfLoops(true).build();
//		MutableGraph<Alternative> graph2 = GraphBuilder.directed()
//                .allowsSelfLoops(true).build();
//
//		graph1.putEdge(a1, a2);
//		graph1.putEdge(a2, a3);
//		
//		graph2.putEdge(a3, a2);
//		graph2.putEdge(a2, a1);
//		
//		MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph1));
//		pref.changeOrder(graph2);
//		assertEquals(Graphs.transitiveClosure(graph2), pref.asGraph());	
//	}
	
	 /**
     * Tests whether method getAlternatives returns a set with all the
     * alternatives of the preference
     */
    @Test
    void testGetAlternatives() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        ImmutableSet<Alternative> expected = a12345;
        assertEquals(expected, pref.getAlternatives());
    }
}
