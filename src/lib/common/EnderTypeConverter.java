package org.ender.common;

import org.ender.common.annotations.Transfer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.sun.jna.Pointer;
import com.sun.jna.TypeConverter;
import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.MethodParameterContext;

public class EnderTypeConverter extends DefaultTypeMapper {

	private static TypeConverter referenceableObjectConverter = new TypeConverter() {
		public Object fromNative(Object arg, FromNativeContext ctx) {
			if (arg == null)
				return null;
			Class cls = ctx.getTargetType();
			try {
				Pointer handle = (Pointer)arg;
				Constructor ctor = cls.getDeclaredConstructor(Pointer.class, boolean.class);
				// call the constructor and own the ref
				Object ret = ctor.newInstance(handle, false);
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

		public Class<?> nativeType() {
			return Pointer.class;
		}

		public Object toNative(Object arg, ToNativeContext ctx) {
			if (arg == null)
				return null;

			Pointer ret = ((ReferenceableObject)arg).getHandle();

			if (ctx instanceof MethodParameterContext)
			{
				MethodParameterContext mcontext = (MethodParameterContext) ctx;
				Method method = mcontext.getMethod();
				int index = mcontext.getParameterIndex();

				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				if (index < parameterAnnotations.length) {
					Annotation[] annotations = parameterAnnotations[index];
					for (int i = 0; i < annotations.length; ++i) {
					}
				}
			}
			return ret;
		}
	};

	@Override
	public FromNativeConverter getFromNativeConverter(Class type)
	{
		if (ReferenceableObject.class.isAssignableFrom(type))
			return referenceableObjectConverter;
		return super.getFromNativeConverter(type);
	}

	@Override
	public ToNativeConverter getToNativeConverter(Class type)
	{
		if (ReferenceableObject.class.isAssignableFrom(type))
			return referenceableObjectConverter;
		return super.getToNativeConverter(type);
	}
}
