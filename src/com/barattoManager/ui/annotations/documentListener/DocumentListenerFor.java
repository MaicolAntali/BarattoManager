package com.barattoManager.ui.annotations.documentListener;

import java.lang.annotation.*;

/**
 * Annotation used to link a method to a field by reflection invoking {@code addActionListener()}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentListenerFor {

	/**
	 * Name of field where the action listener runtime will be added
	 *
	 * @return {@link String} name of field
	 */
	String sourceField();

}
