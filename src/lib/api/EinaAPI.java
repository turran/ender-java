package org.ender.api;

import org.ender.Lib;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.util.Arrays;

public interface EinaAPI extends Library {
	public static final class List extends com.sun.jna.Structure {
		public volatile Pointer data;
		public volatile Pointer _next;
		public volatile Pointer _prev;

		public List() {
			clear();
		}

		public List(Pointer ptr) {
			useMemory(ptr);
			read();
		}

		public static List valueOf(Pointer ptr) {
			return ptr != null ? new List(ptr) : null;
		}

		public List next() {
			return valueOf(_next);
		}
		public List prev() {
			return valueOf(_prev);
		}

		@Override
		protected java.util.List<String> getFieldOrder() {
			return Arrays.asList(new String[]{
				"data", "_next", "_prev"
			});
		}
	}
}
