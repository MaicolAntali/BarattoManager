package com.barattoManager.ui.annotations.actionListener;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Class used to add action listener by searching all the methods with {@link ActionListenerFor} annotation
 */
public class ActionListenerInstaller {

	/**
	 * Method that process all the {@link ActionListenerFor} in the in the sourceMethodsObj class
	 *
	 * @param sourceMethodsObj {@link Object} of which the methods may have {@link ActionListenerFor} annotations
	 * @param sourceFieldsObj  {@link Object} of which the methods may have {@link ActionListenerField} annotations
	 */
	public static void processAnnotations(Object sourceMethodsObj, Object sourceFieldsObj) {
		try {
			for (Method method : sourceMethodsObj.getClass().getDeclaredMethods()) {

				ActionListenerFor annotation = method.getAnnotation(ActionListenerFor.class);

				if (annotation != null) {
					Field field = sourceFieldsObj.getClass().getDeclaredField(annotation.sourceField());
					field.setAccessible(true);

					if (field.getAnnotation(ActionListenerField.class) == null)
						throw new IllegalArgumentException("Field is not annotated with ActionListenerLinked.");

					method.setAccessible(true);
					addListener(field.get(sourceFieldsObj), sourceMethodsObj, method);
				}
			}
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}


	private static void addListener(Object source, final Object obj, final Method m) throws ReflectiveOperationException {
		var handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return m.invoke(obj);
			}
		};

		Object listener = Proxy.newProxyInstance(
				null,
				new Class[]{ActionListener.class},
				handler
		);

		Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
		adder.invoke(source, listener);

	}
}
