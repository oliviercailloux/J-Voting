package io.github.oliviercailloux.j_voting.profiles.management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.StrictCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfileI;
import io.github.oliviercailloux.j_voting.profiles.management.StrictProfileBuilder;

public class StrictProfileBuilderTest {

    @Test
    public void testCreateOneAlternativeProfile() {
        Alternative a1 = new Alternative(1);
        Alternative a2 = new Alternative(2);
        Alternative a3 = new Alternative(3);
        Alternative a4 = new Alternative(4);
        List<Alternative> list1 = new ArrayList<>();
        List<Alternative> list2 = new ArrayList<>();
        List<Alternative> list3 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        list2.add(a2);
        list2.add(a1);
        list2.add(a3);
        list2.add(a4);
        list3.add(a3);
        list3.add(a2);
        list3.add(a4);
        StrictCompletePreferenceImpl p1 = new StrictCompletePreferenceImpl(list1);
        StrictCompletePreferenceImpl p2 = new StrictCompletePreferenceImpl(list2);
        StrictCompletePreferenceImpl p3 = new StrictCompletePreferenceImpl(list3);
        Voter v1 = new Voter(1);
        Voter v2 = new Voter(2);
        Voter v3 = new Voter(3);
        Voter v4 = new Voter(4);
        Voter v5 = new Voter(5);
        Voter v6 = new Voter(6);
        StrictProfileBuilder profBuild = new StrictProfileBuilder();
        profBuild.addVote(v1, p1);
        profBuild.addVote(v2, p1);
        profBuild.addVote(v3, p1);
        profBuild.addVote(v4, p2);
        profBuild.addVote(v5, p2);
        profBuild.addVote(v6, p3);
        ImmutableStrictProfileI resultProf = profBuild
                        .createOneAlternativeProfile();
        Map<Voter, StrictCompletePreferenceImpl> map = new HashMap<>();
        List<Alternative> l1 = new ArrayList<>();
        List<Alternative> l2 = new ArrayList<>();
        List<Alternative> l3 = new ArrayList<>();
        l1.add(a1);
        l2.add(a2);
        l3.add(a3);
        StrictCompletePreferenceImpl pref1 = new StrictCompletePreferenceImpl(l1);
        StrictCompletePreferenceImpl pref2 = new StrictCompletePreferenceImpl(l2);
        StrictCompletePreferenceImpl pref3 = new StrictCompletePreferenceImpl(l3);
        map.put(v1, pref1);
        map.put(v2, pref1);
        map.put(v3, pref1);
        map.put(v4, pref2);
        map.put(v5, pref2);
        map.put(v6, pref3);
        ImmutableStrictProfileI profile = new ImmutableStrictProfileI(map);
        assertEquals(resultProf, profile);
    }
}
