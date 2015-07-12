package org.ender;

import org.ender.common.EnderNative;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;

public class Lib implements NativeMapped {

	private interface API extends Library {
		Lib ender_lib_find(String name);
		int ender_lib_version_get(Lib l);
		String ender_lib_name_get(Lib l);
		org.eina.List ender_lib_dependencies_get(Lib l);
		Item ender_lib_item_find(Lib l, String name);
		org.eina.List ender_lib_item_list(Lib l, ItemType type);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	private Pointer handle;

	/* TODO Needed by NativeMapped, must go away */
	public Lib()
	{

	}

	protected Lib(Pointer ptr)
	{
		handle = ptr;
	}

	public List<Lib> getDependencies()
	{
		org.eina.List l = api.ender_lib_dependencies_get(this);
		org.eina.List next = l;
		List<Lib> ret = new ArrayList<Lib>();

		/* TODO free the list */
		while (next != null)
		{
			if (next.data != null)
			{
				Lib lib = new Lib(next.data);
				ret.add(lib);
			}
			next = next.next();
		}
		return ret;
	}

	public int getVersion()
	{
		return api.ender_lib_version_get(this);
	}

	public String getName()
	{
		return api.ender_lib_name_get(this);
	}

	public List<Item> listItem(ItemType type)
	{
		org.eina.List l = api.ender_lib_item_list(this, type);
		org.eina.List next = l;
		List<Item> ret = new ArrayList<Item>();

		/* TODO free the list */
		while (next != null)
		{
			if (next.data != null)
			{
				Item i = Item.downcast(next.data, true);
				if (i != null)
					ret.add(i);
			}
			next = next.next();
		}
		return ret;
	}

	public Item findItem(String name)
	{
		return api.ender_lib_item_find(this, name);
	}

	public static Lib find(String name)
	{
		return api.ender_lib_find(name);
	}

	@Override
        public Class<?> nativeType() {
            return Pointer.class;
        }

	@Override
	public Object toNative()
	{
		return (Object)handle;
	}

	@Override
	public Object fromNative(Object result, FromNativeContext context)
	{
		return new Lib((Pointer)result);
	}
}
