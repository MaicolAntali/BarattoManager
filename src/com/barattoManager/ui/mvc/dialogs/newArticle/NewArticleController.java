package com.barattoManager.ui.mvc.dialogs.newArticle;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public class NewArticleController implements BaseController {

    private final NewArticleView view;

    public NewArticleController(NewArticleView view) {
        this.view = view;
    }

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
