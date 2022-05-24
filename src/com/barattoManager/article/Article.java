package com.barattoManager.article;

import com.barattoManager.category.field.Field;
import com.barattoManager.category.field.FieldDeserializer;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.utils.History;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@JsonPropertyOrder({ "uuid", "owner", "category_uuid", "state", "fields", "log" })
public class Article {

	@JsonProperty("uuid")
	private final String uuid;
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

	private enum State {
		NOT_CHECKED,
		OPEN_OFFERT,
		CANCELLED_OFFERT,
	}

	public Article(String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) throws IllegalValuesException {
		this.history = new ArrayList<>();
		history.add(new History("Article Created", "Article has just been created by the viewer %s".formatted(userNameOwner)));

		this.uuid = UUID.randomUUID().toString();
		this.userNameOwner = userNameOwner;
		this.categoryUuid = categoryUuid;
		this.articleState = State.NOT_CHECKED;
		this.fieldValueMap = createValidateFieldValueMap(fields, values);
	}


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
	}

	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to create a hashmap and validate it (All required fields must be init).
	 * @param fields {@link ArrayList} of fields
	 * @param values {@link ArrayList} of values
	 * @return {@link HashMap} KEY: fields VALUE: values
	 * @throws IllegalValuesException is thrown if a required field is not init
	 */
	private HashMap<Field, String> createValidateFieldValueMap(ArrayList<Field> fields, ArrayList<String> values) throws IllegalValuesException {
		var map = new HashMap<Field, String>();
		for (int i = 0; i < fields.size(); i++) {
			map.put(fields.get(i), values.get(i));
		}


		var isNotValideMap = map.entrySet().stream()
				.anyMatch(entry -> entry.getKey().required() && entry.getValue().isBlank());


		if (isNotValideMap) {
			history.add(new History("Not Valid fields", "One or more required fields have not been properly initialized", true));
			changeState(State.CANCELLED_OFFERT);
			throw new IllegalValuesException("Uno o più campi obbligatori non sono stati correttamente inizializzati.");
		}

		history.add(new History("Article Validated", "Article is valid and properly initialized"));
		changeState(State.OPEN_OFFERT);

		return map;
	}

	/**
	 * Method used to update the state of {@link #articleState} & log the chhanges
	 * @param state new {@link State}
	 */
	private void changeState(State state) {
		history.add(new History("State Update", "The article state is updated from %s to %s".formatted(this.articleState, state)));

		this.articleState = state;
	}
}
