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

	public static void generateEnum(ItemEnum o)
	{
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
			try {
				o.generate(gen);
			} catch (JClassAlreadyExistsException ex) {
				ex.printStackTrace();
			}
		}

		List<Item> enums = lib.listItem(ItemType.ENUM);
		for (int i = 0; i < enums.size(); i++)
		{
			ItemEnum o = (ItemEnum)enums.get(i);
			try {
				o.generate(gen);
			} catch (JClassAlreadyExistsException ex) {
				ex.printStackTrace();
			}
		}

		try {
			cm.build(new File("."));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Ender.shutdown();
	}
}
