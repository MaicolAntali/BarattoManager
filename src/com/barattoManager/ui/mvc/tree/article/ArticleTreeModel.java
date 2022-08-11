package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.List;

public interface ArticleTreeModel extends BaseModel {

	List<Article> getArticles();
	TreeNode[] getTreeNodes();
	void setTreeNodes(TreeNode[] treeNodes);
	void addModelDataHasChangeListener(ModelDataHasChangeListener listener);
}
