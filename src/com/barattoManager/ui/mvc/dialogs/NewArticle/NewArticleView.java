package com.barattoManager.ui.mvc.dialogs.NewArticle;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.field.Field;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * View that shows the panel used to create a new article
 */
public class NewArticleView implements View {

	private static final String LABEL_ENTER_THE_REQUIRED_FIELDS = "Inserire tutti i campi obbligatori:";
	private final ArrayList<ArticleFieldHasChangeListener> listeners;
	private final JPanel mainPanel;
	@DocumentListenerField
	private final JTextField articleNameField;
	private final ArrayList<Field> articleFields;
	private final ArrayList<JTextField> articleFieldValueJField;

	/**
	 * Constructor of {@link NewArticleView}
	 */
	public NewArticleView() {
		listeners = new ArrayList<>();

		mainPanel = new JPanel();
		articleNameField = new JTextField();
		articleFieldValueJField = new ArrayList<>();
		articleFields = new ArrayList<>();

		mainPanel.setLayout(new GridLayout(0, 1));
	}


	@Override
	public JPanel getMainJPanel() {
		return mainPanel;

	}

	/**
	 * Method used to add a {@link JPanel} to the {@link #mainPanel}
	 *
	 * @param categoryOfArticle used to select the category fields values
	 */
	public void draw(Category categoryOfArticle) {

		mainPanel.add(new JLabel(LABEL_ENTER_THE_REQUIRED_FIELDS));

		var fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridLayout(0, 2));

		fieldsPanel.add(new JLabel("Nome Articolo*:"));
		fieldsPanel.add(articleNameField);

		categoryOfArticle.getCategoryFields().values()
				.forEach(field -> {
					articleFields.add(field);

					fieldsPanel.add(new JLabel("%s%s:".formatted(field.name(), field.required() ? "*" : "")));

					var valueField = new JTextField();

					valueField.getDocument().addDocumentListener(new DocumentListener() {
						@Override
						public void insertUpdate(DocumentEvent e) {
							fireArticleFieldHasChangeListener();
						}

						@Override
						public void removeUpdate(DocumentEvent e) {
							fireArticleFieldHasChangeListener();
						}

						@Override
						public void changedUpdate(DocumentEvent e) {
							fireArticleFieldHasChangeListener();
						}
					});

					articleFieldValueJField.add(valueField);
					fieldsPanel.add(valueField);

				});


		mainPanel.add(fieldsPanel);
	}

	/**
	 * Method used to get the article name from a {@link JTextField}
	 *
	 * @return articleNameField as a {@link String}
	 */
	public String getArticleName() {
		return articleNameField.getText();
	}

	/**
	 * Method used to return an {@link ArrayList} that contains the article fields
	 *
	 * @return {@link ArrayList} that contains the article fields
	 */
	public ArrayList<Field> getArticleFields() {
		return articleFields;
	}

	/**
	 * Method used to return an {@link ArrayList} that contains the fields values
	 *
	 * @return {@link ArrayList} that contains the fields values
	 */
	public ArrayList<String> getFieldsValue() {
		return articleFieldValueJField.stream()
				.map(JTextComponent::getText)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Method used to add a listener
	 *
	 * @param listener {@link ArticleFieldHasChangeListener}
	 */
	public void addArticleFieldHasChangeListener(ArticleFieldHasChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Method used to fire a {@link ArticleFieldHasChangeListener}
	 */
	private void fireArticleFieldHasChangeListener() {
		listeners.forEach(ArticleFieldHasChangeListener::articleFieldHasChange);
	}
}
