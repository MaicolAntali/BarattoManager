package com.barattoManager.manager;

/**
 * Class that contains all constants used in managers
 */
public final class Constants {
	/**
	 * Category params not valid error
	 */
	public static final String ERROR_CATEGORY_PARAMS_NOT_VALID = "Il nome o la descrizione della categoria non è valido";
	/**
	 * Category already exists error
	 */
	public static final String ERROR_CATEGORY_ALREADY_EXISTS = "La categoria che stai creando esiste già.";
	/**
	 * Field already exists error
	 */
	public static final String ERROR_FIELD_ALREADY_EXISTS = "Il campo che stai creando esiste già.";
	/**
	 * No category has been selected error
	 */
	public static final String ERROR_NO_CATEGORY_HAS_BEEN_SELECTED = "Non è stata selezionata una categoria.";
	/**
	 * Invalid name of sub-category error
	 */
	public static final String ERROR_INVALID_NAME_OF_SUBCATEGORY = "Il nome della sotto-categoria non è valido";
	/**
	 * User already exist error
	 */
	public static final String ERROR_USER_ALREADY_EXIST = "L'utente %s esiste gia. Impossible crearlo nuovamente";
	/**
	 * User not found error
	 */
	public static final String ERROR_USER_NOT_FOUND = "L'utente inserito non esiste. Riprovare";
	/**
	 * Password not match error
	 */
	public static final String ERROR_PASSWORD_NOT_MATCH = "La password inserita non è coretta. Riprovare";
	/**
	 * Invalid Username error
	 */
	public static final String ERROR_INVALID_USERNAME = "Lo username non è valido";
	/**
	 * Impossible establish user instance message
	 */
	public static final String MESSAGE_IMPOSSIBLE_ESTABLISH_USER_INSTANCE = "Impossibile stabilire quale istanza di User creare.";
	/**
	 * Post-condition: The user is not present in the map.
	 */
	public static final String POST_CONDITION_USER_NOT_IN_MAP = "Post-condition: The user is not present in the map.";
	/**
	 * Post-Condition: The article is not present in the map
	 */
	public static final String POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP = "Post-Condition: The article is not present in the map";
	/**
	 * Post-condition: The category is not present in the map.
	 */
	public static final String POST_CONDITION_CATEGORY_NOT_IN_MAP = "Post-condition: The category is not present in the map.";
	/**
	 * Post-condition: The sub-category is not present in the map.
	 */
	public static final String POST_CONDITION_SUBCATEGORY_NOT_IN_MAP = "Post-condition: The sub-category is not present in the map.";
	/**
	 * Post-condition: The field is not present in the map.
	 */
	public static final String POST_CONDITION_FIELD_NOT_IN_MAP = "Post-condition: The field is not present in the map.";
}
