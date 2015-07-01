package org.ender.common;

import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.DefaultTypeMapper;

public class EnderTypeConverter extends DefaultTypeMapper {

	@Override
	public FromNativeConverter getFromNativeConverter(Class type)
	{
		if (Referenceable.class.isAssignableFrom(type))
		{
			
		}
		return super.getFromNativeConverter(type);
	}

	@Override
	public ToNativeConverter getToNativeConverter(Class type)
	{
		if (Referenceable.class.isAssignableFrom(type))
		{
			
		}
		return super.getToNativeConverter(type);
	}
}
