package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NewArticle implements MenuItemAction {
	@Override
	public void run(JPanel fatherPanel) {

		var comboCategoryPanel = new SelectCategoryArticlePanel();
		int result = JOptionPane.showOptionDialog(
				fatherPanel,
				comboCategoryPanel,
				"Aggiungi un nuovo articolo",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (result == JOptionPane.OK_OPTION) {
			System.out.println(comboCategoryPanel.getSelectedCategory().getName() );
		}

	}

	static class SelectCategoryArticlePanel extends JPanel {

		private final JComboBox<String> categoryCombo = new JComboBox<>();

		public SelectCategoryArticlePanel() {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel("Seleziona una categoria per il tuo articolo:"));

			for (String item: getLeafCategory()) {
				categoryCombo.addItem(item);
			}
			mainPanel.add(categoryCombo);

			add(mainPanel);
			setVisible(true);
		}

		public Category getSelectedCategory() {
			return getSelectedCategoryByName(String.valueOf(categoryCombo.getSelectedItem()))
					.orElseThrow(NullPointerException::new);
		}

		private Optional<Category> getSelectedCategoryByName(String categoryToGet) {
			var splitCategories = categoryToGet.split("-");

			Optional<Category> optionalCategory = Optional.empty();
			for (int i = 0; i < splitCategories.length; i++) {
				if (i == 0) {
					optionalCategory = CategoryManager.getInstance().getRootCategoryMap().values().stream()
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

		private ArrayList<String> getLeafCategory() {
			var arrayList = new ArrayList<String>();

			for (Category category : CategoryManager.getInstance().getRootCategoryMap().values()) {
				arrayList.addAll(getLeafCategory(category.getSubCategory().values(), category.getName()));
			}

			return arrayList;
		}

		private ArrayList<String> getLeafCategory(Collection<Category> categories, String string) {
			var arrayList = new ArrayList<String>();


			for (Category category : categories) {
				if (category.getSubCategory().isEmpty()) {
					arrayList.add(string + " - %s".formatted(category.getName()));
				}
				else {
					arrayList.addAll(getLeafCategory(category.getSubCategory().values(), string + " - %s".formatted(category.getName())));
				}
			}

			return arrayList;
		}
	}
}
