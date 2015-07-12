package org.ender;

public enum ItemAttrFlag {
	VALUE_OF  (1 << 0),
	DOWNCAST  (1 << 1),
	NULLABLE  (1 << 2);

	private final int val;

	ItemAttrFlag(int val)
	{
		this.val = val;
	}
}

