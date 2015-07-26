package org.ender;

import org.ender.common.EnderNative;

import com.sun.jna.Pointer;
import com.sun.jna.Library;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class ItemBasic extends Item {
	private interface API extends Library {
		ValueType ender_item_basic_value_type_get(ItemBasic self);
	}

	static final API api = EnderNative.loadLibrary("ender", API.class);

	public ItemBasic(Pointer raw, boolean doRef)
	{
		super(raw, doRef);
	}

	public ValueType getValueType()
	{
		return api.ender_item_basic_value_type_get(this);
	}

	@Override
	public JType managedType(Generator gen)
	{
		switch (getValueType())
		{
			case BOOL:
			return gen.cm.BOOLEAN;
	
			case UINT8:
			case INT8:
			return gen.cm.CHAR;

			case UINT32:
			case INT32:
			return gen.cm.INT;

			case UINT64:
			case INT64:
			return gen.cm.LONG;

			case DOUBLE:
			return gen.cm.DOUBLE;

			case POINTER:
			case STRING:
			case SIZE:
			return gen.cm.ref("com.sun.jna.Pointer");
		}
		return gen.cm.VOID;
	}

	@Override
	public JType unmanagedType(Generator gen,
			ItemArgDirection direction, ItemTransfer transfer)
	{
		if (direction == ItemArgDirection.OUT)
		{
			switch (getValueType())
			{
				case BOOL:
				return gen.cm.ref("com.sun.jna.ptr.ByteByReference");
		
				case UINT8:
				case INT8:
				return gen.cm.ref("com.sun.jna.ptr.ByteByReference");

				case UINT32:
				case INT32:
				return gen.cm.ref("com.sun.jna.ptr.IntByReference");

				case UINT64:
				case INT64:
				return gen.cm.ref("com.sun.jna.ptr.LongByReference");

				case DOUBLE:
				return gen.cm.ref("com.sun.jna.ptr.DoubleByReference");

				case POINTER:
				return gen.cm.ref("com.sun.jna.ptr.PointerByReference");

				case STRING:
				case SIZE:
				default:
				return gen.cm.VOID;
			}
		}
		else
		{
			return managedType(gen);
		}
	}
}
