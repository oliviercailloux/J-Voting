package io.github.oliviercailloux.j_voting.profiles;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a123list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a231list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a321list;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa1234v123;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa123v12;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa123v123;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa123v1234;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa123v123renameA;
import static io.github.oliviercailloux.j_voting.ProfileHelper.mspa12v12;
import static io.github.oliviercailloux.j_voting.Voter.v1;
import static io.github.oliviercailloux.j_voting.Voter.v2;
import static io.github.oliviercailloux.j_voting.Voter.v3;
import static io.github.oliviercailloux.j_voting.Voter.v4;
import static io.github.oliviercailloux.j_voting.VoterHelper.v123set;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.MutableLinearPreference;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;

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
		MutableStrictProfile toTest = createMSPToTest();
		toTest.addVoter(v4);
		assertEquals(mspa123v1234, toTest);
	}

	@Test
	public void removeVoterTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.removeVoter(v3);
		assertEquals(mspa12v12, toTest);
	}

	@Test
	public void renameVoterTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.renameVoter(v1, "Léo");
		assertEquals(mspa123v123, toTest);
	}

	@Test
	public void addAlternativeTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.addAlternativeProfile(a4);
		assertEquals(mspa1234v123, toTest);
	}

	@Test
	public void addAlternativePreferenceTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.getPreference(v1).addAlternative(a4);
		assertEquals(mspa1234v123, toTest);
	}

	@Test
	public void removeAlternativeTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.removeAlternativeProfile(a3);
		assertEquals(mspa123v12, toTest);
	}

	@Test
	public void removeAlternativePreferenceTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.getPreference(v1).removeAlternative(a3);
		assertEquals(mspa123v12, toTest);
	}

	@Test
	public void renameAlternativeTest() {
		MutableStrictProfile toTest = createMSPToTest();
		toTest.renameAlternative(a2, "Couscous");
		assertEquals(mspa123v123renameA, toTest);
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
