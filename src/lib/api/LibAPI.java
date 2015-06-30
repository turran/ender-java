package org.ender.api;

import org.ender.Lib;
import org.ender.Item;
import org.ender.ItemType;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LibAPI extends Library {
	Lib ender_lib_find(String name);
	int ender_lib_version_get(Lib l);
	String ender_lib_name_get(Lib l);
	EinaAPI.List ender_lib_dependencies_get(Lib l);
	Item ender_lib_item_find(Lib l, String name);
	EinaAPI.List ender_lib_item_list(Lib l, ItemType type);
}
