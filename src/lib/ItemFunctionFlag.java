package org.ender;

public enum ItemFunctionFlag {
	IS_METHOD (1 << 0),
	THROWS    (1 << 1),
	CTOR      (1 << 2),
	REF       (1 << 3),
	UNREF     (1 << 4),
	CALLBACK  (1 << 5),
	VALUE_OF  (1 << 6),
	DOWNCAST  (1 << 7);

	private final int val;

	ItemFunctionFlag(int val)
	{
		this.val = val;
	}

	public int getValue()
	{
		return this.val;
	}
}
