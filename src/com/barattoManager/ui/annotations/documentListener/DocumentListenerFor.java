package com.barattoManager.ui.annotations.documentListener;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentListenerFor {
	String sourceField();

}
