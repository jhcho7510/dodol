package com.dodol.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)	// annotation이 적용 가능한 대상을 지정함.
// @Target(ElementType.FIELD)	// annotation이 적용 가능한 대상을 지정함.
@Retention(RetentionPolicy.RUNTIME) // annotation이 유지되는 기간(CLASS가 기본값)
public @interface CellOrderAnnotation {
	int order() default 0;

}
