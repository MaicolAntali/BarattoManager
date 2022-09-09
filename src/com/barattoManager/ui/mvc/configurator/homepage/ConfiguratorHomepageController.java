package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.ui.action.actions.JsonLoaderAction;
import com.barattoManager.ui.action.actions.RegisterShowControllerAction;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorController;
import com.barattoManager.ui.mvc.configurator.categoryEditor.SimpleCategoryEditorView;
import com.barattoManager.ui.mvc.configurator.meetEditor.MeetEditorController;
import com.barattoManager.ui.mvc.configurator.meetEditor.SimpleMeetEditorView;
import com.barattoManager.ui.mvc.configurator.offerView.ViewOfferController;
import com.barattoManager.ui.mvc.configurator.offerView.ViewOfferView;
import com.barattoManager.ui.mvc.register.RegisterController;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

/**
 * Controller that handle the events of the {@link ConfiguratorHomepageView}
 */
public class ConfiguratorHomepageController extends GraspController {
	private static final String HELP_MESSAGE = """
			Formato del JSON:
			[
				{
					"nome_categoria": "nome_categoria",
			        "descrizione": "descrizione_categoria",
			        "percorso": null
			    },
			    {
					"nome_categoria": "nome_categoria",
					"descrizione": "descrizione_categoria",
					"percorso": "main|sub|sub"
				},
				{
					"nome_campo": "nome_campo",
					"obbligatorio": true,
					"percorso": "main|sub|sub"
					}
			]
			NB: - in una categoria radice il percorso deve essere null
			    - in una sotto categoria e in un campo"
			           - il percorso non deve essere null
			           - il percorso deve essere descritto con il carattere: |
			""";

	private final ConfiguratorHomepageView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ConfiguratorHomepageView} represent the view of the controller
	 */
	public ConfiguratorHomepageController(ConfiguratorHomepageView view) {
		this.view = view;
		this.view.addActionNotifierListener(this);

		initAction();
	}

	@Override
	protected void initAction() {
		addAction("Configura_Categorie", new RegisterShowControllerAction(
						ControllerNames.CATEGORY_EDITOR,
						new CategoryEditorController(new SimpleCategoryEditorView())
				)
		);
		addAction("Configura_Incontri", new RegisterShowControllerAction(
						ControllerNames.MEET_EDITOR,
						new MeetEditorController(new SimpleMeetEditorView())
				)
		);
		addAction("Registra_Configuratore", new RegisterShowControllerAction(
						ControllerNames.REGISTER_CONFIGURATOR,
						new RegisterController(new RegisterModel(true), new RegisterView())
				)
		);
		addAction("Visualizza_Offerte", new RegisterShowControllerAction(
						ControllerNames.OFFER_VIEW_CONFIGURATOR,
						new ViewOfferController(new ViewOfferView())
				)
		);
		addAction("Caricare_configurazione_JSON", new JsonLoaderAction(view.getMainJPanel()));
		addAction("info_json_load", () -> new MessageDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessageType(JOptionPane.INFORMATION_MESSAGE)
				.setTitle("HELP")
				.setMessage(HELP_MESSAGE)
				.show()
		);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
		return view;
	}
}
