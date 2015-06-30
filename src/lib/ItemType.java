package org.ender;

import org.ender.api.ItemAPI;
import com.sun.jna.Native;

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

	static final ItemAPI api = (ItemAPI) Native.loadLibrary("ender", ItemAPI.class);

	public String getName()
	{
		return api.ender_item_type_name_get(this);
	}
}
