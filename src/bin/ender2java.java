// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar ender2java enesim

import org.ender.*;

import java.util.List;

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
		// Generate objects
		List<Item> objects = lib.listItem(ItemType.OBJECT);
		for (int i = 0; i < objects.size(); i++)
		{
			System.out.println("Object");
		}

		Ender.shutdown();
	}
}
