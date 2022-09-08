package com.barattoManager.ui.mvc.dialogs.NewArticle;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;

/**
 * Controller that handle the events of the {@link NewArticleView} and update the data in the {@link NewArticleModel}
 */
public class NewArticleController implements Controller, ArticleFieldHasChangeListener {

	private final NewArticleModel model;
	private final NewArticleView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link NewArticleModel} represent the model of the controller
	 * @param view {@link  NewArticleView} represent the view of the controller
	 */
	public NewArticleController(NewArticleModel model, NewArticleView view) {
		this.model = model;
		this.view = view;

		this.view.addArticleFieldHasChangeListener(this);

		this.view.draw(this.model.getArticleCategory());
		articleFieldHasChange();

		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewArticleModel getModel() {
		return model;
	}

	@Override
	public View getView() {
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
