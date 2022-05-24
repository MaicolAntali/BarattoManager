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
			myWriter.write("""
					{
					  "libri": {
					    "name": "Libri",
					    "description": "Serie continua di fogli stampati della stessa misura, cuciti insieme e forniti di copertina o rilegatura",
					    "sub_categories": {
						    "romanzo giallo": {
							    "name": "Romanzo Giallo",
							    "description": "...",
							    "sub_categories": {},
							    "fields": {
							      "descrizione": {
							        "name": "Descrizione",
							        "required": true
							      },
							      "stato di conservazione": {
							        "name": "Stato di conservazione",
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
					      "stato di conservazione": {
					        "name": "Stato di conservazione",
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
		assertThrows(AlreadyExistException.class, () -> instance.addNewSubCategory(path, "Romanzo Giallo", "..."));
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
}