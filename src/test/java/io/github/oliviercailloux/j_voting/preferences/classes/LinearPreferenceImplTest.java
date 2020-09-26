package io.github.oliviercailloux.j_voting.preferences.classes;

import static io.github.oliviercailloux.j_voting.Generator.a1;
import static io.github.oliviercailloux.j_voting.Generator.a2;
import static io.github.oliviercailloux.j_voting.Generator.a3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.ImmutableLinearPreference;

class LinearPreferenceImplTest {

	private static Voter v1 = Voter.withId(1);

	private static ImmutableLinearPreference getListedAlternatives() throws EmptySetException, DuplicateValueException {
		return ImmutableLinearPreferenceImpl.given(Voter.withId(3), ImmutableList.of(a1, a3, a2));
	}

	@Test
	void testlistToLinearPreference() throws EmptySetException, DuplicateValueException {
		ImmutableLinearPreference toTest = getListedAlternatives();
		assertEquals(ImmutableList.of(a1, a3, a2), toTest.asList());
	}

	@Test
	void testAsList() throws EmptySetException, DuplicateValueException {
		ImmutableLinearPreference toTest = getListedAlternatives();
		assertEquals(ImmutableList.of(a1, a3, a2), toTest.asList());
	}

	@Test
	void throwsTest() {
		assertThrows(Exception.class, () -> ImmutableLinearPreferenceImpl.given(null, ImmutableList.of(null)));
		assertThrows(Exception.class, () -> ImmutableLinearPreferenceImpl.given(v1, ImmutableList.of(null)));
		assertThrows(Exception.class, () -> ImmutableLinearPreferenceImpl.given(v1, null));
		assertThrows(Exception.class,
				() -> ImmutableLinearPreferenceImpl.given(Voter.withId(1), ImmutableList.of(null)));
		assertThrows(Exception.class, () -> ImmutableLinearPreferenceImpl.given(v1, null));
		assertThrows(Exception.class, () -> ImmutableLinearPreferenceImpl.given(null, ImmutableList.of(null)));
	}
}
