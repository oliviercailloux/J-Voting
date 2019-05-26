package io.github.oliviercailloux.j_voting.profiles.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;
import io.github.oliviercailloux.j_voting.profiles.ProfileI;
import io.github.oliviercailloux.j_voting.profiles.analysis.Borda;

public class BordaTest {

    public static ImmutableProfileI createIPIToTest() {
        Map<Voter, CompletePreferenceImpl> profile = new HashMap<>();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        Voter v1 = Voter.createVoter(1);
        Voter v2 = Voter.createVoter(2);
        Voter v3 = Voter.createVoter(3);
        Voter v4 = Voter.createVoter(4);
        Voter v5 = Voter.createVoter(5);
        Voter v6 = Voter.createVoter(6);
        List<Set<Alternative>> list1 = new ArrayList<>();
        List<Set<Alternative>> list2 = new ArrayList<>();
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        Set<Alternative> s3 = new HashSet<>();
        Set<Alternative> s4 = new HashSet<>();
        s1.add(a1);
        s1.add(a2);
        s2.add(a3);
        s3.add(a2);
        s4.add(a1);
        s4.add(a3);
        list1.add(s1);
        list1.add(s2);
        list2.add(s3);
        list2.add(s4);
        CompletePreferenceImpl pref1 = CompletePreferenceImpl.createCompletePreferenceImpl(list1);
        CompletePreferenceImpl pref2 = CompletePreferenceImpl.createCompletePreferenceImpl(list2);
        profile.put(v1, pref1);
        profile.put(v2, pref1);
        profile.put(v3, pref1);
        profile.put(v4, pref1);
        profile.put(v5, pref2);
        profile.put(v6, pref2);
        return ImmutableProfileI.createImmutableProfileI(profile);
    }

    @Test
    public void testgetSocietyPreference() {
        ImmutableProfileI prof = createIPIToTest();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        List<Set<Alternative>> list1 = new ArrayList<>();
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        Set<Alternative> s3 = new HashSet<>();
        s1.add(a1);
        s2.add(a2);
        s3.add(a3);
        list1.add(s2);
        list1.add(s1);
        list1.add(s3);
        CompletePreferenceImpl pref1 = CompletePreferenceImpl.createCompletePreferenceImpl(list1);
        assertEquals(Borda.createBorda().getSocietyPreference(prof), pref1);
    }

    @Test
    public void testSetScoresPref() {
        Borda b = Borda.createBorda();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        List<Set<Alternative>> list1 = new ArrayList<>();
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        s1.add(a1);
        s1.add(a2);
        s2.add(a3);
        list1.add(s1);
        list1.add(s2);
        CompletePreferenceImpl pref1 = CompletePreferenceImpl.createCompletePreferenceImpl(list1);
        b.setScores(pref1);
        Multiset<Alternative> m = b.getMultiSet();
        assertEquals(m.count(a1), 2);
        assertEquals(m.count(a2), 2);
        assertEquals(m.count(a3), 1);
    }

    @Test
    public void testSetScoresProfile() {
        Borda b = Borda.createBorda();
        ProfileI p = createIPIToTest();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        b.setScores(p);
        Multiset<Alternative> m = b.getMultiSet();
        assertEquals(m.count(a1), 10);
        assertEquals(m.count(a2), 12);
        assertEquals(m.count(a3), 6);
    }

    @Test
    public void testgetMax() {
        HashMultiset<Alternative> listScores = HashMultiset.create();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        listScores.add(a1, 7);
        listScores.add(a2, 5);
        listScores.add(a3, 7);
        Set<Alternative> set = new HashSet<>();
        set.add(a1);
        set.add(a3);
        assertEquals(Borda.createBorda().getMax(listScores), set);
    }

    @Test
    public void testEquals() {
        HashMultiset<Alternative> mset1 = HashMultiset.create();
        HashMultiset<Alternative> mset2 = HashMultiset.create();
        HashMultiset<Alternative> mset3 = HashMultiset.create();
        Alternative a1 = Alternative.createAlternative(1);
        Alternative a2 = Alternative.createAlternative(2);
        Alternative a3 = Alternative.createAlternative(3);
        mset1.add(a1, 2);
        mset1.add(a2, 1);
        mset1.add(a3, 5);
        mset2.add(a1, 2);
        mset2.add(a2, 1);
        mset2.add(a3, 5);
        mset3.add(a1, 1);
        mset3.add(a2, 2);
        mset3.add(a3, 3);
        Borda b1 = Borda.createBorda(mset1);
        Borda b2 = Borda.createBorda(mset2);
        Borda b3 = Borda.createBorda(mset3);
        assertEquals(b1, b2);
        assertTrue(!(b1.equals(b3)));
    }
}
