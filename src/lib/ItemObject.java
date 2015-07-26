package org.ender;

import org.ender.common.EnderNative;
import org.ender.common.annotations.Transfer;

import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JType;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.ClassType;
import com.sun.codemodel.JDefinedClass;

public class ItemObject extends Item {
	private interface API extends Library {
		//Eina_Bool ender_item_object_string_to(Ender_Item *i, void *o, char **str,
		//  	Ender_Item_Transfer *xfer, Eina_Error *err);
		//Ender_Item * ender_item_object_downcast(Ender_Item *i, void *o);
		@Transfer(ItemTransfer.FULL)
		ItemObject ender_item_object_inherit_get(ItemObject self);
		@Transfer(ItemTransfer.FULL)
		org.eina.List ender_item_object_functions_get(ItemObject self);
		//Eina_List * ender_item_object_ctor_get(Ender_Item *i);
		//Eina_List * ender_item_object_props_get(Ender_Item *i);
		//Eina_Bool ender_item_object_ref(Ender_Item *i, void *o);
		//Eina_Bool ender_item_object_unref(Ender_Item *i, void *o);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemObject(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public ItemObject getInherit()
	{
		return api.ender_item_object_inherit_get(this);
	}

	public List<ItemFunction> getFunctions()
	{
		org.eina.List list = api.ender_item_object_functions_get(this);
		return EnderNative.einaListToList(list, ItemFunction.class, ItemTransfer.FULL);
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
		if (direction == ItemArgDirection.OUT)
			return gen.cm.ref("com.sun.jna.ptr.PointerByReference");
		else
			return managedType(gen);
	}

	public void generate(Generator gen) throws JClassAlreadyExistsException
	{
		JCodeModel cm = gen.cm;
		List<ItemFunction> functions = getFunctions();
		ItemFunction ctor = null;
		ItemFunction ref = null;
		ItemFunction unref = null;

		// Check some information before creating the class
		for (int j = 0; j < functions.size(); j++)
		{
			ItemFunction f = functions.get(j);
			int flags = f.getFlags();

			if ((flags & ItemFunctionFlag.CTOR.getValue()) == ItemFunctionFlag.CTOR.getValue())
				ctor = f;
			if ((flags & ItemFunctionFlag.REF.getValue()) == ItemFunctionFlag.REF.getValue())
				ref = f;
			if ((flags & ItemFunctionFlag.UNREF.getValue()) == ItemFunctionFlag.UNREF.getValue())
				unref = f;
		}


		int mods = JMod.NONE;

		if (ctor == null)
		{
			mods |= JMod.ABSTRACT;
		}

		JDefinedClass cls = cm._class(mods, managedType(gen).fullName(), ClassType.CLASS);
		// Add the API interface
		JDefinedClass api = cls._class(JMod.PUBLIC, "API", ClassType.INTERFACE);
		// Add every jna function
		for (int j = 0; j < functions.size(); j++)
		{
			JMethod f = functions.get(j).generatePinvoke(gen, api);
		}

		api._implements(cm.ref("com.sun.jna.Library"));
		if (ref != null && unref != null)
		{
			cls._implements(cm.ref("org.ender.common.ReferenceableObject"));
			// Add the private member handle
			JFieldVar field = cls.field(JMod.PRIVATE, cm.ref("com.sun.jna.Pointer"), "handle");
			// Add the Referenceable overrides (ref, unref, getHandle)
			JMethod method = cls.method(JMod.PUBLIC, cm.VOID, "ref");
			method.annotate(Override.class);
			JBlock body = method.body();

			method = cls.method(JMod.PUBLIC, cm.VOID, "unref");
			method.annotate(Override.class);

			method = cls.method(JMod.PUBLIC, cm.ref("com.sun.jna.Pointer"), "getHandle");
			method.annotate(Override.class);
			body = method.body();
			body._return(field);
			
			// Add the constructor 
		}

		ItemObject parent = getInherit();
		if (parent != null)
		{
			JClass parentCls = cm.ref(parent.managedType(gen).fullName());
			cls._extends(parentCls);
		}

		// Add every method/function
		for (int j = 0; j < functions.size(); j++)
		{
			functions.get(j).generate(gen, cls);
		}
	}
}
