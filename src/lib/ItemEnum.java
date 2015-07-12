package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemEnum extends Item {
	private interface API extends Library {
		//EAPI Eina_List * ender_item_enum_values_get(Ender_Item *i);
		//EAPI Eina_List * ender_item_enum_functions_get(Ender_Item *i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemEnum(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}
}

