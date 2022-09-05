package com.barattoManager.ui.mvc.dialogs.NewArticle;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.field.Field;
import com.barattoManager.ui.mvc.Model;

import java.util.ArrayList;

public class NewArticleModel implements Model {

	private final Category articleCategory;
	private String articleName = "";
	private ArrayList<Field> articleFields = new ArrayList<>();
	private ArrayList<String> articleFieldValues = new ArrayList<>();

	public NewArticleModel(Category articleCategory) {
		this.articleCategory = articleCategory;
	}

	public Category getArticleCategory() {
		return articleCategory;
	}

	public String getArticleName() {
		return articleName;
	}


	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public ArrayList<Field> getArticleFields() {
		return articleFields;
	}

	public void setArticleFields(ArrayList<Field> articleFields) {
		this.articleFields = articleFields;
	}

	public ArrayList<String> getArticleFieldValues() {
		return articleFieldValues;
	}

	public void setArticleFieldValues(ArrayList<String> articleFieldValues) {
		this.articleFieldValues = articleFieldValues;
	}
}
