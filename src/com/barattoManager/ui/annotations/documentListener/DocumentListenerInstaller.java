package com.barattoManager.ui.annotations.documentListener;

import javax.swing.event.DocumentListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Class used to add action listener by searching all the methods with {@link DocumentListenerFor} annotation
 */
public class DocumentListenerInstaller {

	/**
	 * Method that process all the {@link DocumentListenerFor} in the in the sourceMethodsObj class
	 *
	 * @param sourceMethodsObj {@link Object} of which the methods may have {@link DocumentListenerFor} annotations
	 * @param sourceFieldsObj  {@link Object} of which the methods may have {@link DocumentListenerField} annotations
	 */
	public static void processAnnotations(Object sourceMethodsObj, Object sourceFieldsObj) {
		try {
			for (Method method : sourceMethodsObj.getClass().getDeclaredMethods()) {

				DocumentListenerFor annotation = method.getAnnotation(DocumentListenerFor.class);

				if (annotation != null) {
					Field field = sourceFieldsObj.getClass().getDeclaredField(annotation.sourceField());
					field.setAccessible(true);

					if (field.getAnnotation(DocumentListenerField.class) == null)
						throw new IllegalArgumentException("Field is not annotated with ActionListenerLinked.");

					method.setAccessible(true);
					addListener(field.get(sourceFieldsObj), sourceMethodsObj, method);
				}
			}
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	static void addListener(Object source, final Object obj, final Method m) throws ReflectiveOperationException {
		var handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return m.invoke(obj);
			}
		};

		Object listener = Proxy.newProxyInstance(
				null,
				new Class[]{DocumentListener.class},
				handler
		);

		var getDocument = source.getClass().getMethod("getDocument").invoke(source);

		Method adder = getDocument.getClass().getMethod("addDocumentListener", DocumentListener.class);
		adder.invoke(getDocument, listener);

	}
}
