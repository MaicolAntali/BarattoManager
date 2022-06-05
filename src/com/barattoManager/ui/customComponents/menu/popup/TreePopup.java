package com.barattoManager.ui.customComponents.menu.popup;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.meet.Meet;
import com.barattoManager.meet.MeetManager;
import com.barattoManager.trade.TradeManager;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

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

            var selectArticleToTrade = new SelectArticleToTrade(possibleArticles);
            int result = JOptionPane.showOptionDialog(
                    this,
                    selectArticleToTrade,
                    "Scambia Articolo",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            if (result == JOptionPane.OK_OPTION) {
               var selectMeetDate = new SelectMeetDate();
               int resultMeetDate = JOptionPane.showOptionDialog(
                       this,
                       selectMeetDate,
                       "Scambia Articolo",
                       JOptionPane.OK_CANCEL_OPTION,
                       JOptionPane.QUESTION_MESSAGE,
                       null,
                       null,
                       null
               );

               if (resultMeetDate == JOptionPane.OK_OPTION) {
                  TradeManager.getInstance().addNewTrade(
                          LocalDateTime.now().plusDays(Integer.parseInt(selectMeetDate.getSelectedMeetDaysBeforeExpire())),
                          selectArticleToTrade.getSelectedArticle().getUuid(),
                          articleToTrade.getUuid()
                  );
                  System.out.println();
               }
            }

         }
         else {
            throw new IllegalValuesException("Non è stato selezionato un articolo.");
         }


      } catch (NoNodeSelected | IllegalValuesException  ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
      }
   }
}

class SelectArticleToTrade extends JPanel {

   private final JComboBox<String> articleComboBox = new JComboBox<>();
   public SelectArticleToTrade(List<Article> articles) {
      var mainPanel = new JPanel();
      mainPanel.setLayout(new GridLayout(0, 1));


      mainPanel.add(new JLabel("Seleziona l'oggetto che vuoi scambiare:"));

      for (Article item : articles) {
         articleComboBox.addItem(item.getUuid());
      }

      mainPanel.add(articleComboBox);

      add(mainPanel);
      setVisible(true);
   }

   public Article getSelectedArticle() {
      return ArticleManager.getInstance()
              .getArticleById(String.valueOf(articleComboBox.getSelectedItem()))
              .orElseThrow(NullPointerException::new);
   }
}

class SelectMeetDate extends JPanel {

   private final JComboBox<String> meetComboBox = new JComboBox<>();
   public SelectMeetDate() {
      var mainPanel = new JPanel();
      mainPanel.setLayout(new GridLayout(0, 1));


      mainPanel.add(new JLabel("Seleziona il giorno dello scambio:"));

      for (Meet meet : MeetManager.getInstance().getMeetArrayList()) {
         meet.getDays().forEach(days -> meetComboBox.addItem("%s ~ %s ~ %s ~ [%s-%s] ~ %s".formatted(meet.getCity(), meet.getSquare(), days, meet.getIntervals().get(1), meet.getIntervals().get(meet.getIntervals().size()-1), meet.getDaysBeforeExpire())));
      }

      mainPanel.add(meetComboBox);

      add(mainPanel);
      setVisible(true);
   }

   public String getSelectedMeetDaysBeforeExpire() {
      return String.valueOf(meetComboBox.getSelectedItem()).split("~")[4].trim();
   }
}