package com.barattoManager.ui.components;

import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.CategoryAlreadyExist;
import com.barattoManager.exception.FieldAlreadyExist;
import com.barattoManager.exception.NoNodeSelected;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class CategoryViewEditor extends JPanel {

	private static final Dimension DIMENSION = new Dimension(500, 290);
	public static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	public static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	public static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";


	private final JTree tree;
	private final DefaultTreeModel treeModel;
	private final CategoryManager categoryManager;

	public CategoryViewEditor() {
		categoryManager = CategoryManager.getInstance();
		var root = new DefaultMutableTreeNode("Categorie");
		tree = createTree(root);
		tree.setRootVisible(true);
		treeModel = (DefaultTreeModel) tree.getModel();

		// Add the JTree in the JPanel
		add(new JScrollPane(tree)).setPreferredSize(DIMENSION);

		// Add button
		makeButtons();
	}

	/**
	 * Method used to create a JTree based oh the category
	 *
	 * @return {@link JTree} object
	 */
	private JTree createTree(DefaultMutableTreeNode root) {
		for (Category cat : categoryManager.getCategoryMap().values()) {
			var node = createCategoryNode(cat, root);
			getSubCategory(cat, node);
		}

		// Create teh tree based on rootNode
		var treeTmp = new JTree(root);

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeTmp.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));

		return treeTmp;
	}

	/**
	 * Method used to create the button bar
	 */
	private void makeButtons() {
		var panel = new JPanel();

		// Buttons
		var newCategoryButton = new JButton("Nuova Categoria Radice");
		var newSubcategoryButton = new JButton("Nuova Sotto-Categoria");
		var newFieldButton = new JButton("Aggiungi nuovo campo");

		// Button Listener
		newSubcategoryButton.addActionListener(e -> {
			TreeNode[] nodePath;
			try {
				// Get the selected node
				nodePath = getSelectedPathNode();

				var categoryInputPanel = new NewCategoryPanel();
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
							treeNodeArrayToArrayList(nodePath),
							categoryInputPanel.getCategoryName().getText(),
							categoryInputPanel.getCategoryDescription().getText());

					paintNewCategory(newSubCategory);
				}
			} catch (NoNodeSelected | CategoryAlreadyExist ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});

		newFieldButton.addActionListener(e -> {
			TreeNode[] nodePath;
			try {
				// Get the selected node
				nodePath = getSelectedPathNode();

				var fieldInputPanel = new NewFieldInput();
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
							treeNodeArrayToArrayList(nodePath),
							fieldName,
							fieldInputPanel.getFieldIsRequired().isSelected()
					);

					paintNewField(categoryOfNewField, fieldName);

				}
			} catch (NoNodeSelected | FieldAlreadyExist ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Add buttons to panel
		panel.add(newCategoryButton);
		panel.add(newSubcategoryButton);
		panel.add(newFieldButton);

		// Add panel to the main
		add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Method used to paint in the {@link #tree} a new category (Category + Fields).
	 * @param category {@link Category} to paint in the {@link #tree}
	 */
	private void paintNewCategory(Category category) {
		// Print the new category
		var newCategoryNode = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		insertNewNodeAndUpdate(newCategoryNode, selectedNode);

		// Print the Fields
		for(Field field: category.getCategoryFields().values()) {
			var newFieldNode = new DefaultMutableTreeNode("%s: %s".formatted(field.getName(), field.isRequired()));
			insertNewNodeAndUpdate(newFieldNode, newCategoryNode);
		}
	}

	/**
	 * Method used to paint in the {@link #tree} a new field.
	 * @param category {@link Category} to start adding the new field
	 * @param newFieldName Field Name
	 */
	private void paintNewField(Category category, String newFieldName) {

		var field = category.getCategoryFields().get(newFieldName.toLowerCase());

		var newFieldNode = new DefaultMutableTreeNode(("%s: %s").formatted(field.getName(), field.isRequired()));
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		insertNewNodeAndUpdate(newFieldNode, selectedNode);

		for (int i = 0; i < selectedNode.getChildCount(); i++) {
			var node = selectedNode.getChildAt(i);
			if (!node.isLeaf()) {
				insertNewNodeAndUpdate(newFieldNode, (DefaultMutableTreeNode) node);
			}
		}
	}

	/**
	 * Method used to insert a new nope on the fly and update the view of tree. 
	 * @param nodeToAdd Node to add in the {@link #tree}
	 * @param fatherNode Node that's the father of new node.
	 */
	private void insertNewNodeAndUpdate(DefaultMutableTreeNode nodeToAdd, DefaultMutableTreeNode fatherNode) {
		treeModel.insertNodeInto(nodeToAdd, fatherNode, fatherNode.getChildCount());
		treeModel.reload(nodeToAdd);
		TreeNode[] CategoryNodes = treeModel.getPathToRoot(nodeToAdd);
		var CategoryPath = new TreePath(CategoryNodes);
		tree.scrollPathToVisible(CategoryPath);
	}

	/**
	 * Method to convert and array of {@link TreeNode} in to an array of {@link String}
	 * @param nodePath Array of {@link TreeNode} to convert
	 * @return Array of {@link String}
	 */
	private ArrayList<String> treeNodeArrayToArrayList(TreeNode[] nodePath) {
		var arrayList = new ArrayList<String>();
		for (TreeNode node: nodePath) {
			// Split the category name and description and take only the category name
			arrayList.add(node.toString().split("~")[0].trim());
		}
		return arrayList;
	}

	/**
	 * Method used to get the current selected node in the {@link #tree}.
	 * @return Array of {@link TreeNode} that contains the node path.
	 * @throws NoNodeSelected is thrown if the user does not select any node.
	 */
	private TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedNode == null) {
			throw new NoNodeSelected("Non è stato selezionata nessuna categoria. Seleziona una categoria e riprova.");
		}
		else {
			return selectedNode.getPath();
		}
	}

	/**
	 * Recursive method used to append the sub category
	 *
	 * @param cat    Category that want append in the tree
	 * @param catAdd Node of the tree where teh category will be appended.
	 */
	private void getSubCategory(Category cat, DefaultMutableTreeNode catAdd) {
		if (!cat.getSubCategory().isEmpty()) {
			for (Category subCat : cat.getSubCategory().values()) {
				DefaultMutableTreeNode node = createCategoryNode(subCat, catAdd);

				getSubCategory(subCat, node);
			}
		}
	}

	/**
	 * Method used to create a complete (category + field) node.
	 * @param category {@link Category} want to create the node.
	 * @param fatherNode {@link DefaultMutableTreeNode} node to attach the new category node
	 * @return {@link DefaultMutableTreeNode} New category node
	 */
	private DefaultMutableTreeNode createCategoryNode(Category category, DefaultMutableTreeNode fatherNode) {
		// Add the category
		var node = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		fatherNode.add(node);

		// Add fields
		for (Field categoryField: category.getCategoryFields().values()) {
			node.add(new DefaultMutableTreeNode(("%s: %s").formatted(categoryField.getName(), categoryField.isRequired())));
		}
		return node;
	}
}
