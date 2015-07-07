package org.ender.common;

import com.sun.jna.Pointer;

public interface ReferenceableObject {
	public Pointer getHandle();
	public void ref();
	public void unref();
}
