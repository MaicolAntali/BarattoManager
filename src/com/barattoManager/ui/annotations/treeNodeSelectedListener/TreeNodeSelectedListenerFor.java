package com.barattoManager.ui.annotations.treeNodeSelectedListener;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeNodeSelectedListenerFor {
	String sourceField();

}
