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
	 * @return Name of field where the action listener runtime will be added <br>
	 * The field must be marked with the {@link DocumentListenerField} annotation
	 */
	String sourceField();

}
