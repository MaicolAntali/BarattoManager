package com.barattoManager.ui.mvc.dialogs.NewArticle;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.field.Field;
import com.barattoManager.ui.mvc.Model;

import java.util.ArrayList;

/**
 * Model of {@link NewArticleController} that contains the data
 */
public class NewArticleModel implements Model {

	private final Category articleCategory;
	private String articleName = "";
	private ArrayList<Field> articleFields = new ArrayList<>();
	private ArrayList<String> articleFieldValues = new ArrayList<>();

	/**
	 * Constructor of the class
	 *
	 * @param articleCategory {@link Category}
	 */
	public NewArticleModel(Category articleCategory) {
		this.articleCategory = articleCategory;
	}

	/**
	 * Method used to get an article category as a {@link Category}
	 *
	 * @return article category
	 */
	public Category getArticleCategory() {
		return articleCategory;
	}

	/**
	 * Method used to get an article name as a {@link String}
	 *
	 * @return article name
	 */
	public String getArticleName() {
		return articleName;
	}

	/**
	 * Method used to set an article name as a {@link String}
	 *
	 * @param articleName {@link String} to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
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
	 * Method used to set an article field as a {@link Field}
	 *
	 * @param articleFields {@link Field} to set
	 */
	public void setArticleFields(ArrayList<Field> articleFields) {
		this.articleFields = articleFields;
	}

	/**
	 * Method used to return an {@link ArrayList} that contains the article fields values
	 *
	 * @return {@link ArrayList} that contains the article fields values
	 */
	public ArrayList<String> getArticleFieldValues() {
		return articleFieldValues;
	}

	/**
	 * Method used to set an article field value as a {@link String}
	 *
	 * @param articleFieldValues {@link String} to set
	 */
	public void setArticleFieldValues(ArrayList<String> articleFieldValues) {
		this.articleFieldValues = articleFieldValues;
	}
}
