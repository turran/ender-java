// To run,
// java -cp src/bin:src/lib/:/usr/share/java/jna.jar ender2java enesim

import org.ender.*;

public class ender2java {

	public static void main(String[] args)
	{
		Ender.init();
		Lib lib = Lib.find(args[0]);
		System.out.println("Lib " + args[0] + " version " + lib.getVersion());
		Ender.shutdown();
	}
}
