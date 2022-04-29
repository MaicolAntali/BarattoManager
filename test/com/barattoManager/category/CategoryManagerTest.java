package com.barattoManager.category;

import com.barattoManager.exception.CategoryAlreadyExist;
import com.barattoManager.exception.FieldAlreadyExist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryManagerTest {
	private CategoryManager instance;

	@BeforeAll
	static void makeBackup() {

		// Create a backup file of the current category conf
		var file = new File("categories.json");
		if (file.exists()) {
			file.renameTo(new File("categories_BACKUP.json"));
		}

		// Write a default category conf so the test can run
		try {
			FileWriter myWriter = new FileWriter("categories.json");
			myWriter.write("{  \"libri\": {    \"category_name\": \"Libri\",    \"category_description\": \"Serie continua di fogli stampati della stessa misura, cuciti insieme e forniti di copertina o rilegatura\",    \"category_is_root\": true,    \"category_sub_categories\": {      \"romanzo\": {        \"category_name\": \"Romanzo\",        \"category_description\": \"Alle origini delle moderne letterature europee, ampio scritto in lingua volgare\",        \"category_is_root\": false,        \"category_sub_categories\": {          \"romanzo giallo\": {            \"category_name\": \"Romanzo Giallo\",            \"category_description\": \"Il genere giallo è la descrizione di un crimine e dei personaggi coinvolti, siano essi criminali o vittime\",            \"category_is_root\": false,            \"category_sub_categories\": {},            \"category_fields\": {              \"editore\": {                \"field_name\": \"Editore\",                \"field_required\": true              },              \"stato\": {                \"field_name\": \"Stato\",                \"field_required\": true              },              \"descrizione\": {                \"field_name\": \"Descrizione\",                \"field_required\": true              },              \"data di pubblicazione\": {                \"field_name\": \"Data di Pubblicazione\",                \"field_required\": false              },              \"pagine\": {                \"field_name\": \"Pagine\",                \"field_required\": true              },              \"autore\": {                \"field_name\": \"Autore\",                \"field_required\": false              }            }          }        },        \"category_fields\": {          \"editore\": {            \"field_name\": \"Editore\",            \"field_required\": true          },          \"stato\": {            \"field_name\": \"Stato\",            \"field_required\": true          },          \"descrizione\": {            \"field_name\": \"Descrizione\",            \"field_required\": true          },          \"data di pubblicazione\": {            \"field_name\": \"Data di Pubblicazione\",            \"field_required\": false          },          \"pagine\": {            \"field_name\": \"Pagine\",            \"field_required\": true          },          \"autore\": {            \"field_name\": \"Autore\",            \"field_required\": false          }        }      }    },    \"category_fields\": {      \"editore\": {        \"field_name\": \"Editore\",        \"field_required\": true      },      \"stato\": {        \"field_name\": \"Stato\",        \"field_required\": true      },      \"descrizione\": {        \"field_name\": \"Descrizione\",        \"field_required\": true      },      \"data di pubblicazione\": {        \"field_name\": \"Data di Pubblicazione\",        \"field_required\": false      },      \"pagine\": {        \"field_name\": \"Pagine\",        \"field_required\": true      },      \"autore\": {        \"field_name\": \"Autore\",        \"field_required\": false      }    }  }}");
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void setUp() {
		instance = CategoryManager.getInstance();
	}

	@AfterAll
	static void restoreBackup() {
		// Delete the test category conf file
		// Create a backup file of the current category conf
		var file = new File("categories.json");
		file.delete();

		// Restore the backup file
		var backupFile = new File("categories_BACKUP.json");
		backupFile.renameTo(file);
	}

	@Test
	void mapIsNotEmpty() {
		assertFalse(instance.getCategoryMap().isEmpty());
	}

	@Test
	void addCategoryAlreadyExist() {
		var path = new ArrayList<>(Arrays.asList("Categorie", "Libri", "Romanzo"));
		assertThrows(CategoryAlreadyExist.class, () -> instance.addNewSubCategory(path, "Romanzo Giallo", "..."));
	}

	@Test
	void addFieldAlreadyExist() {
		var path = new ArrayList<>(Arrays.asList("Categorie", "Libri", "Romanzo"));
		assertThrows(FieldAlreadyExist.class, () -> instance.addNewField(path, "Editore", true));
	}
}