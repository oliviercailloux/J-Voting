package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12345;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a6;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableLinearPreferenceImplTest {
	
	@Test
	void testMutableLinearPreference() {
		Voter v = Voter.createVoter(1);
        MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, graph);
        
        LinkedList<Alternative> list = new LinkedList<Alternative>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, list);
        
        assertEquals(pref1, pref2);
    }
	
	/**
     * Tests whether the preference is correctly expressed as a graph
     */
	@Test
    void testAsGraph() {
        MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), graph);
        
        graph.putEdge(a1, a3);
        graph.putEdge(a1, a1);
        graph.putEdge(a2, a2);
        graph.putEdge(a3, a3);
        
        assertEquals(graph, pref.asGraph());
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
	
	 /**
     * Tests whether method getAlternatives returns a set with all the
     * alternatives of the preference
     */
    @Test
    void testGetAlternatives() {
        MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a3);
        graph.putEdge(a3, a4);
        graph.putEdge(a4, a5);
        MutableLinearPreference pref = MutableLinearPreferenceImpl.given(Voter.createVoter(1), graph);
        Set<Alternative> expected = a12345;
        assertEquals(expected, pref.getAlternatives());
        assertFalse(pref.getAlternatives().add(a6));
        
        
    }
    
    @Test
    void testChangeOrder(){
    	Voter v = Voter.createVoter(1);
    	MutableGraph<Alternative> toTestGraph = GraphBuilder.directed().allowsSelfLoops(true).build();
    	toTestGraph.putEdge(a1, a2);
    	toTestGraph.putEdge(a2, a3);
    	toTestGraph.putEdge(a3, a4);
    	toTestGraph.putEdge(a4, a5);
    	MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestGraph);
    	
    	MutableGraph<Alternative> graph1 = GraphBuilder.directed().allowsSelfLoops(true).build();
    	graph1.putEdge(a1, a4);
    	graph1.putEdge(a4, a2);
    	graph1.putEdge(a2, a3);
    	graph1.putEdge(a3, a5);		
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, graph1);	
		toTestPref.changeOrder(a4,1);
		assertEquals(pref1, toTestPref);
		
    	MutableGraph<Alternative> graph2 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph2.putEdge(a5, a1);
		graph2.putEdge(a1, a4);
		graph2.putEdge(a4, a2);
		graph2.putEdge(a2, a3);		
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, graph2);
		toTestPref.changeOrder(a5,0);
		assertEquals(pref2, toTestPref);
		
    	MutableGraph<Alternative> graph3 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph3.putEdge(a5, a4);
		graph3.putEdge(a4, a2);
		graph3.putEdge(a2, a1);
		graph3.putEdge(a1, a3);		
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, graph3);
		toTestPref.changeOrder(a1,3);
		assertEquals(pref3, toTestPref);
		
    	MutableGraph<Alternative> graph4 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph4.putEdge(a4, a2);
		graph4.putEdge(a2, a1);
		graph4.putEdge(a1, a3);
		graph4.putEdge(a3, a5);		
		MutableLinearPreference pref4 = MutableLinearPreferenceImpl.given(v, graph4);
		toTestPref.changeOrder(a5,4);
		assertEquals(pref4, toTestPref);
    }
    
    /**
 	* Test the 7 cases of the swap method <br/>
 	* a1 -> a2 -> a3 -> a4 -> a5 <br/>
 	* head -> middle -> middle -> middle -> end <br/>
 	* swap(head,end), swap(head,middle), swap(middle,middle), swap(middle,head), swap(middle,end), swap(end,head), swap(end,middle) <br/>
 	* We have to test 3 additional cases about the neighbours alternatives
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
		
		MutableGraph<Alternative> graph8 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph8.putEdge(a4, a1);
		graph8.putEdge(a1, a2);
		graph8.putEdge(a2, a5);
		graph8.putEdge(a5, a3);		
		MutableLinearPreference pref8 = MutableLinearPreferenceImpl.given(v, graph8);
		toTestPref.swap(a5,a2); //swap(middle,middle) neighbour 
		assertEquals(pref8, toTestPref);
		
		MutableGraph<Alternative> graph9 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph9.putEdge(a1, a4);
		graph9.putEdge(a4, a2);
		graph9.putEdge(a2, a5);
		graph9.putEdge(a5, a3);		
		MutableLinearPreference pref9 = MutableLinearPreferenceImpl.given(v, graph9);
		toTestPref.swap(a4,a1); //swap(head,middle) neighbour 
		assertEquals(pref9, toTestPref);
		
		MutableGraph<Alternative> graph10 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph10.putEdge(a1, a4);
		graph10.putEdge(a4, a2);
		graph10.putEdge(a2, a3);
		graph10.putEdge(a3, a5);		
		MutableLinearPreference pref10 = MutableLinearPreferenceImpl.given(v, graph10);
		toTestPref.swap(a5,a3); //swap(middle,end) neighbour 
		assertEquals(pref10, toTestPref);
		
		MutableGraph<Alternative> toTestGraph1 = GraphBuilder.directed().allowsSelfLoops(true).build();
    	toTestGraph1.putEdge(a1, a2);
    	MutableLinearPreference toTestPref1 = MutableLinearPreferenceImpl.given(v, toTestGraph1);
    	
		MutableGraph<Alternative> graph11 = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph11.putEdge(a2, a1);		
		MutableLinearPreference pref11 = MutableLinearPreferenceImpl.given(v, graph11);
		toTestPref1.swap(a1,a2); //swap(head,end) neighbour 
		assertEquals(pref11, toTestPref1);
    }
}
