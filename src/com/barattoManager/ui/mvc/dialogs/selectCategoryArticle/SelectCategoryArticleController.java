package com.barattoManager.ui.mvc.dialogs.selectCategoryArticle;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public class SelectCategoryArticleController implements BaseController {

    private final SelectCategoryArticleView view;

    public SelectCategoryArticleController(SelectCategoryArticleView view) {
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
