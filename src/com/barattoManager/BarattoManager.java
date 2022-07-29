package com.barattoManager;

import com.barattoManager.ui.mvc.mainFrame.MainFrameController;
import com.barattoManager.ui.mvc.mainFrame.MainFrameModel;
import com.barattoManager.ui.mvc.mainFrame.MainFrameView;
import com.barattoManager.ui.mvc.mainFrame.RegisterControllerHandlerFactory;

import javax.swing.*;
import java.awt.*;

public class BarattoManager {

	public static void main(String[] args) {

		var mainFrameController = new MainFrameController(new MainFrameModel(), new MainFrameView());
		RegisterControllerHandlerFactory.getHandler().addListener(mainFrameController);


		new Thread(
				() -> EventQueue.invokeLater(
						() -> {
							JFrame jFrame = new JFrame("Baratto Manager");
							jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							jFrame.setSize(new Dimension(600, 500));
							jFrame.setResizable(false);

							var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							jFrame.setLocation((screenSize.width - jFrame.getWidth()) / 2, (screenSize.height - jFrame.getHeight()) / 2);



							jFrame.add(mainFrameController.getView().getJPanel());

							jFrame.setVisible(true);
						}
				)
		).start();
	}

}
