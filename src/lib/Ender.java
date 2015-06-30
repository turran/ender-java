package org.ender;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Ender {

	private interface API extends Library {
		void ender_init();
		void ender_shutdown();
	}

	static final API api = (API) Native.loadLibrary("ender", API.class);

	public static void init()
	{
		api.ender_init();
	}

	public static void shutdown()
	{
		api.ender_shutdown();
	}
}
