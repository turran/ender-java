package org.ender.common;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.Library;

public final class EnderNative {

	private EnderNative() {}

	private static final Map<String, Object> options = new HashMap<String, Object>() {{
		put(Library.OPTION_TYPE_MAPPER, new EnderTypeConverter());
	}};

	public static <T extends Library> T loadLibrary(String name, Class<T> interfaceClass)
	{
		T ret = interfaceClass.cast(Native.loadLibrary(name, interfaceClass, options));
		return ret;
	}
}
