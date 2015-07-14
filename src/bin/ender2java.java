// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar:/usr/share/java/codemodel.jar ender2java enesim

import org.ender.*;

import java.util.List;
import java.io.File;
import java.io.IOException;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JMods;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.ClassType;

public class ender2java {
	private static JCodeModel cm;
	private static Lib lib;

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

			JDefinedClass cls = cm._class(mods, "org." + o.getQualifiedClassName(), ClassType.CLASS);
			if (ref != null && unref != null)
			{
				JClass referenceableCls = cm.ref("org.ender.common.ReferenceableObject");
				cls._implements(referenceableCls);
			}

			ItemObject parent = o.getInherit();
			if (parent != null)
			{
				JClass parentCls = cm.ref("org." + parent.getQualifiedClassName());
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

		cm = new JCodeModel();

		// Generate objects
		List<Item> objects = lib.listItem(ItemType.OBJECT);
		for (int i = 0; i < objects.size(); i++)
		{
			ItemObject o = (ItemObject)objects.get(i);
			generateObject(o);
		}

		try {
			cm.build(new File("."));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Ender.shutdown();
	}
}
