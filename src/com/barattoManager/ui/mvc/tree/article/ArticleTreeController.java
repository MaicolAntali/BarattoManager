package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.ArticleUpdateDataEventFactory;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerFor;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedistenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.DefaultMutableTreeNode;

public class ArticleTreeController implements BaseController, ModelDataHasChangeListener {

	private final ArticleTreeModel model;
	private final ArticleTreeView view;

	public ArticleTreeController(YourArticleTreeModel model, ArticleTreeView view) {
		this.model = model;
		this.view = view;

		this.model.addModelDataHasChangeListener(this);
		ArticleUpdateDataEventFactory.getEventHandler().addListener(model);

		this.view.drawTree(model.getArticles());

		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}


	@Override
	public ArticleTreeModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@Override
	public void dataChange() {
		this.view.drawTree(model.getArticles());
		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}

	@TreeNodeSelectedListenerFor(sourceField = "tree")
	private void nodeSelectedChange() {
		Object lastSelectedPathComponent = view.getTree().getLastSelectedPathComponent();

		if (lastSelectedPathComponent != null)
			model.setTreeNodes(((DefaultMutableTreeNode) lastSelectedPathComponent).getPath());
	}

}
