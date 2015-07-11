package org.ender.api;

import org.ender.ItemTransfer;
import org.ender.common.ReferenceableObject;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;

public interface EinaAPI extends Library {
	public static final class List extends Structure {
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

		public <T> java.util.List<T> toList(Class<T> contentClass,
				ItemTransfer transfer)
		{
			EinaAPI.List next = this;
			java.util.List<T> ret = new java.util.ArrayList<T>();

			while (next != null)
			{
				if (next.data == null)
					continue;
				// Check the type
				if (ReferenceableObject.class.isAssignableFrom(contentClass))
				{
					Class<ReferenceableObject> roClass = (Class<ReferenceableObject>)contentClass;
					ReferenceableObject obj = ReferenceableObject.construct(roClass, next.data, true);
					if (obj != null)
						ret.add((T)obj);
				}
				else
				{
					throw new RuntimeException("Unsupported class " + contentClass);
				}
				next = next.next();
			}
			return ret;
		}

		@Override
		protected void finalize() throws Throwable
		{
			System.out.println("finalizing list");
		}

		@Override
		protected java.util.List<String> getFieldOrder() {
			return Arrays.asList(new String[]{
				"data", "_next", "_prev"
			});
		}
	}
}
