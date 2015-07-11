package org.ender.common;

import com.sun.jna.Pointer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

public abstract class ReferenceableObject {
	public abstract Pointer getHandle();
	public abstract void ref();
	public abstract void unref();
	public static final <T extends ReferenceableObject> T construct(
			Class<T> cls, Pointer handle, boolean doRef)
	{
		try {
			T ret = null;
			if (Modifier.isAbstract(cls.getModifiers()))
			{
				Method method = cls.getDeclaredMethod("downcast",
						Pointer.class, boolean.class); 
				ret = (T)method.invoke(null, handle, doRef);
			}
			else
			{
				Constructor ctor = cls.getDeclaredConstructor(
						Pointer.class, boolean.class);
				ret = (T)ctor.newInstance(handle, doRef);
			}
			return ret;
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		} catch (InstantiationException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}
}
