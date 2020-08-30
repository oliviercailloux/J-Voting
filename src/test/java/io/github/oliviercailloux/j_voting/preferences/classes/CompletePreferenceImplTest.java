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

    private ImmutableCompletePreferenceImpl generateCompletePrefImpl()
                    throws DuplicateValueException, EmptySetException {
        return (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,
                        ImmutableList.of(a12, ImmutableSet.of(a3)));
    }

    private ImmutableCompletePreferenceImpl generateStrictCompletePref() throws DuplicateValueException, EmptySetException {
        return (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1), ImmutableSet.of(a3)));
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
        assertThrows(Exception.class, () -> ImmutableCompletePreferenceImpl
                        .asCompletePreference(v1, null));
    }

    @Test
    public void asCompletePreferenceTestExceptionTow() {
        assertThrows(Exception.class, () -> ImmutableCompletePreferenceImpl
                        .asCompletePreference(null, null));
    }

    @Test
    public void asCompletePreferenceTestEmptyList()
                    throws Exception {
        List<ImmutableSet<Alternative>> empList = new ArrayList<>();
        ImmutableCompletePreference testCompletePreferenceImpl = ImmutableCompletePreferenceImpl
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
            ImmutableCompletePreferenceImpl.asCompletePreference(v1,
                            ImmutableList.of(a12, ImmutableSet.of(a2)));
        });
    }

    @Test
    public void createEmptySetException() {
        assertThrows(EmptySetException.class, () -> {
            ImmutableCompletePreferenceImpl.asCompletePreference(v1, ImmutableList
                            .of(ImmutableSet.of(), ImmutableSet.of(a2)));
        });
    }

    @Test
    public void containsTest() throws Exception {
    	ImmutableCompletePreferenceImpl toTest = generateCompletePrefImpl();
        assertTrue(toTest.getAlternatives().contains(a2));
    }
    
    @Test
    public void hasSameAlternativesTest() throws Exception {
    	ImmutableCompletePreferenceImpl toTest = generateCompletePrefImpl();
    	ImmutableCompletePreferenceImpl toTest2 = (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1),ImmutableSet.of(a2), ImmutableSet.of(a3)));
    	assertEquals(toTest.getAlternatives(), toTest2.getAlternatives());
    }
    
    @Test
    public void toStrictPreferenceTest() throws Exception {
    	ImmutableCompletePreferenceImpl toTestComplete = (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(ImmutableSet.of(a1),ImmutableSet.of(a3), ImmutableSet.of(a2)));
    	OldLinearPreferenceImpl toTestLinear = OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(ImmutableList.of(a1, a3, a2));
    	assertEquals(toTestLinear,toTestComplete.toStrictPreference());
    }

    @Test
    public void isIncludedInTest() throws Exception {
        ImmutableCompletePreferenceImpl toTestIsContained = (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(a12, ImmutableSet.of(a3)));
        ImmutableCompletePreferenceImpl toTestContains = (ImmutableCompletePreferenceImpl) ImmutableCompletePreferenceImpl.asCompletePreference(v1,ImmutableList.of(a31, ImmutableSet.of(a2), ImmutableSet.of(a4)));
        assertTrue(toTestContains.getAlternatives().containsAll(toTestIsContained.getAlternatives()));
        assertFalse(toTestIsContained.getAlternatives().containsAll(toTestContains.getAlternatives()));
    }

    @Test
    public void alternativeNumberTest() throws Exception {
        ImmutableCompletePreferenceImpl toTest = generateCompletePrefImpl();
        assertEquals(3, toTest.getAlternatives().size());
    }

    @Test
    public void isStrictTest() throws Exception {
        ImmutableCompletePreferenceImpl toTestNonStrict = generateCompletePrefImpl();
        ImmutableCompletePreferenceImpl toTestStrict = generateStrictCompletePref();
        assertFalse(toTestNonStrict.isStrict());
        assertTrue(toTestStrict.isStrict());
    }
}
