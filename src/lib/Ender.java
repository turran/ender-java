package org.ender;

import org.ender.api.EnderAPI;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class Ender {
	static final EnderAPI api = (EnderAPI) Native.loadLibrary("ender", EnderAPI.class);

	public static void init()
	{
		api.ender_init();
	}

	public static void shutdown()
	{
		api.ender_shutdown();
	}
}
