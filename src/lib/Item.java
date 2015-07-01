package org.ender;

import org.ender.common.Referenceable;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class Item implements Referenceable {

	public interface API extends Library {
		Item ender_item_ref(Item i);
		void ender_item_unref(Item i);
		String ender_item_name_get(Item i);
		String ender_item_full_name_get(Item i);
		ItemType ender_item_type_get(Item i);
		Item ender_item_parent_get(Item i);
		Lib ender_item_lib_get(Item i);
		boolean ender_item_is_exception(Item i);
	}

	static final API api = (API) Native.loadLibrary("ender", API.class);

	private Pointer raw;

	public Item(Pointer raw, boolean doRef)
	{
		this.raw = raw;
		if (doRef)
			ref();
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
}
