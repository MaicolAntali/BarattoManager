package com.barattoManager.ui.customComponents.buttons;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.manager.CategoryManager;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.optionPane.CreateNewCategoryPanel;
import com.barattoManager.ui.customComponents.optionPane.CreateNewFieldPanel;
import com.barattoManager.ui.customComponents.tree.category.CategoryTree;
import com.barattoManager.utils.TreeUtils;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Class used to create a JPanel that contains buttons for the Category tree
 */
public class CategoryConfButtons extends JPanel {
	/**
	 * Name of "New main category" button
	 */
	private static final String NEW_MAIN_CATEGORY_BUTTON_NAME = "Nuova Categoria Radice";
	/**
	 * Name of "New sub-category" button
	 */
	private static final String NEW_SUBCATEGORY_BUTTON_NAME = "Nuova Sotto-Categoria";
	/**
	 * Name of "add new field" button
	 */
	private static final String ADD_NEW_FIELD_BUTTON_NAME = "Aggiungi nuovo campo";
	/**
	 * Creation of main category title
	 */
	private static final String TITLE_CREATION_OF_MAIN_CATEGORY = "Creazione di una categoria radice";
	/**
	 * Creation of sub-category title
	 */
	private static final String TITLE_CREATION_OF_SUBCATEGORY = "Creazione di una sotto-categoria";
	/**
	 * Creation of a new field title
	 */
	private static final String TITLE_CREATION_OF_NEW_FIELD = "Creazione di un nuovo campo";
	/**
	 * Title of error
	 */
	private static final String TITLE_OF_ERROR = "Errore";
	/**
	 * {@link CategoryManager} object
	 */
	private final CategoryManager categoryManager = CategoryManager.getInstance();
	/**
	 * {@link CategoryTree} object
	 */
	private final CategoryTree categoryTree;
	/**
	 * event handler used to fire the repaint event
	 */
	private final RepaintEventHandler repaintEventHandler;

	/**
	 * {@link CategoryConfButtons} constructor
	 *
	 * @param categoryTree        {@link CategoryTree} object
	 * @param repaintEventHandler {@link RepaintEventHandler} used to repaint the view
	 */
	public CategoryConfButtons(CategoryTree categoryTree, RepaintEventHandler repaintEventHandler) {
		this.categoryTree = categoryTree;
		this.repaintEventHandler = repaintEventHandler;

		JButton addNewMainCategoryButton = new JButton(NEW_MAIN_CATEGORY_BUTTON_NAME);
		JButton addNewSubCategoryButton = new JButton(NEW_SUBCATEGORY_BUTTON_NAME);
		JButton addNewFieldButton = new JButton(ADD_NEW_FIELD_BUTTON_NAME);

		// Add listeners
		addNewMainCategoryButton.addActionListener(e -> addNewMainCategory());
		addNewSubCategoryButton.addActionListener(e -> addNewSubCategory());
		addNewFieldButton.addActionListener(e -> addNewField());

		// Add buttons to panel
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
				categoryManager.addNewMainCategory(
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText()
				);
				repaintEventHandler.fireListeners();
			}
		} catch (AlreadyExistException | IllegalValuesException | NullCategoryException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), TITLE_OF_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method used to add a new sub category
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
				categoryManager.addNewSubCategory(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText()
				);
				repaintEventHandler.fireListeners();
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
				categoryManager.addNewField(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						fieldName,
						fieldInputPanel.getFieldIsRequired().isSelected()
				);
				repaintEventHandler.fireListeners();
			}
		} catch (NoNodeSelected | AlreadyExistException | IllegalValuesException | NullCategoryException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), TITLE_OF_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

}