package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.article.ArticleManager;
import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Class used to create new article
 */
public class NewArticle implements MenuItemAction {
	/**
	 * Title: Add new article
	 */
	private static final String TITLE_ADD_NEW_ARTICLE = "Aggiungi un nuovo articolo";
	/**
	 * Label: Enter all the required fields
	 */
	private static final String LABEL_ENTER_ALL_THE_REQUIRED_FIELDS = "Inserisci tutti i campi obbligatori:";
	/**
	 * Label: Choose a category for your article
	 */
	private static final String LABEL_SELECT_A_CATEGORY_FOR_YOUR_ARTICLE = "Seleziona una categoria per il tuo articolo:";

	/**
	 * Implementation of the method {@link MenuItemAction#run(JPanel, RepaintEventHandler, User, ArticleTree)}
	 * Method used to add new article
	 * @param fatherPanel {@link JPanel}
	 * @param repaintEventHandler {@link RepaintEventHandler} object
	 * @param user {@link User} object
	 * @param articleTree {@link ArticleTree} object
	 */
	@Override
	public void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree) {

		var comboCategoryPanel = new SelectCategoryArticlePanel();
		int result = JOptionPane.showOptionDialog(
				fatherPanel,
				comboCategoryPanel,
				TITLE_ADD_NEW_ARTICLE,
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
					TITLE_ADD_NEW_ARTICLE,
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

	/**
	 * Inner class used to enter the details of an article
	 */
	static class FormArticlePanel extends JPanel {

		/**
		 * ArrayList of {@link Field}
		 */
		private final ArrayList<Field> fields;
		/**
		 * Arraylist of {@link JTextField}
		 */
		private final ArrayList<JTextField> jTextFields;

		/**
		 * {@link FormArticlePanel} constructor
		 * @param category {@link Category}
		 */
		public FormArticlePanel(Category category) {
			fields = new ArrayList<>();
			jTextFields = new ArrayList<>();

			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));

			mainPanel.add(new JLabel(LABEL_ENTER_ALL_THE_REQUIRED_FIELDS));

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

		/**
		 * Method used to get the {@link ArrayList} of fields
		 * @return fields
		 */
		public ArrayList<Field> getFields() {
			return fields;
		}

		/**
		 * Method used to get the {@link ArrayList} of fields values
		 * @return values
		 */
		public ArrayList<String> getValues() {
			var values = new ArrayList<String>();

			for (JTextField jTextField : jTextFields) {
				values.add(jTextField.getText());
			}

			return values;
		}
	}

	/**
	 * Inner class used to select a category for the article to add
	 */
	static class SelectCategoryArticlePanel extends JPanel {
		/**
		 * Category ComboBox
		 */
		private final JComboBox<String> categoryCombo = new JComboBox<>();

		/**
		 * {@link SelectCategoryArticlePanel} constructor
		 */
		public SelectCategoryArticlePanel() {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel(LABEL_SELECT_A_CATEGORY_FOR_YOUR_ARTICLE));

			for (String item : generateComboBoxItems()) {
				categoryCombo.addItem(item);
			}
			mainPanel.add(categoryCombo);

			add(mainPanel);
			setVisible(true);
		}

		/**
		 * Method used to get the selected category in a combo box
		 * @return {@link Category} object
		 */
		public Category getSelectedCategory() {
			return getSelectedCategoryByName(String.valueOf(categoryCombo.getSelectedItem()))
					.orElseThrow(NullPointerException::new);
		}

		/**
		 * Method used to get the selected category as a {@link Optional}
		 * @param categoryToGet Name of a category to get
		 * @return category optional
		 */
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

		/**
		 * Method used to generate the items for the ComboBox of categories
		 * @return ArrayList of categories
		 */
		private ArrayList<String> generateComboBoxItems() {
			var arrayList = new ArrayList<String>();

			for (Category category : CategoryManager.getInstance().getRootCategoryMap().values()) {
				arrayList.addAll(generateComboBoxItems(category.getSubCategory().values(), category.getName()));
			}

			return arrayList;
		}

		/**
		 * Method used to generate the items for the ComboBox of categories
		 * @param categories Collection of categories
		 * @param string String of categories and sub-categories name
		 * @return ArrayList of categories
		 */
		private ArrayList<String> generateComboBoxItems(Collection<Category> categories, String string) {
			var arrayList = new ArrayList<String>();

			for (Category category : categories) {
				if (category.getSubCategory().isEmpty()) {
					arrayList.add(string + " - %s".formatted(category.getName()));
				}
				else {
					arrayList.addAll(generateComboBoxItems(category.getSubCategory().values(), string + " - %s".formatted(category.getName())));
				}
			}

			return arrayList;
		}
	}
}
