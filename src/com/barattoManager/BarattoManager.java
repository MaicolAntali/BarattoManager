package com.barattoManager;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.mvc.homepage.HomepageController;
import com.barattoManager.ui.mvc.homepage.HomepageView;
import com.barattoManager.ui.mvc.mainFrame.MainFrameController;
import com.barattoManager.ui.mvc.mainFrame.MainFrameModel;
import com.barattoManager.ui.mvc.mainFrame.MainFrameView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;

import javax.swing.*;
import java.awt.*;

public class BarattoManager {

	public static void main(String[] args) {

		new Thread(new UserManagerFactory()).start();
		new Thread(new CategoryManagerFactory()).start();
		new Thread(new MeetManagerFactory()).start();
		new Thread(new ArticleManagerFactory()).start();

		var mainFrameController = new MainFrameController(new MainFrameModel(), new MainFrameView());
		var homepageController  = new HomepageController(new HomepageView());

		RegisterControllerHandlerFactory.getHandler().addListener(mainFrameController);
		ShowControllerHandlerFactory.getHandler().addListener(mainFrameController);
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(homepageController, ControllerNames.HOMEPAGE.toString());

		new Thread(
				() -> EventQueue.invokeLater(
						() -> {
							JFrame jFrame = new JFrame("Baratto Manager");
							jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							jFrame.setSize(new Dimension(600, 500));
							jFrame.setResizable(false);

							var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							jFrame.setLocation((screenSize.width - jFrame.getWidth()) / 2, (screenSize.height - jFrame.getHeight()) / 2);

							ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE.toString());

							jFrame.add(mainFrameController.getView().getMainJPanel());

							jFrame.setVisible(true);
						}
				)
		).start();
	}

}
