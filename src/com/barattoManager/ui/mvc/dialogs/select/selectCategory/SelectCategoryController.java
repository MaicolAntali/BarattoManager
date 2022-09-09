package com.barattoManager.ui.mvc.dialogs.select.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleModel;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleView;

import java.util.Objects;
import java.util.Optional;

/**
 * Controller that handle the events of the {@link SelectView} and update the data in the {@link SelectCategoryModel}
 */
public class SelectCategoryController extends GraspController {

	private final SelectCategoryModel model;
	private final SelectView<String> view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link SelectCategoryModel} represent the model of the controller
	 * @param view {@link SelectView} represent the view of the controller
	 */
	public SelectCategoryController(SelectCategoryModel model, SelectView<String> view) {
		this.model = model;
		this.view = view;

		initAction();

		this.view.addActionNotifierListener(this);
		this.view.draw(this.model.getCategoriesName());
	}

	@Override
	public SelectCategoryModel getModel() {
		return model;
	}

	@Override
	public SelectView<String> getView() {
		return view;
	}

	/**
	 * Method used to get an {@link Optional} of a {@link Category} by a string category path
	 * @param categoryPath {@link String} that represent category path <i>("CategoryName - CategoryName2 - ...")</i>
	 * @return {@link Optional} of a {@link Category}
	 */
	public static Optional<Category> getCategoryFromCategoryPath(String categoryPath) {
		var splitCategories = categoryPath.split("-");

		Optional<Category> optionalCategory = Optional.empty();
		for (int i = 0; i < splitCategories.length; i++) {
			if (i == 0) {
				optionalCategory = CategoryManagerFactory.getManager().getRootCategoryMap().values().stream()
						.filter(category -> Objects.equals(category.getName(), splitCategories[0].trim()))
						.findFirst();
			}
			else {
				final int finalI = i;
				optionalCategory = optionalCategory.orElseThrow(NullPointerException::new).getSubCategory().values().stream()
						.filter(category -> Objects.equals(category.getName(), splitCategories[finalI].trim()))
						.findFirst();
			}
		}

		return optionalCategory;
	}
	@Override
	protected void initAction() {
		addAction("comboBoxChanged", () -> model.setCategoryNamesSelected(view.getSelectedObject()));
	}
}
