package com.barattoManager.model.article;

import com.barattoManager.model.category.field.Field;
import com.barattoManager.model.category.field.FieldDeserializer;
import com.barattoManager.model.history.History;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Class representing the object Article
 */
@JsonPropertyOrder({"uuid", "owner", "category_uuid", "state", "fields", "log"})
public class Article {

	private static final String NOT_VALID_FIELDS_ERROR = "Not Valid fields";
	private static final String FIELDS_HAVE_NOT_BEEN_INITIALIZED_ERROR_DESCRIPTION = "One or more required fields have not been properly initialized";
	private static final String ARTICLE_VALIDATED = "Article Validated";
	private static final String ARTICLE_CREATED = "Article Created";
	private static final String ARTICLE_HAS_JUST_BEEN_CREATED_BY_THE_VIEWER = "Article has just been created by the viewer %s";
	private static final String ARTICLE_IS_VALID_AND_PROPERLY_INITIALIZED_DESCRIPTION = "Article is valid and properly initialized";
	private static final String PRE_CONDITION_FIELDS_ARRAY_LIST_IS_EMPTY = "Pre-condition: Fields ArrayList is empty";
	private static final String POST_CONDITION_FIELD_VALUE_MAP_IS_EMPTY = "Post-condition: Fields values Map is empty";
	private static final String PRE_CONDITION_OWNER_USER_NAME_IS_EMPTY = "Pre-condition: Owner userName is empty";
	private static final String PRE_CONDITION_UUID_IS_EMPTY = "Pre-condition: Uuid is empty";
	private static final String PRE_CONDITION_CATEGORY_UUID_IS_EMPTY = "Pre-condition: Category Uuid is empty";
	private static final String PRE_CONDITION_HISTORY_IS_EMPTY = "Pre-condition: History is empty";
	private static final String PRE_CONDITION_FIELDS_MAP_IS_EMPTY = "Pre-condition: Fields value map is empty";
	private static final String STATE_UPDATED = "The article state is updated from %s to %s";

	@JsonProperty("uuid")
	private final String uuid;
	@JsonProperty("name")
	private final String articleName;
	@JsonProperty("owner")
	private final String userNameOwner;
	@JsonProperty("category_uuid")
	private final String categoryUuid;
	@JsonProperty("fields")
	@JsonDeserialize(keyUsing = FieldDeserializer.class)
	private final HashMap<Field, String> fieldValueMap;
	@JsonProperty("log")
	private final ArrayList<History> history;
	@JsonProperty("state")
	private State articleState;

	/**
	 * Enum with the different states that an article can have
	 */
	public enum State {
		/**
		 * Article status when its fields have not yet been validated
		 */
		NOT_CHECKED("Non validato"),
		/**
		 * The Article is correct and can be bartered
		 */
		OPEN_OFFER("Offerta Aperta"),
		/**
		 * The Article is not valid or its owner no longer wants to trade it
		 */
		CANCELLED_OFFER("Offerta Cancellata"),
		/**
		 * The owner of the article decided to exchange it for an article
		 */
		LINKED_OFFER("Offerta accoppiata"),
		/**
		 * The owner of the article received a barter proposal
		 */
		SELECTED_OFFER("Offerta selezionata"),
		/**
		 * The article is being exchanged with another article
		 */
		IN_TRADE_OFFER("Offerta in Scambio"),
		/**
		 * Exchange ended. The article was bartered
		 */
		CLOSE_OFFER("Offerta chiusa");

		private final String italianLabel;

		/**
		 * Constructor of the enum
		 *
		 * @param italianLabel String representing the Italian translation
		 */
		State(String italianLabel) {
			this.italianLabel = italianLabel;
		}

		@Override
		public String toString() {
			return this.italianLabel;
		}
	}

	/**
	 * Constructor of the class
	 *
	 * @param articleName   Name of the article
	 * @param userNameOwner Username of the article owner
	 * @param categoryUuid  Uuid of the category
	 * @param fields        {@link ArrayList} that contains all the name of the article fields
	 * @param values        {@link ArrayList} that contains all the values of the article fields
	 */
	public Article(String articleName, String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		assert !categoryUuid.isEmpty() : PRE_CONDITION_CATEGORY_UUID_IS_EMPTY;
		assert !userNameOwner.isEmpty() : PRE_CONDITION_OWNER_USER_NAME_IS_EMPTY;
		assert !fields.isEmpty() : PRE_CONDITION_FIELDS_ARRAY_LIST_IS_EMPTY;
		assert !values.isEmpty() : PRE_CONDITION_FIELDS_ARRAY_LIST_IS_EMPTY;


		this.history = new ArrayList<>();
		history.add(new History(ARTICLE_CREATED, ARTICLE_HAS_JUST_BEEN_CREATED_BY_THE_VIEWER.formatted(userNameOwner)));

		this.uuid = UUID.randomUUID().toString();
		this.articleName = articleName;
		this.userNameOwner = userNameOwner;
		this.categoryUuid = categoryUuid;
		this.articleState = State.NOT_CHECKED;
		this.fieldValueMap = createValidateFieldValueMap(fields, values);

		assert !fieldValueMap.isEmpty() : POST_CONDITION_FIELD_VALUE_MAP_IS_EMPTY;
	}


	/**
	 * Constructor of the class
	 *
	 * @param uuid          Uuid of the article
	 * @param articleName   Name of article
	 * @param userNameOwner Username of the article owner
	 * @param categoryUuid  Uuid of the category
	 * @param fieldValueMap {@link HashMap} that contains fields and values
	 * @param history       {@link ArrayList} that contains the logs/history
	 * @param articleState  {@link State} of the article
	 */
	public Article(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("name") String articleName,
			@JsonProperty("owner") String userNameOwner,
			@JsonProperty("category_uuid") String categoryUuid,
			@JsonProperty("fields") HashMap<Field, String> fieldValueMap,
			@JsonProperty("log") ArrayList<History> history,
			@JsonProperty("state") State articleState
	) {
		assert !uuid.isEmpty() : PRE_CONDITION_UUID_IS_EMPTY;
		assert !categoryUuid.isEmpty() : PRE_CONDITION_CATEGORY_UUID_IS_EMPTY;
		assert !userNameOwner.isEmpty() : PRE_CONDITION_OWNER_USER_NAME_IS_EMPTY;
		assert !fieldValueMap.isEmpty() : PRE_CONDITION_FIELDS_MAP_IS_EMPTY;
		assert !history.isEmpty() : PRE_CONDITION_HISTORY_IS_EMPTY;

		this.uuid = uuid;
		this.articleName = articleName;
		this.userNameOwner = userNameOwner;
		this.categoryUuid = categoryUuid;
		this.fieldValueMap = fieldValueMap;
		this.history = history;
		this.articleState = articleState;
	}

	/**
	 * Method used to get the uuid of the article
	 *
	 * @return Uuid of the article
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the article name
	 *
	 * @return Article name
	 */
	public String getArticleName() {
		return articleName;
	}

	/**
	 * Method used to get the owner name of the article
	 *
	 * @return Owner name of the article
	 */
	public String getUserNameOwner() {
		return userNameOwner;
	}

	/**
	 * Method used to get the category uuid of the article
	 *
	 * @return Category uuid of the article
	 */
	public String getCategoryUuid() {
		return categoryUuid;
	}

	/**
	 * Method used to get the state of the article
	 *
	 * @return {@link State} of the article
	 */
	public State getArticleState() {
		return articleState;
	}

	/**
	 * Method used to return an {@link HashMap} that contains fields and values
	 *
	 * @return {@link HashMap} of {@link Field} and {@link String value}
	 */
	public HashMap<Field, String> getFieldValueMap() {
		return fieldValueMap;
	}

	/**
	 * Method used to return an {@link ArrayList} that contains the log of the article.
	 *
	 * @return {@link ArrayList} that contains the log of the article.
	 */
	public ArrayList<History> getHistory() {
		return history;
	}

	/**
	 * method used to set a new article {@link State}
	 *
	 * @param state new article {@link State} to set
	 */
	public void setArticleState(State state) {
		history.add(new History("State Update", STATE_UPDATED.formatted(this.articleState, state)));
		this.articleState = state;
	}

	private HashMap<Field, String> createValidateFieldValueMap(ArrayList<Field> fields, ArrayList<String> values) {
		var map = new HashMap<Field, String>();
		for (int i = 0; i < fields.size(); i++) {
			map.put(fields.get(i), values.get(i));
		}


		var isNotValideMap = map.entrySet().stream()
				.anyMatch(entry -> entry.getKey().required() && entry.getValue().isBlank());


		if (isNotValideMap) {
			history.add(new History(NOT_VALID_FIELDS_ERROR, FIELDS_HAVE_NOT_BEEN_INITIALIZED_ERROR_DESCRIPTION, true));
			setArticleState(State.CANCELLED_OFFER);
		}
		else {
			history.add(new History(ARTICLE_VALIDATED, ARTICLE_IS_VALID_AND_PROPERLY_INITIALIZED_DESCRIPTION));
			setArticleState(State.OPEN_OFFER);
		}

		return map;
	}
}
