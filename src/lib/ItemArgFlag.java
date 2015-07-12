package org.ender;

public enum ItemArgFlag {
	IS_RETURN        (1 << 0),
	IS_CLOSURE       (1 << 1),
	NULLABLE         (1 << 2),
	DELAYED_CALLBACK (1 << 3);

	private final int val;

	ItemArgFlag(int val)
	{
		this.val = val;
	}
}
