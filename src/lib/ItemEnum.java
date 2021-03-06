package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.ClassType;

public class ItemEnum extends Item {
	private interface API extends Library {
		//EAPI Eina_List * ender_item_enum_values_get(Ender_Item *i);
		//EAPI Eina_List * ender_item_enum_functions_get(Ender_Item *i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemEnum(Pointer raw, boolean doRef)
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

	public void generate(Generator gen) throws JClassAlreadyExistsException
	{
		JCodeModel cm = gen.cm;
		int mods = JMod.NONE;

		JDefinedClass cls = cm._class(mods, managedType(gen).fullName(), ClassType.ENUM);
	}
}

