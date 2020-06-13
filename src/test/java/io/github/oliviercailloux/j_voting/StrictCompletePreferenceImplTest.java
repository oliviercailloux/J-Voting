package io.github.oliviercailloux.j_voting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;

public class StrictCompletePreferenceImplTest {

    @Test
    public void testGetPreferences() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        List<Alternative> prefs = new ArrayList<>();
        prefs.add(a1);
        prefs.add(a2);
        prefs.add(a3);
        OldLinearPreferenceImpl p = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(prefs);
        assertEquals(p.getAlternatives(), prefs);
    }

    @Test
    public void testListAlternativeToListSetAlternative() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        List<Alternative> prefs = new ArrayList<>();
        prefs.add(a1);
        prefs.add(a2);
        prefs.add(a3);
        Set<Alternative> set1 = new HashSet<>();
        Set<Alternative> set2 = new HashSet<>();
        Set<Alternative> set3 = new HashSet<>();
        set1.add(a1);
        set2.add(a2);
        set3.add(a3);
        List<Set<Alternative>> list = OldLinearPreferenceImpl
                        .listAlternativeToListSetAlternative(prefs);
        assertEquals(set1, list.get(0));
        assertEquals(set2, list.get(1));
        assertEquals(set3, list.get(2));
    }

    @Test
    public void testToString() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        List<Alternative> prefs = new ArrayList<>();
        prefs.add(a1);
        prefs.add(a2);
        prefs.add(a3);
        OldLinearPreferenceImpl strict = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(prefs);
        assertEquals(strict.toString(), "Alternative{id=1},Alternative{id=2},Alternative{id=3}");
    }

    @Test
    public void testGetAlternative() {
        Alternative a1 = Alternative.withId(1);
        Alternative a2 = Alternative.withId(2);
        Alternative a3 = Alternative.withId(3);
        List<Alternative> prefs = new ArrayList<>();
        prefs.add(a1);
        prefs.add(a2);
        prefs.add(a3);
        OldLinearPreferenceImpl strict = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(prefs);
        assertEquals(strict.getAlternative(1), a2);
    }
}
