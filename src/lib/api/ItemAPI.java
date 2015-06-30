package org.ender.api;

import org.ender.Item;
import org.ender.ItemType;
import org.ender.Lib;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ItemAPI extends Library {
	Item ender_item_ref(Item i);
	void ender_item_unref(Item i);
	String ender_item_name_get(Item i);
	String ender_item_full_name_get(Item i);
	ItemType ender_item_type_get(Item i);
	Item ender_item_parent_get(Item i);
	String ender_item_type_name_get(ItemType type);
	Lib ender_item_lib_get(Item i);
	boolean ender_item_is_exception(Item i);
}
