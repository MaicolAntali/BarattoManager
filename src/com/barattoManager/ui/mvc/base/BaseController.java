package com.barattoManager.ui.mvc.base;

import java.util.Objects;

public class BaseController<M, V> {

	private final M model;
	private final V view;

	public BaseController(M model, V view) {
		this.model = Objects.requireNonNull(model, "Model can not be null");;
		this.view = Objects.requireNonNull(view, "View can not be null");;
	}

	public M getModel() {
		return model;
	}

	public V getView() {
		return view;
	}
}
