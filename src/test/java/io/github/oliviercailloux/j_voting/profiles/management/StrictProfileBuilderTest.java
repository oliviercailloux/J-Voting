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
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
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
        StrictCompletePreferenceImpl p1 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(list1);
        StrictCompletePreferenceImpl p2 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(list2);
        StrictCompletePreferenceImpl p3 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(list3);
        Voter v1 = Voter.createVoter(1);
        Voter v2 = Voter.createVoter(2);
        Voter v3 = Voter.createVoter(3);
        Voter v4 = Voter.createVoter(4);
        Voter v5 = Voter.createVoter(5);
        Voter v6 = Voter.createVoter(6);
        StrictProfileBuilder profBuild = StrictProfileBuilder.createStrictProfileBuilder();
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
        StrictCompletePreferenceImpl pref1 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(l1);
        StrictCompletePreferenceImpl pref2 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(l2);
        StrictCompletePreferenceImpl pref3 = StrictCompletePreferenceImpl.createStrictCompletePreferenceImpl(l3);
        map.put(v1, pref1);
        map.put(v2, pref1);
        map.put(v3, pref1);
        map.put(v4, pref2);
        map.put(v5, pref2);
        map.put(v6, pref3);
        ImmutableStrictProfileI profile = ImmutableStrictProfileI.createImmutableStrictProfileI(map);
        assertEquals(resultProf, profile);
    }
}
