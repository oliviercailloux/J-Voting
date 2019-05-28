package io.github.oliviercailloux.j_voting.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.StrictCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfile;

public class ImmutableStrictProfileTest {

    /**
     * 
     * @return an ImmutableStrictProfileI to test
     */
    public static ImmutableStrictProfile createISPToTest() {
        Map<Voter, StrictCompletePreferenceImpl> profile = new HashMap<>();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Voter v1 = Voter.createVoter(1);
        Voter v2 = Voter.createVoter(2);
        Voter v3 = Voter.createVoter(3);
        Voter v4 = Voter.createVoter(4);
        Voter v5 = Voter.createVoter(5);
        Voter v6 = Voter.createVoter(6);
        ArrayList<Alternative> list1 = new ArrayList<>();
        ArrayList<Alternative> list2 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        list2.add(a3);
        list2.add(a2);
        list2.add(a1);
        StrictCompletePreferenceImpl pref1 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(list1);
        StrictCompletePreferenceImpl pref2 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(list2);
        profile.put(v1, pref1);
        profile.put(v2, pref1);
        profile.put(v3, pref1);
        profile.put(v4, pref1);
        profile.put(v5, pref2);
        profile.put(v6, pref2);
        return ImmutableStrictProfile.createImmutableStrictProfile(profile);
    }

    @Test
    public void testGetNbAlternatives() {
        assertEquals(createISPToTest().getNbAlternatives(), 3);
    }

    @Test
    public void testGetAlternatives() {
        Set<Alternative> alters = new HashSet<>();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        alters.add(a1);
        alters.add(a2);
        alters.add(a3);
        assertEquals(alters, createISPToTest().getAlternatives());
    }
}
