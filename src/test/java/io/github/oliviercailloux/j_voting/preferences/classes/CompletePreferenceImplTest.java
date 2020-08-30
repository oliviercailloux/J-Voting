package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.ImmutableCompletePreference;

class CompletePreferenceImplTest {

    private static Voter v1 = Voter.createVoter(1);

    private CompletePreferenceImpl generateCompletePrefImpl()
                    throws DuplicateValueException, EmptySetException {
        return (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,
                        ImmutableList.of(a12, ImmutableSet.of(a3)));
    }

    private CompletePreferenceImpl generateStrictCompletePref() throws DuplicateValueException, EmptySetException {
        return (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1), ImmutableSet.of(a3)));
    }

    @Test
    void getRankTest() throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
        assertEquals(1, toTest.getRank(a1));
        assertEquals(2, toTest.getRank(a3));
    }

    @Test
    public void getRankExceptionTest()
                    throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
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
                    throws Exception {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        ImmutableCompletePreference testCompletePreferenceImpl = CompletePreferenceImpl
                        .asCompletePreference(v1, empList);
        assertTrue(testCompletePreferenceImpl.asGraph().edges().isEmpty());
    }

    @Test
    public void getAlternativesTest()
                    throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
        ImmutableSet<Alternative> immutableSet = ImmutableSet.of(a1, a2);
        assertEquals(immutableSet, toTest.getAlternatives(1));
    }

    @Test
    public void getAlternativesExceptionTest()
                    throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                        () -> toTest.getAlternatives(3));
    }

    @Test
    public void asEquivalenceClassesTest()
                    throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
        List<ImmutableSet<Alternative>> list = new ArrayList<>(
                        Arrays.asList(ImmutableSet.of(a1, a2),
                                        ImmutableSet.of(a3)));
        assertEquals(list, toTest.asEquivalenceClasses());
    }

    @Test
    public void getVoterTest()
                    throws Exception {
        ImmutableCompletePreference toTest = generateCompletePrefImpl();
        assertEquals(v1, toTest.getVoter());
    }

    @Test
    public void createDuplicateException() {
        assertThrows(DuplicateValueException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(v1,
                            ImmutableList.of(a12, ImmutableSet.of(a2)));
        });
    }

    @Test
    public void createEmptySetException() {
        assertThrows(EmptySetException.class, () -> {
            CompletePreferenceImpl.asCompletePreference(v1, ImmutableList
                            .of(ImmutableSet.of(), ImmutableSet.of(a2)));
        });
    }

    @Test
    public void containsTest() throws Exception {
    	CompletePreferenceImpl toTest = generateCompletePrefImpl();
        assertTrue(toTest.getAlternatives().contains(a2));
    }
    
    @Test
    public void hasSameAlternativesTest() throws Exception {
    	CompletePreferenceImpl toTest = generateCompletePrefImpl();
    	CompletePreferenceImpl toTest2 = (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1),ImmutableSet.of(a2), ImmutableSet.of(a3)));
    	assertEquals(toTest.getAlternatives(), toTest2.getAlternatives());
    }
    
    @Test
    public void toStrictPreferenceTest() throws Exception {
    	CompletePreferenceImpl toTestComplete = (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1),ImmutableSet.of(a3), ImmutableSet.of(a2)));
    	OldLinearPreferenceImpl toTestLinear = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(ImmutableList.of(a1, a3, a2));
    	assertEquals(toTestLinear,toTestComplete.toStrictPreference());
    }

    @Test
    public void isIncludedInTest() throws Exception {
        CompletePreferenceImpl toTestIsContained = (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(a12, ImmutableSet.of(a3)));
        CompletePreferenceImpl toTestContains = (CompletePreferenceImpl) CompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(a31, ImmutableSet.of(a2), ImmutableSet.of(a4)));
        assertTrue(toTestContains.getAlternatives().containsAll(toTestIsContained.getAlternatives()));
        assertFalse(toTestIsContained.getAlternatives().containsAll(toTestContains.getAlternatives()));
    }

    @Test
    public void alternativeNumberTest() throws Exception {
        CompletePreferenceImpl toTest = generateCompletePrefImpl();
        assertEquals(3, toTest.getAlternatives().size());
    }

    @Test
    public void isStrictTest() throws Exception {
        CompletePreferenceImpl toTestNonStrict = generateCompletePrefImpl();
        CompletePreferenceImpl toTestStrict = generateStrictCompletePref();
        assertFalse(toTestNonStrict.isStrict());
        assertTrue(toTestStrict.isStrict());
    }
}
