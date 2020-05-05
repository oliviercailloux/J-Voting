package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingSet;

import io.github.oliviercailloux.j_voting.Alternative;

public class MutableLinearSetDecorator extends ForwardingSet<Alternative> {

	protected Set<Alternative> delegate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MutableLinearPreferenceImpl.class.getName());

	private MutableLinearSetDecorator(Set<Alternative> delegate) {
		this.delegate = delegate;
	}

	public static MutableLinearSetDecorator given(Set<Alternative> delegate) {
		LOGGER.debug("MutableLinearSetDecorator given");
		Preconditions.checkNotNull(delegate);
		return new MutableLinearSetDecorator(delegate);
	}

	@Override
	protected Set<Alternative> delegate() {
		return delegate;
	}

	@Override
	public boolean add(Alternative a) {
		LOGGER.debug("You are trying to modify a Set<Alternative> from MutableLinearPreference");
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Alternative> c) {
		LOGGER.debug("You are trying to modify a Set<Alternative> from MutableLinearPreference");
		return false;
	}

}
