package io.github.oliviercailloux.j_voting.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v4;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a21list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a321list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a231list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1234list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3214list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2314list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v123set;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v1a123list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v2a321list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v3a231list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v4a321list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v1a1234list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v2a3214list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v3a2314list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v1a12list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v2a21list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.v3a21list;


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

		MutableLinearPreference mlp1 = MutableLinearPreferenceImpl.given(v1, a123list);
		MutableLinearPreference mlp2 = MutableLinearPreferenceImpl.given(v2, a321list);
		MutableLinearPreference mlp3 = MutableLinearPreferenceImpl.given(v3, a231list);

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
		
		profile.put(v1, v1a123list);
		profile.put(v2, v2a321list);
		profile.put(v3, v3a231list);
		profile.put(v4, v4a321list);

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

		profile.put(v1, v1a123list);
		profile.put(v2, v2a321list);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		
		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.removeVoter(v3);
		assertEquals(msp1, toTest);
	}

	@Test
	public void renameVoterTest() {
		
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		profile.put(v1, v1a123list);
		profile.put(v2, v2a321list);
		profile.put(v3, v3a231list);

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

		profile.put(v1, v1a1234list);
		profile.put(v2, v2a3214list);
		profile.put(v3, v3a2314list);

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
		toTest.addAlternativeProfile(a4);
		assertEquals(msp1, toTest);
	}
	
	@Test
	public void addAlternativePreferenceTest() {
		
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		profile.put(v1, v1a1234list);
		profile.put(v2, v2a3214list);
		profile.put(v3, v3a2314list);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");
		alternativeNames.put(a3, "Fondue Savoyarde");
		alternativeNames.put(a4, "Alternative 4");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");
		
		MutableStrictProfile mspExpected = MutableStrictProfile.given(profile, alternativeNames, voterNames);

		MutableStrictProfile toTest = createMSPToTest();
		toTest.getPreference(v1).addAlternative(a4);
		assertEquals(mspExpected, toTest);
	}

	@Test
	public void removeAlternativeTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		profile.put(v1, v1a12list);
		profile.put(v2, v2a21list);
		profile.put(v3, v3a21list);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		MutableStrictProfile msp1 = MutableStrictProfile.given(profile, alternativeNames, voterNames);
		MutableStrictProfile toTest = createMSPToTest();
		toTest.removeAlternativeProfile(a3);
		assertEquals(msp1, toTest);
	}
	
	@Test
	public void removeAlternativePreferenceTest() {
		
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		profile.put(v1, v1a12list);
		profile.put(v2, v2a21list);
		profile.put(v3, v3a21list);

		BiMap<Alternative, String> alternativeNames = HashBiMap.create();
		alternativeNames.put(a1, "Tartiflette");
		alternativeNames.put(a2, "Pâte au saumon");

		BiMap<Voter, String> voterNames = HashBiMap.create();
		voterNames.put(v1, "Pierre");
		voterNames.put(v2, "Thomas");
		voterNames.put(v3, "Jade");

		MutableStrictProfile mspExpected = MutableStrictProfile.given(profile, alternativeNames, voterNames);

		MutableStrictProfile toTest = createMSPToTest();
		toTest.getPreference(v1).removeAlternative(a3);
		assertEquals(mspExpected, toTest);
	}

	@Test
	public void renameAlternativeTest() {
		Map<Voter, MutableLinearPreference> profile = new HashMap<>();

		profile.put(v1, v1a123list);
		profile.put(v2, v2a321list);
		profile.put(v3, v3a231list);

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
		
		MutableStrictProfile toTest = createMSPToTest();
		assertEquals(v123set, toTest.getVoters());
	}

	@Test
	public void getAlternativesTest() {
		
		MutableStrictProfile toTest = createMSPToTest();
		assertEquals(a123, toTest.getAlternatives());
		
		MutableStrictProfile toTest1 = MutableStrictProfile.empty();
		Set<Alternative> emptySet = new HashSet<>();
		assertEquals(emptySet, toTest1.getAlternatives());
	}
}
