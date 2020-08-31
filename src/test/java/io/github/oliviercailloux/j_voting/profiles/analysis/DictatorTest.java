package io.github.oliviercailloux.j_voting.profiles.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;
import io.github.oliviercailloux.j_voting.profiles.ProfileI;
import io.github.oliviercailloux.j_voting.profiles.analysis.Dictator;
import io.github.oliviercailloux.j_voting.profiles.management.ProfileBuilder;

public class DictatorTest {

    @Test
    public void getSocietyPreferenceTest() {
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Voter v3 = Voter.withId(3);
        Dictator d1 = Dictator.about(v1);
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        List<Set<Alternative>> list1 = new ArrayList<>();
        List<Set<Alternative>> list2 = new ArrayList<>();
        List<Set<Alternative>> list3 = new ArrayList<>();
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        Set<Alternative> s3 = new HashSet<>();
        s1.add(a1);
        s1.add(a2);
        s2.add(a3);
        s3.add(a1);
        s3.add(a3);
        list1.add(s1);
        list1.add(s2);
        list2.add(s2);
        list3.add(s3);
        OldCompletePreferenceImpl pref1 = OldCompletePreferenceImpl.createCompletePreferenceImpl(list1);
        OldCompletePreferenceImpl pref2 = OldCompletePreferenceImpl.createCompletePreferenceImpl(list2);
        OldCompletePreferenceImpl pref3 = OldCompletePreferenceImpl.createCompletePreferenceImpl(list3);
        ProfileBuilder prof = ProfileBuilder.createProfileBuilder();
        prof.addVote(v1, pref1);
        prof.addVote(v2, pref2);
        prof.addVote(v3, pref3);
        ProfileI profile = prof.createProfileI();
        ImmutableProfileI iprof = (ImmutableProfileI) profile;
        assertEquals(d1.getSocietyPreference(iprof), pref1);
    }

    @Test
    public void equalsTest() {
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Dictator d1 = Dictator.about(v1);
        Dictator d2 = Dictator.about(v2);
        Dictator d3 = Dictator.about(v1);
        assertEquals(d1, d3);
        assertTrue(!d1.equals(d2));
    }

    @Test
    public void equalsVoterTest() {
        Voter v1 = Voter.withId(1);
        Voter v2 = Voter.withId(2);
        Dictator d1 = Dictator.about(v1);
        assertEquals(d1.getDictator(), v1);
        assertTrue(!d1.getDictator().equals(v2));
    }
}
