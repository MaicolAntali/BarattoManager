package com.barattoManager.ui.panels.categoryTree;

import com.barattoManager.category.CategoryManager;
import com.barattoManager.exception.CategoryAlreadyExist;
import com.barattoManager.exception.FieldAlreadyExist;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.ui.panels.optionPane.CreateNewCategoryPanel;
import com.barattoManager.ui.panels.optionPane.CreateNewFieldPanel;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class TreeActionButtons extends JPanel {
	private final CategoryManager categoryManager = CategoryManager.getInstance();
	private final TreeView treeView;

	public TreeActionButtons(TreeView treeView) {
		this.treeView = treeView;

		JButton addNewMainCategoryButton = new JButton("Nuova Categoria Radice");
		JButton addNewSubCategoryButton = new JButton("Nuova Sotto-Categoria");
		JButton addNewFieldButton = new JButton("Aggiungi nuovo campo");

		// Add listeners
		addNewMainCategoryButton.addActionListener(e -> addNewMainCategory());
		addNewSubCategoryButton.addActionListener(e -> addNewSubCategory());
		addNewFieldButton.addActionListener(e -> addNewField());

		// Add buttons to panel
		add(addNewMainCategoryButton);
		add(addNewSubCategoryButton);
		add(addNewFieldButton);
	}

	private void addNewMainCategory() {
		try {
			var categoryInputPanel = new CreateNewCategoryPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					categoryInputPanel,
					"Creazione di una categoria radice",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				var newCategory = categoryManager.addNewMainCategory(
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText());

				treeView.paintNewMainCategory(newCategory);
			}
		} catch (CategoryAlreadyExist | FieldAlreadyExist ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addNewSubCategory() {
		try {
			// Get the selected node
			TreeNode[] nodePath = treeView.getSelectedPathNode();

			var categoryInputPanel = new CreateNewCategoryPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					categoryInputPanel,
					"Creazione di una sotto-categoria",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				assert nodePath != null;
				var newSubCategory = categoryManager.addNewSubCategory(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						categoryInputPanel.getCategoryName().getText(),
						categoryInputPanel.getCategoryDescription().getText());

				treeView.paintNewSubCategory(newSubCategory);
			}
		} catch (NoNodeSelected | CategoryAlreadyExist ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addNewField() {
		try {
			// Get the selected node
			TreeNode[] nodePath = treeView.getSelectedPathNode();

			var fieldInputPanel = new CreateNewFieldPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					fieldInputPanel,
					"Creazione di un nuovo campo",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				assert nodePath != null;
				String fieldName = fieldInputPanel.getFieldName().getText();
				var categoryOfNewField =categoryManager.addNewField(
						TreeUtils.treeNodeArrayToArrayList(nodePath),
						fieldName,
						fieldInputPanel.getFieldIsRequired().isSelected()
				);

				treeView.paintNewField(categoryOfNewField, fieldName);

			}
		} catch (NoNodeSelected | FieldAlreadyExist ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

}