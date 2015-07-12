// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar:/usr/share/java/codemodel.jar ender2java enesim

import org.ender.*;

import java.util.List;
import java.io.File;
import java.io.IOException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JClassAlreadyExistsException;

public class ender2java {
	private static JCodeModel cm;
	private static Lib lib;

	public static void generateObject(ItemObject o)
	{
		List<ItemFunction> functions = o.getFunctions();
		try {
			JDefinedClass cls = cm._class("org." + lib.getName() + "." + o.getName());
			ItemObject parent = o.getInherit();
			if (parent != null)
			{
				JClass parentCls = cm.ref("org." + lib.getName() + "." + parent.getName());
				cls._extends(parentCls);
			}

		} catch (JClassAlreadyExistsException ex) {
			ex.printStackTrace();
		}

		for (int j = 0; j < functions.size(); j++)
		{
			ItemFunction f = functions.get(j);
			int flags = f.getFlags();
			//if (flags & ItemFunction.Flags.CTOR)
			//	System.out.println("is ctor");
			// flags.contains(ItemFunction.Flags.CTOR)
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
