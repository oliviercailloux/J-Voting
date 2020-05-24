package io.github.oliviercailloux.j_voting.preferences.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graph;

import io.github.oliviercailloux.j_voting.Alternative;

public class MutableLinearGraphDecorator extends ForwardingGraph<Alternative> {
	
	protected MutableLinearPreferenceImpl delegate;
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableLinearPreferenceImpl.class.getName());

	@Override
	protected Graph<Alternative> delegate() {
		return delegate.graph;
	}
	
	private MutableLinearGraphDecorator(MutableLinearPreferenceImpl delegate) {
		this.delegate = delegate;
	}

	public static MutableLinearGraphDecorator given(MutableLinearPreferenceImpl delegate) {
		LOGGER.debug("MutableLinearSetDecorator given");
		Preconditions.checkNotNull(delegate);
		return new MutableLinearGraphDecorator(delegate);
	}
}
