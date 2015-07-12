package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemAttr extends Item {
	private interface API extends Library {
		//EAPI Ender_Item * ender_item_attr_type_get(Ender_Item *i);
		//EAPI ssize_t ender_item_attr_offset_get(Ender_Item *i);
		//EAPI Eina_Bool ender_item_attr_value_get(Ender_Item *i, void *o, Ender_Item_Transfer *xfer,
		//		Ender_Value *v, Eina_Error *err);
		//EAPI Eina_Bool ender_item_attr_value_set(Ender_Item *i, void *o, Ender_Value *v, Eina_Error *err);
		//EAPI int ender_item_attr_flags_get(Ender_Item *i);

		//EAPI Ender_Item * ender_item_attr_getter_get(Ender_Item *i);
		//EAPI Ender_Item * ender_item_attr_setter_get(Ender_Item *i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemAttr(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}
}

