package org.ender.common;

import org.ender.common.annotations.Transfer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.sun.jna.Pointer;
import com.sun.jna.TypeConverter;
import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.MethodParameterContext;

public class EnderTypeConverter extends DefaultTypeMapper {

	private static TypeConverter referenceableConverter = new TypeConverter() {
		public Object fromNative(Object arg, FromNativeContext ctx) {
			if (arg == null)
				return null;
			// call the constructor and own the ref
			Class cls = ctx.getTargetType();
			System.out.println("class is " + cls);
			return null;
		}

		public Class<?> nativeType() {
			return Pointer.class;
		}

		public Object toNative(Object arg, ToNativeContext ctx) {
			System.out.println("to native " + arg);
			if (arg == null)
				return null;

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
			return null;
		}
	};

	@Override
	public FromNativeConverter getFromNativeConverter(Class type)
	{
		if (Referenceable.class.isAssignableFrom(type))
			return referenceableConverter;
		return super.getFromNativeConverter(type);
	}

	@Override
	public ToNativeConverter getToNativeConverter(Class type)
	{
		if (Referenceable.class.isAssignableFrom(type))
			return referenceableConverter;
		return super.getToNativeConverter(type);
	}
}
