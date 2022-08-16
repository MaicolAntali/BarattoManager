package com.barattoManager.ui.mvc.dialogs.NewArticle;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class NewArticleController implements BaseController, ArticleFieldHasChangeListener {

	private final NewArticleModel model;
	private final NewArticleView view;

	public NewArticleController(NewArticleModel model, NewArticleView view) {
		this.model = model;
		this.view = view;

		this.view.addArticleFieldHasChangeListener(this);

		this.view.draw(this.model.getArticleCategory());

		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewArticleModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@DocumentListenerFor(sourceField = "articleNameField")
	private void articleNameHasChanges() {
		model.setArticleName(view.getArticleName());
	}

	@Override
	public void articleFieldHasChange() {
		model.setArticleFields(view.getArticleFields());
		model.setArticleFieldValues(view.getFieldsValue());
	}
}
