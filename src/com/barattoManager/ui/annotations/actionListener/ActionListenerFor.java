package com.barattoManager.ui.annotations.actionListener;

import java.lang.annotation.*;

/**
 * Annotation used to link a method to a field by reflection invoking {@code addActionListener()}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionListenerFor {

	/**
	 * @return Name of field where the action listener runtime will be added <br>
	 * The field must be marked with the {@link ActionListenerField} annotation
	 */
	String sourceField();

}
