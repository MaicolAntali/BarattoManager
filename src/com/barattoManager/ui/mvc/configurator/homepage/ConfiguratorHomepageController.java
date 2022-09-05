package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorController;
import com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorView;
import com.barattoManager.ui.mvc.configurator.meetEditor.MeetEditorController;
import com.barattoManager.ui.mvc.configurator.meetEditor.MeetEditorView;
import com.barattoManager.ui.mvc.configurator.offerView.ViewOfferController;
import com.barattoManager.ui.mvc.configurator.offerView.ViewOfferView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.register.RegisterController;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;
import com.barattoManager.ui.utils.ControllerNames;

public class ConfiguratorHomepageController implements Controller {

	private final ConfiguratorHomepageView view;

	private CategoryEditorController categoryEditorController;

	public ConfiguratorHomepageController(ConfiguratorHomepageView view) {
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "configCategoryButton")
	private void clickOnConfigCategoryButton() {
		if (categoryEditorController == null) {
			categoryEditorController = new CategoryEditorController(new CategoryEditorView());
			RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
					categoryEditorController, ControllerNames.CATEGORY_EDITOR.toString()
			);
		}
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.CATEGORY_EDITOR.toString());
	}

	@ActionListenerFor(sourceField = "configMeetButton")
	private void clickOnConfigMeetButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new MeetEditorController(new MeetEditorView()), ControllerNames.MEET_EDITOR.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.MEET_EDITOR.toString());
	}

	@ActionListenerFor(sourceField = "addNewConfigurator")
	private void clickOnAddNewConfigurator() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new RegisterController(
						new RegisterModel(true),
						new RegisterView()
				), ControllerNames.REGISTER_CONFIGURATOR.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.REGISTER_CONFIGURATOR.toString());
	}

	@ActionListenerFor(sourceField = "showOffer")
	private void clickOnShowOffer() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewOfferController(new ViewOfferView()), ControllerNames.OFFER_VIEW_CONFIGURATOR.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.OFFER_VIEW_CONFIGURATOR.toString());
	}

	@ActionListenerFor(sourceField = "loadJsonButton")
	private void clickOnLoadJsonButton() {
		new JsonLoader().loadJson(view);
	}

	@ActionListenerFor(sourceField = "loadJsonQuestionButton")
	private void clickOnLoadJsonQuestionButton() {
		System.out.println("Informazioni");
	}
}
