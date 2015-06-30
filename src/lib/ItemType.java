package org.ender;

import com.sun.jna.Native;
import com.sun.jna.Library;

public enum ItemType {
	INVALID,
	BASIC,
	FUNCTION,
	ATTR,
	ARG,
	OBJECT,
	STRUCT,
	CONSTANT,
	ENUM,
	DEF;

	public interface API extends Library {
		String ender_item_type_name_get(ItemType type);
	}

	static final API api = (API) Native.loadLibrary("ender", API.class);

	public String getName()
	{
		return api.ender_item_type_name_get(this);
	}
}
