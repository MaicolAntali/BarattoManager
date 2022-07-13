package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.manager.factory.CategoryManagerFactory;
import com.barattoManager.model.category.Category;
import com.barattoManager.model.category.field.Field;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * New article action
 */
public class NewArticle implements MenuAction {

	private static final String TITLE_ADD_NEW_ARTICLE = "Aggiungi un nuovo articolo";
	private static final String LABEL_ENTER_ALL_THE_REQUIRED_FIELDS = "Inserisci tutti i campi obbligatori:";
	private static final String LABEL_SELECT_A_CATEGORY_FOR_YOUR_ARTICLE = "Seleziona una categoria per il tuo articolo:";
	private static final String ERROR_ARTICLE_NAME_EMPTY = "Nome dell'articolo vuoto";
	private static final String ERROR_THERE_ARE_NO_CATEGORY_AVAILABLE = "Non ci sono categorie disponibili.";

	@Override
	public void run(User user, Tree tree) {

		SelectCategoryArticlePanel comboCategoryPanel;
		try {
			comboCategoryPanel = new SelectCategoryArticlePanel();
		} catch (IllegalValuesException e) {
			JOptionPane.showMessageDialog(tree, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int result = JOptionPane.showOptionDialog(
				tree,
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
			var formPanel        = new FormArticlePanel(categorySelected);
			int resultFormPanel = JOptionPane.showOptionDialog(
					tree,
					formPanel,
					TITLE_ADD_NEW_ARTICLE,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (resultFormPanel == JOptionPane.OK_OPTION) {

				if (formPanel.getArticleName().getText().trim().isBlank()) {
					JOptionPane.showMessageDialog(tree, ERROR_ARTICLE_NAME_EMPTY, "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}

				ArticleManagerFactory.getManager().addNewArticle(
						formPanel.getArticleName().getText(),
						user.getUsername(),
						categorySelected.getUuid(),
						formPanel.getFields(),
						formPanel.getValues()
				);
			}
		}
	}

	/**
	 * Inner class used to enter the details of an article
	 */
	static class FormArticlePanel extends JPanel {

		private final ArrayList<Field> fields;
		private final ArrayList<JTextField> jTextFields;
		private final JTextField articleName;

		/**
		 * Constructor of the class
		 *
		 * @param category {@link Category} object where it to take the fields
		 */
		public FormArticlePanel(Category category) {
			fields = new ArrayList<>();
			jTextFields = new ArrayList<>();

			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));

			mainPanel.add(new JLabel(LABEL_ENTER_ALL_THE_REQUIRED_FIELDS));

			var fieldsPanel = new JPanel();
			fieldsPanel.setLayout(new GridLayout(0, 2));

			fieldsPanel.add(new JLabel("Nome Articolo*"));
			this.articleName = new JTextField();
			fieldsPanel.add(articleName);

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
		 *
		 * @return fields
		 */
		public ArrayList<Field> getFields() {
			return fields;
		}

		/**
		 * Method used to get the {@link ArrayList} of fields values
		 *
		 * @return values
		 */
		public ArrayList<String> getValues() {
			var values = new ArrayList<String>();

			for (JTextField jTextField : jTextFields) {
				values.add(jTextField.getText());
			}

			return values;
		}

		/**
		 * Method used to get {@link JTextField} with the article name
		 *
		 * @return {@link JTextField} with the article name
		 */
		public JTextField getArticleName() {
			return articleName;
		}
	}

	/**
	 * Inner class used to select a category for the article to add
	 */
	static class SelectCategoryArticlePanel extends JPanel {

		private final JComboBox<String> categoryCombo = new JComboBox<>();

		/**
		 * Constructor of the class
		 */
		public SelectCategoryArticlePanel() throws IllegalValuesException {
			var mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));


			mainPanel.add(new JLabel(LABEL_SELECT_A_CATEGORY_FOR_YOUR_ARTICLE));

			ArrayList<String> categories = generateComboBoxItems();

			if (categories.isEmpty())
				throw new IllegalValuesException(ERROR_THERE_ARE_NO_CATEGORY_AVAILABLE);

			for (String item : categories) {
				categoryCombo.addItem(item);
			}
			mainPanel.add(categoryCombo);

			add(mainPanel);
			setVisible(true);
		}

		/**
		 * Method used to get the selected category in a combo box
		 *
		 * @return {@link Category} object selected in the combo box
		 */
		public Category getSelectedCategory() {
			return getSelectedCategoryByName(String.valueOf(categoryCombo.getSelectedItem()))
					.orElseThrow(NullPointerException::new);
		}

		private Optional<Category> getSelectedCategoryByName(String categoryToGet) {
			var splitCategories = categoryToGet.split("-");

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

		private ArrayList<String> generateComboBoxItems() {
			var arrayList = new ArrayList<String>();

			for (Category category : CategoryManagerFactory.getManager().getRootCategoryMap().values()) {
				arrayList.addAll(generateComboBoxItems(category.getSubCategory().values(), category.getName()));
			}

			return arrayList;
		}

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
