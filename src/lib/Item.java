package org.ender;

import org.ender.api.ItemAPI;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Item {
	static final ItemAPI api = (ItemAPI) Native.loadLibrary("ender", ItemAPI.class);

	private Pointer raw;

	public Item(Pointer raw, boolean doRef)
	{
		this.raw = raw;
		if (doRef)
			ref();
	}

	private void ref()
	{
		api.ender_item_ref(this);
	}

	private void unref()
	{
		api.ender_item_unref(this);
	}
}
