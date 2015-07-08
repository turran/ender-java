package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemFunction extends Item {
	private interface API extends Library {
		//EAPI Eina_List * ender_item_function_args_get(Ender_Item *i);
		//EAPI Ender_Item * ender_item_function_args_at(Ender_Item *i, int idx);
		//EAPI int ender_item_function_args_count(Ender_Item *i);
		//EAPI Ender_Item * ender_item_function_ret_get(Ender_Item *i);
		//Eina_Bool ender_item_function_call(Ender_Item *i,
		//		Ender_Value *args, Ender_Value *retval);
		//EAPI int ender_item_function_flags_get(Ender_Item *i);
		//EAPI int ender_item_function_throw_position_get(Ender_Item *i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemFunction(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}
}
