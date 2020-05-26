package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12345;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1234;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a5;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a6;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableLinearPreferenceImplTest {
	
	/**
	 * Tests if the graph decorator is correctly working. In other words, if the cast to MutableGraph isn't possible.
	 * Take off comments to see a ClassCastException thrown.
	 */
	@Test
	void testGraphDecorator() {
//		Voter v = Voter.createVoter(1);
//		List<Alternative> toTestList = new ArrayList<>();		
//		toTestList.add(a1);
//		toTestList.add(a2);
//		toTestList.add(a3);
//		toTestList.add(a4);
//		toTestList.add(a5);
//		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
//		MutableGraph<Alternative> graph1 = GraphBuilder.directed().allowsSelfLoops(true).build();
//		graph1 = (MutableGraph<Alternative>)toTestPref.asGraph();
		
//		The cast can't be done thanks to the graph decorator. It throws a ClassCastException.
	}

	/**
	 * Tests whether the preference is correctly expressed as a graph
	 */
	@Test
	void testAsGraph() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		toTestList.add(a5);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
		MutableGraph<Alternative> graph = GraphBuilder.directed().allowsSelfLoops(true).build();
		graph.putEdge(a1, a1);
		graph.putEdge(a1, a2);
		graph.putEdge(a1, a3);
		graph.putEdge(a1, a4);
		graph.putEdge(a1, a5);
		graph.putEdge(a2, a2);
		graph.putEdge(a2, a3);
		graph.putEdge(a2, a4);
		graph.putEdge(a2, a5);
		graph.putEdge(a3, a3);
		graph.putEdge(a3, a4);
		graph.putEdge(a3, a5);
		graph.putEdge(a4, a4);
		graph.putEdge(a4, a5);
		graph.putEdge(a5, a5);
		assertEquals(graph, toTestPref.asGraph());
	}

	/**
	 * Tests whether the single alternative is added to the preferences
	 */
	@Test
	void testAddAlternative() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
		List<Alternative> list1 = new ArrayList<>();	
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);
		list1.add(a4);
		list1.add(a5);
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, list1);
		toTestPref.addAlternative(a5);
		assertEquals(toTestPref, pref1);
	}

	/**
	 * Tests whether the single alternative is removed to the preferences
	 */
	@Test
	void testRemoveAlternative() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		toTestList.add(a5);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
		List<Alternative> list1 = new ArrayList<>();		
		list1.add(a1);
		list1.add(a3);
		list1.add(a4);
		list1.add(a5);
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, list1);
		toTestPref.removeAlternative(a2);
		assertEquals(toTestPref, pref1);
		
		List<Alternative> list2 = new ArrayList<>();		
		list2.add(a3);
		list2.add(a4);
		list2.add(a5);
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, list2);
		toTestPref.removeAlternative(a1);
		assertEquals(toTestPref, pref2);
		
		List<Alternative> list3 = new ArrayList<>();		
		list3.add(a3);
		list3.add(a4);
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, list3);
		toTestPref.removeAlternative(a5);
		assertEquals(toTestPref, pref3);
	}

	/**
	 * Tests whether method getAlternatives returns a set with all the alternatives
	 * of the preference. Here we are going to test the decorator.
	 */
	@Test
	void testGetAlternatives() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		Set<Alternative> expected = a1234;
		assertEquals(expected, toTestPref.getAlternatives());
		
		List<Alternative> list1 = new ArrayList<>();		
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);
		list1.add(a4);
		list1.add(a5);
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, list1);		
		toTestPref.getAlternatives().add(a5);
		assertEquals(toTestPref, pref1);
		
		List<Alternative> list2 = new ArrayList<>();		
		list2.add(a1);
		list2.add(a2);
		list2.add(a3);
		list2.add(a5);
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, list2);		
		toTestPref.getAlternatives().remove(a4);
		assertEquals(toTestPref, pref2);
		
		List<Alternative> list3 = new ArrayList<>();		
		list3.add(a1);
		list3.add(a2);
		list3.add(a3);
		list3.add(a5);
		list3.add(a4);
		list3.add(a6);
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, list3);
		List<Alternative> addList = new ArrayList<>();
		addList.add(a4);
		addList.add(a6);
		toTestPref.getAlternatives().addAll(addList);
		assertEquals(pref3,toTestPref);
		
		List<Alternative> list4 = new ArrayList<>();		
		list4.add(a1);
		list4.add(a2);
		list4.add(a5);
		list4.add(a6);
		MutableLinearPreference pref4 = MutableLinearPreferenceImpl.given(v, list4);
		List<Alternative> removeList = new ArrayList<>();
		removeList.add(a3);
		removeList.add(a4);
		toTestPref.getAlternatives().removeAll(removeList);
		assertEquals(pref4,toTestPref);
		
		List<Alternative> list5 = new ArrayList<>();		
		MutableLinearPreference pref5 = MutableLinearPreferenceImpl.given(v, list5);
		toTestPref.getAlternatives().clear();
		assertEquals(pref5,toTestPref);
		
	}

	@Test
	void testChangeOrder() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		toTestList.add(a5);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
		List<Alternative> list1 = new ArrayList<>();		
		list1.add(a4);
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);
		list1.add(a5);
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, list1);
		toTestPref.changeOrder(a4, 1);
		assertEquals(pref1, toTestPref);
		
		List<Alternative> list2 = new ArrayList<>();		
		list2.add(a4);
		list2.add(a1);
		list2.add(a3);
		list2.add(a2);
		list2.add(a5);
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, list2);
		toTestPref.changeOrder(a2, 4);
		assertEquals(pref2, toTestPref);
		
		List<Alternative> list3 = new ArrayList<>();		
		list3.add(a4);
		list3.add(a3);
		list3.add(a2);
		list3.add(a5);
		list3.add(a1);
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, list3);
		toTestPref.changeOrder(a1, 5);
		assertEquals(pref3, toTestPref);
	}

	/**
	 * Test the 7 cases of the swap method <br/>
	 * a1 -> a2 -> a3 -> a4 -> a5 <br/>
	 * head -> middle -> middle -> middle -> end <br/>
	 * swap(head,end), swap(head,middle), swap(middle,middle), swap(middle,head),
	 * swap(middle,end), swap(end,head), swap(end,middle) <br/>
	 * We have to test 3 additional cases about the neighbours alternatives
	 */
	@Test
	void testSwap() {
		Voter v = Voter.createVoter(1);
		List<Alternative> toTestList = new ArrayList<>();		
		toTestList.add(a1);
		toTestList.add(a2);
		toTestList.add(a3);
		toTestList.add(a4);
		toTestList.add(a5);
		MutableLinearPreference toTestPref = MutableLinearPreferenceImpl.given(v, toTestList);
		
		List<Alternative> list1 = new ArrayList<>();		
		list1.add(a5);
		list1.add(a2);
		list1.add(a3);
		list1.add(a4);
		list1.add(a1);
		MutableLinearPreference pref1 = MutableLinearPreferenceImpl.given(v, list1);

		toTestPref.swap(a1, a5); // swap(head,end)	
		assertEquals(pref1, toTestPref);
		
		List<Alternative> list2 = new ArrayList<>();		
		list2.add(a3);
		list2.add(a2);
		list2.add(a5);
		list2.add(a4);
		list2.add(a1);
		MutableLinearPreference pref2 = MutableLinearPreferenceImpl.given(v, list2);
		toTestPref.swap(a5, a3); // swap(head,middle)	
		assertEquals(pref2, toTestPref);
		
		List<Alternative> list3 = new ArrayList<>();		
		list3.add(a3);
		list3.add(a4);
		list3.add(a5);
		list3.add(a2);
		list3.add(a1);
		MutableLinearPreference pref3 = MutableLinearPreferenceImpl.given(v, list3);
		toTestPref.swap(a2, a4); // swap(middle,middle)	
		assertEquals(pref3, toTestPref);
		
		List<Alternative> list4 = new ArrayList<>();		
		list4.add(a5);
		list4.add(a4);
		list4.add(a3);
		list4.add(a2);
		list4.add(a1);
		MutableLinearPreference pref4 = MutableLinearPreferenceImpl.given(v, list4);
		toTestPref.swap(a5, a3); // swap(middle,head)
		assertEquals(pref4, toTestPref);

		List<Alternative> list5 = new ArrayList<>();		
		list5.add(a5);
		list5.add(a1);
		list5.add(a3);
		list5.add(a2);
		list5.add(a4);
		MutableLinearPreference pref5 = MutableLinearPreferenceImpl.given(v, list5);
		toTestPref.swap(a4, a1); // swap(middle,end)
		assertEquals(pref5, toTestPref);
		
		List<Alternative> list6 = new ArrayList<>();		
		list6.add(a4);
		list6.add(a1);
		list6.add(a3);
		list6.add(a2);
		list6.add(a5);
		MutableLinearPreference pref6 = MutableLinearPreferenceImpl.given(v, list6);
		toTestPref.swap(a4, a5); // swap(end,head)
		assertEquals(pref6, toTestPref);

		List<Alternative> list7 = new ArrayList<>();		
		list7.add(a4);
		list7.add(a1);
		list7.add(a5);
		list7.add(a2);
		list7.add(a3);
		MutableLinearPreference pref7 = MutableLinearPreferenceImpl.given(v, list7);
		toTestPref.swap(a5, a3); // swap(end,middle)
		assertEquals(pref7, toTestPref);
		
		List<Alternative> list8 = new ArrayList<>();		
		list8.add(a4);
		list8.add(a1);
		list8.add(a2);
		list8.add(a5);
		list8.add(a3);
		MutableLinearPreference pref8 = MutableLinearPreferenceImpl.given(v, list8);
		toTestPref.swap(a5, a2); // swap(middle,middle) neighbour
		assertEquals(pref8, toTestPref);
		
		List<Alternative> list9 = new ArrayList<>();		
		list9.add(a1);
		list9.add(a4);
		list9.add(a2);
		list9.add(a5);
		list9.add(a3);
		MutableLinearPreference pref9 = MutableLinearPreferenceImpl.given(v, list9);
		toTestPref.swap(a4, a1); // swap(head,middle) neighbour
		assertEquals(pref9, toTestPref);

		List<Alternative> list10 = new ArrayList<>();		
		list10.add(a1);
		list10.add(a4);
		list10.add(a2);
		list10.add(a3);
		list10.add(a5);
		MutableLinearPreference pref10 = MutableLinearPreferenceImpl.given(v, list10);
		toTestPref.swap(a5, a3); // swap(middle,end) neighbour
		assertEquals(pref10, toTestPref);
		
		List<Alternative> toTestList1 = new ArrayList<>();		
		toTestList1.add(a1);
		toTestList1.add(a2);
		MutableLinearPreference toTestPref1 = MutableLinearPreferenceImpl.given(v, toTestList1);
		
		List<Alternative> list11 = new ArrayList<>();		
		list11.add(a2);
		list11.add(a1);
		MutableLinearPreference pref11 = MutableLinearPreferenceImpl.given(v, list11);
		toTestPref1.swap(a1, a2); // swap(head,end) neighbour
		assertEquals(pref11, toTestPref1);
	}
}

