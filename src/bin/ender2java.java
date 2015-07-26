// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar:/usr/share/java/codemodel.jar ender2java enesim

import org.ender.*;

import org.ender.common.annotations.Transfer;

import java.util.List;
import java.io.File;
import java.io.IOException;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.codemodel.JMods;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JType;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.ClassType;

public class ender2java {
	private static Generator gen;
	private static JCodeModel cm;
	private static Lib lib;

	public static JMethod generatePinvoke(ItemFunction f, JDefinedClass cls)
	{
		//int mods = JMod.NONE;
		//if ((flags & ItemFunctionFlag.IS_METHOD.getValue()) == ItemFunctionFlag.IS_METHOD.getValue())
		//	mods |= JMod.STATIC;

		ItemArg retItem = f.getRet();
		JType ret = cm.VOID;
		String name;

		Item parent = f.getParent();

		if (parent == null)
		{
			name = lib.getName() + "_" + f.getName();
		}
		else
		{
			name = parent.getName() + "_" + f.getName();
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

		List<ItemArg> args = f.getArgs();
		for (int i = 0; i < args.size(); i++)
		{
			ItemArg arg = args.get(i);
			JVar param = method.param(arg.unmanagedType(gen, arg.getDirection(), arg.getTransfer()), arg.getName());
		}

		return method;
	}

	public static void generateEnum(ItemEnum o)
	{
		try {
			int mods = JMod.NONE;

			JDefinedClass cls = cm._class(mods, o.managedType(gen).fullName(), ClassType.ENUM);
		} catch (JClassAlreadyExistsException ex) {
			ex.printStackTrace();
		}
	}

	public static void generateObject(ItemObject o)
	{
		List<ItemFunction> functions = o.getFunctions();
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


		try {
			int mods = JMod.NONE;

			if (ctor == null)
			{
				mods |= JMod.ABSTRACT;
			}

			JDefinedClass cls = cm._class(mods, o.managedType(gen).fullName(), ClassType.CLASS);
			// Add the API interface
			JDefinedClass api = cls._class(JMod.PUBLIC, "API", ClassType.INTERFACE);
			// Add every jna function
			for (int j = 0; j < functions.size(); j++)
			{
				JMethod f = generatePinvoke(functions.get(j), api);
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

			ItemObject parent = o.getInherit();
			if (parent != null)
			{
				JClass parentCls = cm.ref(parent.managedType(gen).fullName());
				cls._extends(parentCls);
			}
		} catch (JClassAlreadyExistsException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		Ender.init();

		lib = Lib.find(args[0]);
		if (lib == null)
			return;

		List<Lib> dependencies = lib.getDependencies();
		for (int i = 0; i < dependencies.size(); i++)
		{
			System.out.println("Dep: " + dependencies.get(i).getName());
		}

		gen = new Generator();
		cm = new JCodeModel();

		gen.cm = cm;
		gen.prefix = "org";

		// Generate objects
		List<Item> objects = lib.listItem(ItemType.OBJECT);
		for (int i = 0; i < objects.size(); i++)
		{
			ItemObject o = (ItemObject)objects.get(i);
			generateObject(o);
		}

		List<Item> enums = lib.listItem(ItemType.ENUM);
		for (int i = 0; i < enums.size(); i++)
		{
			ItemEnum o = (ItemEnum)enums.get(i);
			generateEnum(o);
		}

		try {
			cm.build(new File("."));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Ender.shutdown();
	}
}
