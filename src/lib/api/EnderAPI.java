package org.ender.api;

import org.ender.Lib;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface EnderAPI extends Library {
	void ender_init();
	void ender_shutdown();
}
