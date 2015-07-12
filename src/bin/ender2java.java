// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar:/usr/share/java/codemodel.jar ender2java enesim

import org.ender.*;

import java.util.List;
import java.io.File;
import java.io.IOException;
import com.sun.codemodel.JCodeModel;

public class ender2java {

	public static void main(String[] args)
	{
		Ender.init();
		Lib lib = Lib.find(args[0]);
		System.out.println("Lib " + args[0] + " version " + lib.getVersion() + " name " + lib.getName());
		List<Lib> dependencies = lib.getDependencies();
		for (int i = 0; i < dependencies.size(); i++)
		{
			System.out.println("Dep: " + dependencies.get(i).getName());
		}

		JCodeModel cm = new JCodeModel();

		// Generate objects
		List<Item> objects = lib.listItem(ItemType.OBJECT);
		for (int i = 0; i < objects.size(); i++)
		{
			ItemObject o = (ItemObject)objects.get(i);
			System.out.println("Object " + o.getName());
			ItemObject parent = o.getInherit();
			while (parent != null)
			{
				//System.out.println("+ " + parent.getName());
				parent = parent.getInherit();
			}

			List<ItemFunction> functions = o.getFunctions();
			for (int j = 0; j < functions.size(); j++)
			{
				ItemFunction f = functions.get(j);
				int flags = f.getFlags();
				System.out.println("Function " + f.getName() + " flags " + flags);
				//if (flags & ItemFunction.Flags.CTOR)
				//	System.out.println("is ctor");
				// flags.contains(ItemFunction.Flags.CTOR)
			}
		}

		try {
			cm.build(new File("."));
		} catch (IOException ex) {

		}
		Ender.shutdown();
	}
}
