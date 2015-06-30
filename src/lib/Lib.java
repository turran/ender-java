package org.ender;

import org.ender.api.LibAPI;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;

public class Lib implements NativeMapped {
	static final LibAPI api = (LibAPI) Native.loadLibrary("ender", LibAPI.class);

	private Pointer handle;

	public Lib()
	{

	}

	protected Lib(Pointer ptr)
	{
		handle = ptr;
	}

	public int getVersion()
	{
		return api.ender_lib_version_get(this);
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
