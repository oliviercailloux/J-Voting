package io.github.oliviercailloux.j_voting.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfileI;

public class ImmutableStrictProfileITest {

    /**
     * 
     * @return an ImmutableStrictProfileI to test
     */
    public static ImmutableStrictProfileI createISPIToTest() {
        Map<Voter, OldLinearPreferenceImpl> profile = new HashMap<>();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Voter v3 = Voter.withId(3);
        Voter v4 = Voter.withId(4);
        Voter v5 = Voter.withId(5);
        Voter v6 = Voter.withId(6);
        ArrayList<Alternative> list1 = new ArrayList<>();
        ArrayList<Alternative> list2 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        list2.add(a3);
        list2.add(a2);
        OldLinearPreferenceImpl pref1 = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list1);
        OldLinearPreferenceImpl pref2 = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list2);
        profile.put(v1, pref1);
        profile.put(v2, pref1);
        profile.put(v3, pref1);
        profile.put(v4, pref1);
        profile.put(v5, pref2);
        profile.put(v6, pref2);
        return ImmutableStrictProfileI.createImmutableStrictProfileI(profile);
    }

    /**
     * 
     * @return a map of Voter and Preference to test
     */
    public static Map<Voter, OldCompletePreferenceImpl> createNonStrictMap() {
        Map<Voter, OldCompletePreferenceImpl> map = new HashMap<>();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Voter v3 = Voter.withId(3);
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        Set<Alternative> s3 = new HashSet<>();
        s1.add(a1);
        s2.add(a2);
        s3.add(a3);
        List<Set<Alternative>> list1 = new ArrayList<>();
        List<Set<Alternative>> list2 = new ArrayList<>();
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);
        list2.add(s3);
        list2.add(s2);
        list2.add(s1);
        OldCompletePreferenceImpl p1 = OldCompletePreferenceImpl.createCompletePreferenceImpl(list1);
        OldCompletePreferenceImpl p2 = OldCompletePreferenceImpl.createCompletePreferenceImpl(list2);
        map.put(v1, p1);
        map.put(v2, p1);
        map.put(v3, p2);
        return map;
    }

    /**
     * 
     * @return a map of Voter and StrictPreference to test
     */
    public static Map<Voter, OldLinearPreferenceImpl> createStrictMap() {
        Map<Voter, OldLinearPreferenceImpl> map = new HashMap<>();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Voter v3 = Voter.withId(3);
        List<Alternative> list1 = new ArrayList<>();
        List<Alternative> list2 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        list2.add(a3);
        list2.add(a2);
        list2.add(a1);
        OldLinearPreferenceImpl p1 = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list1);
        OldLinearPreferenceImpl p2 = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list2);
        map.put(v1, p1);
        map.put(v2, p1);
        map.put(v3, p2);
        return map;
    }

    @Test
    public void testGetPreferenceVoter() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Voter v1 = Voter.withId(1);
        ArrayList<Alternative> list1 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        OldLinearPreferenceImpl pref1 = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list1);
        assertEquals(pref1, createISPIToTest().getPreference(v1));
    }
}
