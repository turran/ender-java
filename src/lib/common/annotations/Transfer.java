package org.ender.common.annotations;

import org.ender.ItemTransfer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Transfer {
	ItemTransfer value() default ItemTransfer.NONE;
}
