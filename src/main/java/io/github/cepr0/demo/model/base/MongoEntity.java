package io.github.cepr0.demo.model.base;

import io.github.cepr0.crud.model.IdentifiableEntity;

import java.io.Serializable;

public abstract class MongoEntity<ID extends Serializable> implements IdentifiableEntity<ID> {

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{id=" + getId() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!getClass().isInstance(o)) return false;
		return getId() != null && getId().equals(((MongoEntity) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
