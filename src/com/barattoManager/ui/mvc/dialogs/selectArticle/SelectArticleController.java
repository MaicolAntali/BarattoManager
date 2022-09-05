package com.barattoManager.ui.mvc.dialogs.selectArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.View;

public class SelectArticleController implements Controller {

	private final SelectArticleView view;
	private final SelectArticleModel model;

	public SelectArticleController(SelectArticleModel model, SelectArticleView view) {
		this.model = model;
		this.view = view;

		this.view.draw(this.model.getArticles());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public SelectArticleModel getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "articleComboBox")
	private void comboBoxValueHasChanged() {
		model.setArticleSelected(view.getSelectedArticle());
	}
}
