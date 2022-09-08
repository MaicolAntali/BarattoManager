package com.barattoManager.ui.annotations.actionListener;

import java.lang.annotation.*;

/**
 * Annotation used to mark a field<br>
 * The field will be linked to the method by reflection invoking {@code addActionListener()}
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionListenerField {
}
