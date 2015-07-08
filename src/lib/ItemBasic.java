package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemBasic extends Item {
	private interface API extends Library {
		ValueType ender_item_basic_value_type_get(ItemBasic self);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemBasic(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public ValueType getValueType()
	{
		return api.ender_item_basic_value_type_get(this);
	}
}
