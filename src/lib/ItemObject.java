package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.EnderTypeConverter;
import org.ender.common.annotations.Transfer;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemObject extends Item {
	private interface API extends Library {
		//Eina_Bool ender_item_object_string_to(Ender_Item *i, void *o, char **str,
		//  	Ender_Item_Transfer *xfer, Eina_Error *err);
		//Ender_Item * ender_item_object_downcast(Ender_Item *i, void *o);
		@Transfer(ItemTransfer.FULL)
		ItemObject ender_item_object_inherit_get(ItemObject self);
		//Eina_List * ender_item_object_functions_get(Ender_Item *i);
		//Eina_List * ender_item_object_ctor_get(Ender_Item *i);
		//Eina_List * ender_item_object_props_get(Ender_Item *i);
		//Eina_Bool ender_item_object_ref(Ender_Item *i, void *o);
		//Eina_Bool ender_item_object_unref(Ender_Item *i, void *o);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemObject(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public ItemObject getInherit()
	{
		return api.ender_item_object_inherit_get(this);
	}
}

