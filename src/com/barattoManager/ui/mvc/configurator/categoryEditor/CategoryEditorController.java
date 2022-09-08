package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.action.actions.AddCategoryFieldAction;
import com.barattoManager.ui.action.actions.AddRootCategoryAction;
import com.barattoManager.ui.action.actions.AddSubCategoryAction;
import com.barattoManager.ui.action.actions.GoToControllerAction;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeView;
import com.barattoManager.ui.utils.ControllerNames;

public class CategoryEditorController extends GraspController {

	private final CategoryEditorView view;
	private final CategoryTreeController categoryTreeController;

	public CategoryEditorController(CategoryEditorView view) {
		this.view = view;
		this.view.addActionNotifierListener(this);

		categoryTreeController = new CategoryTreeController(
				new CategoryTreeModel(
						CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()
				),
				new CategoryTreeView()
		);
		this.view.setCategoryTree(categoryTreeController.getView().getMainJPanel());

		initAction();
	}

	protected void initAction() {
		addAction("Indietro", new GoToControllerAction(ControllerNames.HOMEPAGE_CONFIGURATOR));
		addAction("Aggiungi_Categoria_Radice", new AddRootCategoryAction(this.view.getMainJPanel()));
		addAction("Aggiungi_Sotto-Categoria", new AddSubCategoryAction(this.categoryTreeController, this.view.getMainJPanel()));
		addAction("Aggiungi_Campo", new AddCategoryFieldAction(this.categoryTreeController, this.view.getMainJPanel()));
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public CategoryEditorView getView() {
		return view;
	}
}
