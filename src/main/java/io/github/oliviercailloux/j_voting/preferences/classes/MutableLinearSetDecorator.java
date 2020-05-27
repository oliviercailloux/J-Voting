package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingSet;

import io.github.oliviercailloux.j_voting.Alternative;

public class MutableLinearSetDecorator extends ForwardingSet<Alternative> {

	protected MutableLinearPreferenceImpl delegate;
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableLinearPreferenceImpl.class.getName());

	private MutableLinearSetDecorator(MutableLinearPreferenceImpl delegate) {
		this.delegate = delegate;
	}

	public static MutableLinearSetDecorator given(MutableLinearPreferenceImpl delegate) {
		LOGGER.debug("MutableLinearSetDecorator given");
		Preconditions.checkNotNull(delegate);
		return new MutableLinearSetDecorator(delegate);
	}

	@Override
	protected Set<Alternative> delegate() {
		return delegate.alternatives;
	}

	@Override
	public boolean add(Alternative a) {
		LOGGER.debug("MutableLinearSetDecorator add");
		if (delegate.getAlternatives().contains(a)){
			return false;
		}
		return delegate.addAlternative(a);
	}

	@Override
	public boolean addAll(Collection<? extends Alternative> c) {
		LOGGER.debug("MutableLinearSetDecorator addAll");
		boolean hasChanged = false;		
		for (Iterator<? extends Alternative> iterator = c.iterator(); iterator.hasNext();) {
			Alternative alternative = iterator.next();
			if (!  delegate.getAlternatives().contains(alternative)) {
				hasChanged = delegate.addAlternative(alternative);
			}		
		}
		return hasChanged;
	}
	
	@Override
	public boolean remove(Object o) {
		LOGGER.debug("MutableLinearSetDecorator remove");
		
		if (o instanceof Alternative) {
			Alternative a = (Alternative) o;
			if ( delegate.getAlternatives().contains(a)) {
				return delegate.removeAlternative(a);
			}
		}
		return false;		
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		LOGGER.debug("MutableLinearSetDecorator removeAll");
		boolean hasChanged = false;
		for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
			Alternative alternative = (Alternative) iterator.next();
			if (delegate.getAlternatives().contains(alternative)) {
				hasChanged = delegate.removeAlternative(alternative);
			}
		}		
		return hasChanged;		
	}
	
	@Override
	public void clear() {
		LOGGER.debug("MutableLinearSetDecorator clear");
		delegate.clear();
	}

}
