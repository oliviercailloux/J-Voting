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
     * Tests whether the single alternative is added to the preferences
     */
    @Test
    void testAddAlternative() {
    	Voter v = Voter.createVoter(1);
    	MutableGraph<Alternative> toTestGraph = GraphBuilder.directed().allowsSelfLoops(true).build();
        toTestGraph.putEdge(a1, a2);
        toTestGraph.putEdge(a2, a3);
        MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestGraph);
        
        MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        graph.putEdge(a3, a4);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(v,graph);

        toTestPref.addAlternative(a4);
        assertEquals(toTestPref,pref);
    }
    
    /**
     * Tests whether the single alternative is removed to the preferences
     */
	@Test
    void testDeleteAlternative() {
		Voter v = Voter.createVoter(1);
        MutableGraph<Alternative> toTestGraph = GraphBuilder.directed().allowsSelfLoops(true).build();
        toTestGraph.putEdge(a1, a2);
        toTestGraph.putEdge(a2, a3);
        MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestGraph);
        
        MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.putEdge(a1, a3);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(v, graph);

        toTestPref.deleteAlternative(a2);
        assertEquals(toTestPref,pref);
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
    
    /**
 	* Test the 7 cases of the swap method <br/>
 	* a1 -> a2 -> a3 -> a4 -> a5 <br/>
 	* head -> middle -> middle -> middle -> end <br/>
 	* swap(head,end), swap(head,middle), swap(middle,middle), swap(middle,head), swap(middle,end), swap(end,head), swap(end,middle)
 	*/
    @Test
    void testSwap() {
    	Voter v = Voter.createVoter(1);
    	MutableGraph<Alternative> toTestGraph = GraphBuilder.directed().allowsSelfLoops(true).build();
    	toTestGraph.putEdge(a1, a2);
    	toTestGraph.putEdge(a2, a3);
    	toTestGraph.putEdge(a3, a4);
    	toTestGraph.putEdge(a4, a5);
    	MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestGraph);
        
		MutableGraph<Alternative> graph1 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph1.putEdge(a5, a2);
		graph1.putEdge(a2, a3);
		graph1.putEdge(a3, a4);
		graph1.putEdge(a4, a1);		
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, graph1);
		toTestPref.swap(a1,a5);	//swap(head,end)
		assertEquals(pref1, toTestPref);
			
		MutableGraph<Alternative> graph2 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph2.putEdge(a3, a2);
		graph2.putEdge(a2, a5);
		graph2.putEdge(a5, a4);
		graph2.putEdge(a4, a1);		
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, graph2);
		toTestPref.swap(a5,a3); //swap(head,middle)
		assertEquals(pref2, toTestPref);
		
		MutableGraph<Alternative> graph3 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph3.putEdge(a3, a4);
		graph3.putEdge(a4, a5);
		graph3.putEdge(a5, a2);
		graph3.putEdge(a2, a1);		
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, graph3);			
		toTestPref.swap(a2,a4); //swap(middle,middle)
		assertEquals(pref3, toTestPref);
		
		MutableGraph<Alternative> graph4 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph4.putEdge(a5, a4);
		graph4.putEdge(a4, a3);
		graph4.putEdge(a3, a2);
		graph4.putEdge(a2, a1);		
		MutableLinearPreference pref4 = MutableLinearPreferenceImpl.given(v, graph4);
		toTestPref.swap(a5,a3); //swap(middle,head)
		assertEquals(pref4, toTestPref);
		
		MutableGraph<Alternative> graph5 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph5.putEdge(a5, a1);
		graph5.putEdge(a1, a3);
		graph5.putEdge(a3, a2);
		graph5.putEdge(a2, a4);		
		MutableLinearPreference pref5 = MutableLinearPreferenceImpl.given(v, graph5);
		toTestPref.swap(a4,a1); //swap(middle,end)
		assertEquals(pref5, toTestPref);
		
		MutableGraph<Alternative> graph6 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph6.putEdge(a4, a1);
		graph6.putEdge(a1, a3);
		graph6.putEdge(a3, a2);
		graph6.putEdge(a2, a5);		
		MutableLinearPreference pref6 = MutableLinearPreferenceImpl.given(v, graph6);
		toTestPref.swap(a4,a5); //swap(end,head)
		assertEquals(pref6, toTestPref);
		
		MutableGraph<Alternative> graph7 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph7.putEdge(a4, a1);
		graph7.putEdge(a1, a5);
		graph7.putEdge(a5, a2);
		graph7.putEdge(a2, a3);		
		MutableLinearPreference pref7 = MutableLinearPreferenceImpl.given(v, graph7);
		toTestPref.swap(a5,a3); //swap(end,middle)
		assertEquals(pref7, toTestPref);
    }
}
