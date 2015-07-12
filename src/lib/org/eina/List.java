package org.eina;

import org.ender.common.EnderNative;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;

public class List extends Structure {
	private interface API extends Library {
		void eina_list_free(Pointer handle);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public volatile Pointer data;
	public volatile Pointer _next;
	public volatile Pointer _prev;

	public List() {
		clear();
	}

	public List(Pointer ptr) {
		useMemory(ptr);
		read();
	}

	public static List valueOf(Pointer ptr) {
		return ptr != null ? new List(ptr) : null;
	}

	public List next() {
		return valueOf(_next);
	}
	public List prev() {
		return valueOf(_prev);
	}

	@Override
	protected void finalize() throws Throwable
	{
		System.out.println("finalizing list");
	}

	@Override
	protected java.util.List<String> getFieldOrder() {
		return Arrays.asList(new String[]{
			"data", "_next", "_prev"
		});
	}
}
