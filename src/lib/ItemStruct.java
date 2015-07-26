package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.annotations.Transfer;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class ItemStruct extends Item {

	public interface API extends Library {
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemStruct(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	@Override
	public JType managedType(Generator gen)
	{
		return gen.cm.ref(gen.prefix + "." + getQualifiedClassName());
	}

	@Override
	public JType unmanagedType(Generator gen,
			ItemArgDirection direction, ItemTransfer transfer)
	{
		return managedType(gen);
	}
}

