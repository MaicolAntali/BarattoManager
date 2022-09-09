package com.barattoManager.ui.mvc.dialogs.select.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetModel;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetView;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;

/**
 * Controller that handle the events of the {@link SelectView} and update the data in the {@link SelectArticleModel}
 */
public class SelectArticleController extends GraspController {

	private final SelectView<Article> view;
	private final SelectArticleModel model;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link SelectArticleModel} represent the model of the controller
	 * @param view {@link SelectView} represent the view of the controller
	 */
	public SelectArticleController(SelectArticleModel model, SelectView<Article> view) {
		this.model = model;
		this.view = view;

		initAction();

		this.view.addActionNotifierListener(this);
		this.view.draw(this.model.getArticles());

	}

	@Override
	public SelectArticleModel getModel() {
		return model;
	}

	@Override
	public SelectView<Article> getView() {
		return view;
	}

	@Override
	protected void initAction() {
		addAction("comboBoxChanged", () -> model.setArticleSelected(view.getSelectedObject()));
	}
}
