package org.ender;

import org.ender.common.EnderTypeConverter;
import org.ender.common.ReferenceableObject;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class Item implements ReferenceableObject {

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

	private static final Map<String, Object> options = new HashMap<String, Object>() {{
		put(Library.OPTION_TYPE_MAPPER, new EnderTypeConverter());
	}};

	static final API api = (API) Native.loadLibrary("ender", API.class, options);

	private Pointer raw;

	public Item(Pointer raw, boolean doRef)
	{
		this.raw = raw;
		if (doRef)
			ref();
	}

	public String getName()
	{
		return api.ender_item_name_get(this);
	}

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
