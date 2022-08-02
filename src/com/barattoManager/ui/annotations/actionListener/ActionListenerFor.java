package com.barattoManager.ui.annotations.actionListener;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionListenerFor {

	String sourceField();

}
