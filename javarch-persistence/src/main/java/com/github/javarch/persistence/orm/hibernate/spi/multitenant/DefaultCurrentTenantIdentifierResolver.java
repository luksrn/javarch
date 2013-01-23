package com.github.javarch.persistence.orm.hibernate.spi.multitenant;

import org.springframework.context.annotation.Profile;

import com.github.javarch.support.spring.Profiles;


@CurrentTenantIdentifierResolver
@Profile(Profiles.MULT_TENANT)
public class DefaultCurrentTenantIdentifierResolver implements org.hibernate.context.spi.CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return "jnark";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
}
