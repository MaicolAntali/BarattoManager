package com.barattoManager.article;

import com.barattoManager.category.field.Field;
import com.barattoManager.category.field.FieldDeserializer;
import com.barattoManager.utils.History;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Class that represent an article
 */
@JsonPropertyOrder({ "uuid", "owner", "category_uuid", "state", "fields", "log" })
public class Article {
	/**
	 * Not Valid fields error
	 */
	private static final String NOT_VALID_FIELDS_ERROR = "Not Valid fields";
	/**
	 * One or more required fields have not been properly initialized error description
	 */
	private static final String FIELDS_HAVE_NOT_BEEN_INITIALIZED_ERROR_DESCRIPTION = "One or more required fields have not been properly initialized";
	/**
	 * Article Validated message
	 */
	private static final String ARTICLE_VALIDATED = "Article Validated";
	/**
	 * "Article is valid and properly initialized description
	 */
	private static final String ARTICLE_IS_VALID_AND_PROPERLY_INITIALIZED_DESCRIPTION = "Article is valid and properly initialized";
	/**
	 * "Pre-condition: Fields values ArrayList is empty"
	 */
	private static final String PRE_CONDITION_FIELDS_VALUES_ARRAY_LIST_IS_EMPTY = "Pre-condition: Values ArrayList is empty";
	/**
	 * "Post-condition: Field value Map is empty"
	 */
	private static final String POST_CONDITION_FIELD_VALUE_MAP_IS_EMPTY = "Post-condition: Fields values Map is empty";
	/**
	 * Post-condition: Owner userName is empty
	 */
	private static final String POST_CONDITION_OWNER_USER_NAME_IS_EMPTY = "Post-condition: Owner userName is empty";
	/**
	 * Article uuid
	 */
	@JsonProperty("uuid")
	private final String uuid;
	/**
	 * Article owner
	 */
	@JsonProperty("owner")
	private final String userNameOwner;
	/**
	 * Article category uuid
	 */
	@JsonProperty("category_uuid")
	private final String categoryUuid;
	/**
	 * Article fields
	 */
	@JsonProperty("fields")
	@JsonDeserialize(keyUsing = FieldDeserializer.class)
	private final HashMap<Field, String> fieldValueMap;
	/**
	 * Article log
	 */
	@JsonProperty("log")
	private final ArrayList<History> history;
	/**
	 * Article state
	 */
	@JsonProperty("state")
	private State articleState;

	/**
	 * enum that represents the name of the article states
	 */
	public enum State {
		/**
		 * Not checked italian label
		 */
		NOT_CHECKED("Non validato"),
		/**
		 * Open offer italian label
		 */
		OPEN_OFFER("Offerta Aperta"),
		/**
		 * Cancelled offer italian label
		 */
		CANCELLED_OFFER("Offerta Cancellata");

		/**
		 * Label in italian
		 */
		private final String italianLabel;

		/**
		 * {@link State} constructor
		 * @param italianLabel label in italian
		 */
		State(String italianLabel) {
			this.italianLabel = italianLabel;
		}

		/**
		 * Method used to get italianLabel
		 * @return italianLabel
		 */
		@Override
		public String toString() {
			return this.italianLabel;
		}
	}

	/**
	 * {@link Article} constructor
	 * @param userNameOwner Owner name of article
	 * @param categoryUuid Category uuid of article
	 * @param fields {@link ArrayList} that contains fields name of article
	 * @param values {@link ArrayList} that contains fields values
	 */
	public Article(String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		this.history = new ArrayList<>();
		history.add(new History("Article Created", "Article has just been created by the viewer %s".formatted(userNameOwner)));

		assert !values.isEmpty() : PRE_CONDITION_FIELDS_VALUES_ARRAY_LIST_IS_EMPTY;

		this.uuid = UUID.randomUUID().toString();
		this.userNameOwner = userNameOwner;
		this.categoryUuid = categoryUuid;
		this.articleState = State.NOT_CHECKED;
		this.fieldValueMap = createValidateFieldValueMap(fields, values);

		assert !fieldValueMap.isEmpty() : POST_CONDITION_FIELD_VALUE_MAP_IS_EMPTY;
	}


	/**
	 * {@link Article} constructor
	 * @param uuid Uuid of article
	 * @param userNameOwner Owner name of article
	 * @param categoryUuid Category uuid of article
	 * @param fieldValueMap {@link HashMap} that contains fields values
	 * @param history {@link ArrayList} that contains the history of article
	 * @param articleState {@link State} of article
	 */
	public Article(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("user") String userNameOwner,
			@JsonProperty("category_uuid") String categoryUuid,
			@JsonProperty("fields") HashMap<Field, String> fieldValueMap,
			@JsonProperty("log") ArrayList<History> history,
			@JsonProperty("state") State articleState
	) {

		this.uuid = uuid;
		this.userNameOwner = userNameOwner;
		this.categoryUuid = categoryUuid;
		this.fieldValueMap = fieldValueMap;
		this.history = history;
		this.articleState = articleState;

		assert userNameOwner.isEmpty() : POST_CONDITION_OWNER_USER_NAME_IS_EMPTY;
	}

	/**
	 * Method used to get the uuid of the article
	 * @return UUID of the article
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the owner name of the article
	 * @return Owner name of the article
	 */
	public String getUserNameOwner() {
		return userNameOwner;
	}

	/**
	 * Method used to get the category uuid of the article
	 * @return Category uuid of the article
	 */
	public String getCategoryUuid() {
		return categoryUuid;
	}

	/**
	 * Method used to get the state of the article
	 * @return State of the article
	 */
	public State getArticleState() {
		return articleState;
	}

	/**
	 * Method used to get the fields values map
	 * @return Fields values map of the article
	 */
	public HashMap<Field, String> getFieldValueMap() {
		return fieldValueMap;
	}

	/**
	 * Method used to get the history of the article
	 * @return History of the article
	 */
	public ArrayList<History> getHistory() {
		return history;
	}

	/**
	 * Method used to update the state of {@link #articleState} and log the changes
	 * @param state new {@link State}
	 */
	public void changeState(State state) {
		history.add(new History("State Update", "The article state is updated from %s to %s".formatted(this.articleState, state)));
		this.articleState = state;
		ArticleManager.getInstance().forceSaveData();
	}

	/**
	 * Method used to create a hashmap and validate it (All required fields must be init).
	 * @param fields {@link ArrayList} of fields
	 * @param values {@link ArrayList} of values
	 * @return {@link HashMap} KEY: fields VALUE: values
	 */
	private HashMap<Field, String> createValidateFieldValueMap(ArrayList<Field> fields, ArrayList<String> values) {
		var map = new HashMap<Field, String>();
		for (int i = 0; i < fields.size(); i++) {
			map.put(fields.get(i), values.get(i));
		}


		var isNotValideMap = map.entrySet().stream()
				.anyMatch(entry -> entry.getKey().required() && entry.getValue().isBlank());


		if (isNotValideMap) {
			history.add(new History(NOT_VALID_FIELDS_ERROR, FIELDS_HAVE_NOT_BEEN_INITIALIZED_ERROR_DESCRIPTION, true));
			changeState(State.CANCELLED_OFFER);
		}
		else {
			history.add(new History(ARTICLE_VALIDATED, ARTICLE_IS_VALID_AND_PROPERLY_INITIALIZED_DESCRIPTION));
			changeState(State.OPEN_OFFER);
		}

		return map;
	}
}
