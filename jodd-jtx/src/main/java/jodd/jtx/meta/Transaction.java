// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.jtx.meta;

import jodd.jtx.JtxPropagationBehavior;
import jodd.jtx.JtxIsolationLevel;
import jodd.jtx.JtxTransactionMode;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Transaction method annotation for external use.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Transaction {

	/**
	 * Transaction propagation mode. Default value: <code>PROPAGATION_SUPPORTS</code>.
	 */
	JtxPropagationBehavior propagation() default JtxPropagationBehavior.PROPAGATION_SUPPORTS;

	/**
	 * Transaction isolation mode. Default value: <code>ISOLATION_DEFAULT</code>.
	 */
	JtxIsolationLevel isolation() default JtxIsolationLevel.ISOLATION_DEFAULT;

	/**
	 * Transaction read-only mode. Default value: <code>true</code>
	 */
	boolean readOnly() default true;

	/**
	 * Transaction time-out time in seconds.
	 */
	int timeout() default JtxTransactionMode.DEFAULT_TIMEOUT;
	
}
