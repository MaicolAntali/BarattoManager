package com.barattoManager.ui.annotations.treeNodeSelectedListener;

import javax.swing.event.TreeSelectionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TreeNodeSelectedInstaller {

	public static void processAnnotations(Object sourceMethodsObj, Object sourceFieldsObj) {
		try {
			for (Method method : sourceMethodsObj.getClass().getSuperclass().getDeclaredMethods()) {

				var annotation = method.getAnnotation(TreeNodeSelectedListenerFor.class);

				if (annotation != null) {
					Field field = sourceFieldsObj.getClass().getSuperclass().getDeclaredField(annotation.sourceField());
					field.setAccessible(true);

					if (field.getAnnotation(TreeNodeSelectedField.class) == null)
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
				new Class[]{TreeSelectionListener.class},
				handler
		);

		Method adder = source.getClass().getMethod("addTreeSelectionListener", TreeSelectionListener.class);
		adder.invoke(source, listener);

	}
}
