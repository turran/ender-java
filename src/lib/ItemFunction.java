package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.annotations.Transfer;

import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JDefinedClass;

public class ItemFunction extends Item {
	private interface API extends Library {
		@Transfer(ItemTransfer.FULL)
		org.eina.List ender_item_function_args_get(Item i);
		@Transfer(ItemTransfer.FULL)
		ItemArg ender_item_function_args_at(Item i, int idx);
		int ender_item_function_args_count(Item i);
		@Transfer(ItemTransfer.FULL)
		ItemArg ender_item_function_ret_get(Item i);
		//Eina_Bool ender_item_function_call(Ender_Item *i,
		//		Ender_Value *args, Ender_Value *retval);
		int ender_item_function_flags_get(ItemFunction i);
		//EAPI int ender_item_function_throw_position_get(Ender_Item *i);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemFunction(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public int getFlags()
	{
		return api.ender_item_function_flags_get(this);
	}

	public int countArgs()
	{
		return api.ender_item_function_args_count(this);
	}

	public ItemArg getArg(int idx)
	{
		return api.ender_item_function_args_at(this, idx);
	}

	public List<ItemArg> getArgs()
	{
		org.eina.List list = api.ender_item_function_args_get(this);
		return EnderNative.einaListToList(list, ItemArg.class, ItemTransfer.FULL);
	}

	public ItemArg getRet()
	{
		return api.ender_item_function_ret_get(this);
	}

	@Override
	public JType managedType(Generator gen)
	{
		return gen.cm.VOID;
	}

	@Override
	public JType unmanagedType(Generator gen,
			ItemArgDirection direction, ItemTransfer transfer)
	{
		return gen.cm.VOID;
	}

	public JMethod generatePinvoke(Generator gen, JDefinedClass cls)
	{
		JCodeModel cm = gen.cm;

		ItemArg retItem = getRet();
		JType ret = cm.VOID;
		String name;

		Item parent = getParent();

		if (parent == null)
		{
			name = getLib().getName() + "_" + getName();
		}
		else
		{
			name = parent.getName() + "_" + getName();
			name = name.replace(".", "_");
		}

		if (retItem != null)
		{
			ret = retItem.unmanagedType(gen, retItem.getDirection(), retItem.getTransfer());
		}
		JMethod method = cls.method(JMod.PUBLIC, ret, name);
		if (retItem != null && retItem.getTransfer() == ItemTransfer.FULL)
		{
			JAnnotationUse ann = method.annotate(Transfer.class);
			ann.param("value", ItemTransfer.FULL);
		}

		List<ItemArg> args = getArgs();
		for (int i = 0; i < args.size(); i++)
		{
			ItemArg arg = args.get(i);
			JVar param = method.param(arg.unmanagedType(gen, arg.getDirection(), arg.getTransfer()), arg.getName());
		}

		return method;

	}

	public void generate(Generator gen, JDefinedClass cls)
	{
		JCodeModel cm = gen.cm;
		JMethod method = null;
		int mods = JMod.PUBLIC;
		int startParam = 0;

		// The return type
		ItemArg retItem = getRet();
		JType ret = cm.VOID;
		if (retItem != null)
		{
			ret = retItem.managedType(gen);
		}

		// The constructors
		if ((getFlags() & ItemFunctionFlag.CTOR.getValue()) == ItemFunctionFlag.CTOR.getValue())
		{
			method = cls.constructor(mods);
		}
		else if ((getFlags() & ItemFunctionFlag.DOWNCAST.getValue()) == ItemFunctionFlag.DOWNCAST.getValue())
		{

		}
		// Generic method/function
		else
		{
			if ((getFlags() & ItemFunctionFlag.IS_METHOD.getValue()) != ItemFunctionFlag.IS_METHOD.getValue())
				mods |= JMod.STATIC;
			else
				startParam = 1;

			method = cls.method(mods, ret, getName());
		}

		if (method == null)
			return;

		// The params
		List<ItemArg> args = getArgs();
		for (int i = startParam; i < args.size(); i++)
		{
			ItemArg arg = args.get(i);
			JVar param = method.param(arg.managedType(gen), arg.getName());
		}
	}
}
