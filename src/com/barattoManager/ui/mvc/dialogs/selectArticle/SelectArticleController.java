package com.barattoManager.ui.mvc.dialogs.selectArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class SelectArticleController implements BaseController {

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
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "articleComboBox")
	private void comboBoxValueHasChanged() {
		model.setArticleSelected(view.getSelectedArticle());
	}
}
