package io.github.oliviercailloux.j_voting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;

public class CompletePreferenceImplTest {

    public static CompletePreferenceImpl createPreferenceToTest() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set1.add(a2);
        set2.add(a3);
        set3.add(a4);
        set3.add(a5);
        List<Set<Alternative>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);
        return CompletePreferenceImpl.createCompletePreferenceImpl(list);
    }

    @Test
    public void testGetPreferencesNonStrict() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set1.add(a2);
        set2.add(a3);
        set3.add(a4);
        set3.add(a5);
        List<Set<Alternative>> pref = createPreferenceToTest()
                        .getPreferencesNonStrict();
        assertEquals(pref.get(0), set1);
        assertEquals(pref.get(1), set2);
        assertEquals(pref.get(2), set3);
    }

    @Test
    public void testToString() {
        String s1 = "{1,2},{3},{4,5}";
        String s2 = "{2,1},{3},{4,5}";
        String s3 = "{1,2},{3},{5,4}";
        String s4 = "{2,1},{3},{5,4}";
        String pref = createPreferenceToTest().toString();
        assertTrue(pref.equals(s1) || pref.equals(s2) || pref.equals(s3)
                        || pref.equals(s4));
    }

    @Test
    public void testSize() {
        assertEquals(createPreferenceToTest().size(), 5);
    }

    @Test
    public void testEqualsPreference() {
        CompletePreferenceImpl p1 = createPreferenceToTest();
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set1.add(a2);
        set2.add(a3);
        set3.add(a4);
        set3.add(a5);
        List<Set<Alternative>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);
        CompletePreferenceImpl p2 = CompletePreferenceImpl.createCompletePreferenceImpl(list);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testContains() {
        Alternative a = Alternative.withId(4);
        assertTrue(createPreferenceToTest().contains(a));
    }

    @Test
    public void testHasSameAlternatives() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set2.add(a2);
        set2.add(a3);
        set2.add(a4);
        set3.add(a5);
        List<Set<Alternative>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);
        CompletePreferenceImpl pref = CompletePreferenceImpl.createCompletePreferenceImpl(list);
        assertTrue(pref.hasSameAlternatives(createPreferenceToTest()));
    }

    @Test
    public void testIsIncludedIn() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set1.add(a2);
        set2.add(a3);
        set3.add(a4);
        List<Set<Alternative>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);
        CompletePreferenceImpl p = CompletePreferenceImpl.createCompletePreferenceImpl(list);
        assertTrue(p.isIncludedIn(createPreferenceToTest()));
    }

    @Test
    public void testToAlternativeSet() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a3);
        set.add(a4);
        set.add(a5);
        assertEquals(CompletePreferenceImpl.toAlternativeSet(
                        createPreferenceToTest().getPreferencesNonStrict()),
                        set);
    }

    @Test
    public void testAlternativeSetEqual() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a3);
        set.add(a4);
        set.add(a5);
        Set<Alternative> set2 = new HashSet<>();
        set2.add(a3);
        set2.add(a4);
        set2.add(a1);
        set2.add(a2);
        set2.add(a5);
        assertEquals(set, set2);
    }

    @Test
    public void testAlternativeSetContains() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Alternative a4 = Alternative.withId(4);
        Alternative a5 = Alternative.withId(5);
        Set<Alternative> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a3);
        set.add(a4);
        set.add(a5);
        assertTrue(set.contains(a3));
    }

    @Test
    public void testSizeListSetAlternative() {
        assertEquals(CompletePreferenceImpl.size(
                        createPreferenceToTest().getPreferencesNonStrict()), 5);
    }

    @Test
    public void testIsStrictFalse() {
        assertTrue(!createPreferenceToTest().isStrict());
    }

    @Test
    public void testIsStrictTrue() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        Set<Alternative> s1 = new HashSet<>();
        Set<Alternative> s2 = new HashSet<>();
        Set<Alternative> s3 = new HashSet<>();
        s1.add(a1);
        s2.add(a2);
        s3.add(a3);
        List<Set<Alternative>> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        CompletePreferenceImpl p = CompletePreferenceImpl.createCompletePreferenceImpl(list);
        assertTrue(p.isStrict());
    }

    @Test
    public void testGetAlternativeRank() {
        assertEquals(createPreferenceToTest()
                        .getAlternativeRank(Alternative.withId(4)), 3);
    }
}
