package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a12;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

class CompletePreferenceImplTest {

    private static Voter v1 = Voter.createVoter(1);

    private CompletePreference getTwoClassesPreference()
                    throws DuplicateValueException, EmptySetException {
        return CompletePreferenceImpl.asCompletePreference(v1,
                        ImmutableList.of(a12, ImmutableSet.of(a3)));
    }

    @Test
    void getRankTest() throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertEquals(1, toTest.getRank(a1));
        assertEquals(2, toTest.getRank(a3));
    }

    @Test
    public void getRankExceptionTest()
                    throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertThrows(Exception.class, () -> toTest.getRank(a4));
    }

    @Test
    public void asCompletePreferenceTestException() {
        assertThrows(Exception.class, () -> CompletePreferenceImpl
                        .asCompletePreference(v1, null));
    }

    @Test
    public void asCompletePreferenceTestExceptionTow() {
        assertThrows(Exception.class, () -> CompletePreferenceImpl
                        .asCompletePreference(null, null));
    }

    @Test
    public void asCompletePreferenceTestEmptyList()
                    throws DuplicateValueException, EmptySetException {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        CompletePreference testCompletePreferenceImpl = CompletePreferenceImpl
                        .asCompletePreference(v1, empList);
        assertTrue(testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest()
                    throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        ImmutableSet<Alternative> immutableSet = ImmutableSet.of(a1, a2);
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest()
                    throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest()
                    throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        List<ImmutableSet<Alternative>> list = new ArrayList<ImmutableSet<Alternative>>(
                        Arrays.asList(ImmutableSet.of(a1, a2),
                                        ImmutableSet.of(a3)));
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest()
                    throws DuplicateValueException, EmptySetException {
        CompletePreference toTest = getTwoClassesPreference();
        assertEquals(v1, toTest.getVoter());
    }

    @Test
    public void createDuplicateException() throws DuplicateValueException {
        assertThrows(DuplicateValueException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(v1,
                            ImmutableList.of(a12, ImmutableSet.of(a2)));
        });
    }

    @Test
    public void createEmptySetException() throws DuplicateValueException {
        assertThrows(EmptySetException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(v1, ImmutableList
                            .of(ImmutableSet.of(), ImmutableSet.of(a2)));
        });
    }
}
