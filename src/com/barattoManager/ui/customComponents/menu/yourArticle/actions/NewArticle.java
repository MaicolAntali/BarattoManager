package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.article.ArticleManager;
import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class NewArticle implements MenuItemAction {
	@Override
	public void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user) {

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
			var categorySelected = comboCategoryPanel.getSelectedCategory();
			var formPanel = new FormArticlePanel(categorySelected);
			int resultFormPanel = JOptionPane.showOptionDialog(
					fatherPanel,
					formPanel,
					"Aggiungi un nuovo articolo",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (resultFormPanel == JOptionPane.OK_OPTION) {
				ArticleManager.getInstance().addNewArticle(
						user.getUsername(),
						categorySelected.getUuid(),
						formPanel.getFields(),
						formPanel.getValues()
				);

				repaintEventHandler.fireListeners();
			}
		}

	}

	static class FormArticlePanel extends JPanel {

		private final ArrayList<Field> fields;
		private final ArrayList<JTextField> jTextFields;

		public FormArticlePanel(Category category) {
			fields = new ArrayList<>();
			jTextFields = new ArrayList<>();

			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));

			mainPanel.add(new JLabel("Inserisci tutti i campi obbligatori:"));

			var fieldsPanel = new JPanel();
			fieldsPanel.setLayout(new GridLayout(0, 2));
			for (Field field : category.getCategoryFields().values()) {
				fields.add(field);
				fieldsPanel.add(new JLabel("%s%s".formatted(field.name(), field.required() ? "*" : "")));

				var valueField = new JTextField();
				jTextFields.add(valueField);
				fieldsPanel.add(valueField);
			}
			mainPanel.add(fieldsPanel, BorderLayout.CENTER);

			add(mainPanel);
			setVisible(true);
		}

		public ArrayList<Field> getFields() {
			return fields;
		}

		public ArrayList<String> getValues() {
			var values = new ArrayList<String>();

			for (JTextField jTextField : jTextFields) {
				values.add(jTextField.getText());
			}

			return values;
		}
	}

	static class SelectCategoryArticlePanel extends JPanel {

		private final JComboBox<String> categoryCombo = new JComboBox<>();

		public SelectCategoryArticlePanel() {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel("Seleziona una categoria per il tuo articolo:"));

			for (String item : getLeafCategory()) {
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
