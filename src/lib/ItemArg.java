package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.annotations.Transfer;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemArg extends Item {
	private interface API extends Library {
		@Transfer(ItemTransfer.FULL)
		Item ender_item_arg_type_get(Item i);
		ItemArgDirection ender_item_arg_direction_get(Item i);
		ItemTransfer ender_item_arg_transfer_get(Item i);
		int ender_item_arg_flags_get(Item i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemArg(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public Item getType()
	{
		return api.ender_item_arg_type_get(this);
	}

	public ItemArgDirection getDirection()
	{
		return api.ender_item_arg_direction_get(this);
	}

	public ItemTransfer getTransfer()
	{
		return api.ender_item_arg_transfer_get(this);
	}

	public int getFlags()
	{
		return api.ender_item_arg_flags_get(this);
	}
}
