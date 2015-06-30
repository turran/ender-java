package org.ender;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemBasic extends Item {
	private interface API extends Library {
		ValueType ender_item_basic_value_type_get(ItemBasic self);
	}

	static final API api = (API) Native.loadLibrary("ender", API.class);

	public ItemBasic(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}
}
