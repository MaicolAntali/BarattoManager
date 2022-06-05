package com.barattoManager.ui.customComponents.menu.popup;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;
import javax.swing.tree.TreeNode;

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
         TreeNode[] nodePath = tree.getSelectedPathNode();

         var articleOptional = ArticleManager.getInstance().getArticleById(nodePath[nodePath.length -1].toString());

         if (articleOptional.isPresent()) {
            var articleToTrade = articleOptional.get();

            var possibleArticles = ArticleManager.getInstance()
                    .getArticlesByOwnerStateCategory(tree.getUsername(), Article.State.OPEN_OFFERT, articleToTrade.getCategoryUuid());

            if (possibleArticles.isEmpty()) {
               throw new IllegalValuesException("Non possiedi nessun articolo di questa categoria.");
            }

            System.out.println(possibleArticles);
         }
         else {
            throw new IllegalValuesException("Non è stato selezionato un articolo.");
         }


      } catch (NoNodeSelected | IllegalValuesException  ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
      }
   }
}