package com.barattoManager.category;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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
			myWriter.write("""
					{
					  "db442916-6ccd-453d-a4a9-32141a132983": {
					    "uuid": "db442916-6ccd-453d-a4a9-32141a132983",
					    "name": "Libri",
					    "description": ".",
					    "sub_categories": {
					      "cb066d20-1acd-4434-9cad-31cdbacae926": {
					        "uuid": "cb066d20-1acd-4434-9cad-31cdbacae926",
					        "name": "Romanzi",
					        "description": ".",
					        "sub_categories": {
					          "719e26a0-9fe5-44b5-af28-0b29c9aa5880": {
					            "uuid": "719e26a0-9fe5-44b5-af28-0b29c9aa5880",
					            "name": "Gialli",
					            "description": ".",
					            "sub_categories": {},
					            "fields": {
					              "descrizione": {
					                "name": "Descrizione",
					                "required": true
					              },
					              "titolo": {
					                "name": "Titolo",
					                "required": true
					              },
					              "stato di conservazione": {
					                "name": "Stato di conservazione",
					                "required": true
					              },
					              "anno pubblicazione": {
					                "name": "Anno Pubblicazione",
					                "required": false
					              },
					              "pagine": {
					                "name": "Pagine",
					                "required": true
					              },
					              "autore": {
					                "name": "Autore",
					                "required": true
					              }
					            }
					          }
					        },
					        "fields": {
					          "descrizione": {
					            "name": "Descrizione",
					            "required": true
					          },
					          "titolo": {
					            "name": "Titolo",
					            "required": true
					          },
					          "stato di conservazione": {
					            "name": "Stato di conservazione",
					            "required": true
					          },
					          "anno pubblicazione": {
					            "name": "Anno Pubblicazione",
					            "required": false
					          },
					          "pagine": {
					            "name": "Pagine",
					            "required": true
					          },
					          "autore": {
					            "name": "Autore",
					            "required": true
					          }
					        }
					      }
					    },
					    "fields": {
					      "descrizione": {
					        "name": "Descrizione",
					        "required": true
					      },
					      "titolo": {
					        "name": "Titolo",
					        "required": true
					      },
					      "stato di conservazione": {
					        "name": "Stato di conservazione",
					        "required": true
					      },
					      "anno pubblicazione": {
					        "name": "Anno Pubblicazione",
					        "required": false
					      },
					      "pagine": {
					        "name": "Pagine",
					        "required": true
					      },
					      "autore": {
					        "name": "Autore",
					        "required": true
					      }
					    }
					  }
					}
					""");
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
		assertFalse(instance.getRootCategoryMap().isEmpty());
	}

	@Test
	void addCategoryAlreadyExist() {
		var path = new ArrayList<>(Arrays.asList("Categorie", "Libri"));
		assertThrows(AlreadyExistException.class, () -> instance.addNewSubCategory(path, "Romanzi", "..."));
	}

	@Test
	void addFieldAlreadyExist() {
		var path = new ArrayList<>(Arrays.asList("Categorie", "Libri"));
		assertThrows(AlreadyExistException.class, () -> instance.addNewField(path, "descrizione", true));
	}

	@Test
	void emptyMainCategoryName() {
		assertThrows(IllegalValuesException.class, () -> instance.addNewMainCategory("          ", "..."));
	}

	@Test
	void emptySubcategoryName() {
		assertThrows(IllegalValuesException.class, () -> instance.addNewSubCategory(null, "",  ""));
	}

	@Test
	void emptyFieldName() {
		assertThrows(IllegalValuesException.class, () -> instance.addNewField(null, "", true));
	}


	@Test
	void getCategoryByUuidNull() {
		assertTrue(instance.getCategoryByUuid("null").isEmpty());
	}
}