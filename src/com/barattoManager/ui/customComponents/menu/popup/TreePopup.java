package com.barattoManager.ui.customComponents.menu.popup;

import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.ui.customComponents.optionPane.CreateNewFieldPanel;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.util.Arrays;

public class TreePopup extends JPopupMenu {

   private final ArticleTree tree;

   public TreePopup(ArticleTree tree) {
      this.tree = tree;

      JMenuItem trade = new JMenuItem("Scambia Articolo");
      trade.addActionListener(e -> tradeArticle());
      add(trade);
   }

   private void tradeArticle() {
      try {
         // Get the selected node
         TreeNode[] nodePath = tree.getSelectedPathNode();

         System.out.println(Arrays.toString(nodePath));

      } catch (NoNodeSelected  ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
      }
   }
}