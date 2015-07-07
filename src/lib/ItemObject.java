package org.ender;

import org.ender.common.EnderTypeConverter;
import org.ender.common.annotations.Transfer;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemObject extends Item {
	private interface API extends Library {
		//Eina_Bool ender_item_object_string_to(Ender_Item *i, void *o, char **str,
		//  	Ender_Item_Transfer *xfer, Eina_Error *err);
		//Ender_Item * ender_item_object_downcast(Ender_Item *i, void *o);
		@Transfer("Full")
		ItemObject ender_item_object_inherit_get(ItemObject self);
		//Eina_List * ender_item_object_functions_get(Ender_Item *i);
		//Eina_List * ender_item_object_ctor_get(Ender_Item *i);
		//Eina_List * ender_item_object_props_get(Ender_Item *i);
		//Eina_Bool ender_item_object_ref(Ender_Item *i, void *o);
		//Eina_Bool ender_item_object_unref(Ender_Item *i, void *o);
	}

	private static final Map<String, Object> options = new HashMap<String, Object>() {{
		put(Library.OPTION_TYPE_MAPPER, new EnderTypeConverter());
	}};

	static final API api = (API) Native.loadLibrary("ender", API.class, options);

	public ItemObject(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public ItemObject getInherit()
	{
		return api.ender_item_object_inherit_get(this);
	}
}

