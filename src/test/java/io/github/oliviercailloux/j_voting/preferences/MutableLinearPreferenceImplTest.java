package io.github.oliviercailloux.j_voting.preferences;

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
import io.github.oliviercailloux.j_voting.preferences.classes.MutablePreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;

public class MutableLinearPreferenceImplTest {
	
	
	
	@Test
    void testGiven() {
		//Test du given mais pas obligatoire je pense 
	}
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
        
        graph.putEdge(a1,a1);
        graph.putEdge(a2,a2);
        graph.putEdge(a3,a3);
        
        graph.putEdge(a1,a3);
        
        assertEquals(graph, pref.asGraph());
    }
	
	/**
     * Tests whether the single alternative is added to the preferences' graph
     */
    @Test
    void testAddAlternative() {
        MutableGraph<Alternative> graph = GraphBuilder.directed()
                        .allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        pref.addAlternative(a3);
        assertEquals(graph, pref.asGraph());
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
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        pref.addAlternative(a3);
        pref.deleteAlternative(a3);
        assertEquals(graph, pref.asGraph());
    }
	
	@Test
    void testChangeOrder() {
	
		//a toi de jouer Léo
		
		
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
        graph.putEdge(a5, a5);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), Graphs.copyOf(graph));
        ImmutableSet<Alternative> expected = a12345;
        assertEquals(expected, pref.getAlternatives());
    }
}
