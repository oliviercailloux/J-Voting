package io.github.oliviercailloux.j_voting.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableStrictProfileTest {

	/**
	 * 
	 * @return an MutableStrictProfile to test
	 */
	public static MutableStrictProfile createMSPToTest() {

		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		return MutableStrictProfile.given(profile, alternativeNames, voterNames);
	}

	@Test
	public void addVoterTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);
		Voter v4 = Voter.createVoter(4);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);
		MutableLinearPreference mlp4 = MutableLinearPreferenceImpl.given(v4, list1);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);
		profile.put(v4, mlp4);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");
		voterNames.put(v4, "Voter 4");
		
		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.addVoter(v4);
		assertEquals(msp1, toTest);
	}

	@Test
	public void removeVoterTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		
		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		Voter v3 = Voter.createVoter(3);
		toTest.removeVoter(v3);
		assertEquals(msp1, toTest);
	}

	@Test
	public void renameVoterTest() {
		
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Léo");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");
		
		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.renameVoter(v1, "Léo");
		assertEquals(msp1, toTest);
	}

	@Test
	public void addAlternativeTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);
		Alternative a4 = Alternative.withId(4);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);
		list1.add(a4);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);
		list2.add(a4);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);
		list3.add(a4);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");
		alternativeNames.put(a4, "Alternative 4");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.addAlternative(a4);
		assertEquals(msp1, toTest);
	}

	@Test
	public void removeAlternativeTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		Alternative a3 = Alternative.withId(3);
		toTest.removeAlternative(a3);
		assertEquals(msp1, toTest);
	}

	@Test
	public void renameAlternativeTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);

		profile.put(v1, mlp1);
		profile.put(v2, mlp2);
		profile.put(v3, mlp3);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Couscous");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.renameAlternative(a2, "Couscous");
		assertEquals(msp1, toTest);
	}

	@Test
	public void getVotersTest() {
		
		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);
		
		Set<Voter> set = new HashSet<>();
		set.add(v1);
		set.add(v2);
		set.add(v3);
		
		MutableStrictProfile toTest = createMSPToTest();
		assertEquals(set, toTest.getVoters());
	}

	@Test
	public void getAlternativesTest() {

		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);
		
		Set<Alternative> set = new HashSet<>();
		set.add(a1);
		set.add(a2);
		set.add(a3);
		
		//MutableStrictProfile toTest = createMSPToTest();
		//assertEquals(set, toTest.getAlternatives());
	}

	@Test
	public void getPreferenceTest() {
		
		Alternative a1 = Alternative.withId(1);
		Alternative a2 = Alternative.withId(2);
		Alternative a3 = Alternative.withId(3);

		Voter v1 = Voter.createVoter(1);
		Voter v2 = Voter.createVoter(2);
		Voter v3 = Voter.createVoter(3);

		ArrayList<Alternative> list1 = new ArrayList<>();
		list1.add(a1);
		list1.add(a2);
		list1.add(a3);

		ArrayList<Alternative> list2 = new ArrayList<>();
		list2.add(a3);
		list2.add(a2);
		list2.add(a1);

		ArrayList<Alternative> list3 = new ArrayList<>();
		list3.add(a2);
		list3.add(a3);
		list3.add(a1);

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, list1);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, list2);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, list3);
		
		MutableStrictProfile toTest = createMSPToTest();
		assertEquals(mlp1, toTest.getPreference(v1));
		assertEquals(mlp2, toTest.getPreference(v2));
		assertEquals(mlp3, toTest.getPreference(v3));
	}
}
