package com.github.javarch.persistence.orm.hibernate.spi.multitenant;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;


@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Component
public @interface CurrentTenantIdentifierResolver {

}
