package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.annotations.Transfer;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class ItemDef extends Item {

	public interface API extends Library {
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemDef(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}
}
