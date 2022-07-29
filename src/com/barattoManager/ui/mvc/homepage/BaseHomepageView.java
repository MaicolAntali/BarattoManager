package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.mvc.base.BaseView;

public interface BaseHomepageView extends BaseView {

	void addLoginButtonListeners(HomepageLoginButtonListener listener);
	void addRegisterButtonListeners(HomepageRegisterButtonListener listener);

}
