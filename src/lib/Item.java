package org.ender;

import org.ender.common.EnderTypeConverter;
import org.ender.common.ReferenceableObject;
import org.ender.common.annotations.Transfer;

import org.ender.common.EnderNative;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public abstract class Item extends ReferenceableObject {

	public interface API extends Library {
		Item ender_item_ref(Item i);
		void ender_item_unref(Item i);
		String ender_item_name_get(Item i);
		@Transfer(ItemTransfer.FULL)
		String ender_item_full_name_get(Item i);
		// We change the first parameter to handle the downcast
		ItemType ender_item_type_get(Pointer raw);
		@Transfer(ItemTransfer.FULL)
		Item ender_item_parent_get(Item i);
		Lib ender_item_lib_get(Item i);
		boolean ender_item_is_exception(Item i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	private Pointer raw;

	public Item(Pointer raw, boolean doRef)
	{
		this.raw = raw;
		if (doRef)
			ref();
	}

	// Needed for constructing the correct object
	static public Item downcast(Pointer raw, boolean doRef)
	{
		ItemType type = api.ender_item_type_get(raw);
		return downcast(raw, doRef, type);
	}

	static public Item downcast(Pointer raw, boolean doRef, ItemType type)
	{
		Item i = null;
		switch (type)
		{
			case BASIC:
			i = new ItemBasic(raw, doRef);
			break;

			case OBJECT:
			i = new ItemObject(raw, doRef);
			break;

			case FUNCTION:
			i = new ItemFunction(raw, doRef);
			break;

			default:
			throw new IllegalArgumentException("No such type " + type);
		}
		return i;
	}

	public String getName()
	{
		return api.ender_item_name_get(this);
	}

	public ItemType getItemType()
	{
		return api.ender_item_type_get(raw);
	}

	public Item getParent()
	{
		return api.ender_item_parent_get(this);
	}

	public Lib getLib()
	{
		return api.ender_item_lib_get(this);
	}

	@Override
	protected void finalize() throws Throwable
	{
		try {
			System.out.println("finalizing");
		} finally {
			super.finalize();
		}
	}

	@Override
	public void ref()
	{
		api.ender_item_ref(this);
	}

	@Override
	public void unref()
	{
		api.ender_item_unref(this);
	}

	@Override
	public Pointer getHandle()
	{
		return this.raw;
	}
}
