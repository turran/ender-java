package org.ender.common;

import org.ender.ItemTransfer;
import org.eina.List;

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

	public static <T> java.util.List<T> einaListToList(List from, Class<T> contentClass,
			ItemTransfer transfer)
	{
		List next = from;
		java.util.List<T> ret = new java.util.ArrayList<T>();

		while (next != null)
		{
			if (next.data == null)
				continue;
			// Check the type
			if (ReferenceableObject.class.isAssignableFrom(contentClass))
			{
				Class<ReferenceableObject> roClass = (Class<ReferenceableObject>)contentClass;
				ReferenceableObject obj = ReferenceableObject.construct(roClass, next.data, true);
				if (obj != null)
					ret.add((T)obj);
			}
			else
			{
				throw new RuntimeException("Unsupported class " + contentClass);
			}
			next = next.next();
		}
		return ret;
	}
}
