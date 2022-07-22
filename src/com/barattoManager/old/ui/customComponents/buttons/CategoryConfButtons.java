package com.barattoManager.old.ui.customComponents.buttons;

import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.exception.NoNodeSelected;
import com.barattoManager.old.exception.NullCategoryException;
import com.barattoManager.old.manager.factory.CategoryManagerFactory;
import com.barattoManager.old.ui.customComponents.optionPane.CreateNewCategoryPanel;
import com.barattoManager.old.ui.customComponents.optionPane.CreateNewFieldPanel;
import com.barattoManager.old.ui.customComponents.tree.category.CategoryTree;
import com.barattoManager.old.utils.TreeUtils;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to create a {@link JPanel} that contains buttons for the configurator to create new categories/fields.
 */
public class CategoryConfButtons extends JPanel {

	private static final String NEW_MAIN_CATEGORY_BUTTON_NAME = "Nuova Categoria Radice";
	private static final String NEW_SUBCATEGORY_BUTTON_NAME = "Nuova Sotto-Categoria";
	private static final String ADD_NEW_FIELD_BUTTON_NAME = "Aggiungi nuovo campo";
	private static final String TITLE_CREATION_OF_MAIN_CATEGORY = "Creazione di una categoria radice";
	private static final String TITLE_CREATION_OF_SUBCATEGORY = "Creazione di una sotto-categoria";
	private static final String TITLE_CREATION_OF_NEW_FIELD = "Creazione di un nuovo campo";
	private static final String TITLE_OF_ERROR = "Errore";

	private final CategoryTree categoryTree;


	/**
	 * Constructor of the class
	 *
	 * @param categoryTree {@link CategoryTree} object
	 */
	public CategoryConfButtons(CategoryTree categoryTree) {
		this.categoryTree = categoryTree;

		JButton addNewMainCategoryButton = new JButton(NEW_MAIN_CATEGORY_BUTTON_NAME);
		JButton addNewSubCategoryButton  = new JButton(NEW_SUBCATEGORY_BUTTON_NAME);
		JButton addNewFieldButton        = new JButton(ADD_NEW_FIELD_BUTTON_NAME);

		addNewMainCategoryButton.addActionListener(e -> addNewMainCategory());
		addNewSubCategoryButton.addActionListener(e -> addNewSubCategory());
		addNewFieldButton.addActionListener(e -> addNewField());

		add(addNewMainCategoryButton);
		add(addNewSubCategoryButton);
		add(addNewFieldButton);
	}

	/**
	 * Method used to add a new root category
	 */
	private void addNewMainCategory() {
		try {
			var categoryInputPanel = new CreateNewCategoryPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					categoryInputPanel,
					TITLE_CREATION_OF_MAIN_CATEGORY,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				CategoryManagerFactory.getManager().addNewMainCategory(
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText()
				);
			}
		} catch (AlreadyExistException | IllegalValuesException | NullCategoryException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), TITLE_OF_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method used to add a new sub-category
	 */
	private void addNewSubCategory() {
		try {
			// Get the selected node
			TreeNode[] nodePath = categoryTree.getSelectedPathNode();

			var categoryInputPanel = new CreateNewCategoryPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					categoryInputPanel,
					TITLE_CREATION_OF_SUBCATEGORY,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				assert nodePath != null;
				CategoryManagerFactory.getManager().addNewSubCategory(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText()
				);
			}
		} catch (NoNodeSelected | AlreadyExistException | IllegalValuesException | NullCategoryException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), TITLE_OF_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method used to add a new field
	 */
	private void addNewField() {
		try {
			// Get the selected node
			TreeNode[] nodePath = categoryTree.getSelectedPathNode();

			var fieldInputPanel = new CreateNewFieldPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					fieldInputPanel,
					TITLE_CREATION_OF_NEW_FIELD,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				assert nodePath != null;
				String fieldName = fieldInputPanel.getFieldName().getText();
				CategoryManagerFactory.getManager().addNewField(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						fieldName,
						fieldInputPanel.getFieldIsRequired().isSelected()
				);
			}
		} catch (NoNodeSelected | AlreadyExistException | IllegalValuesException | NullCategoryException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), TITLE_OF_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

}