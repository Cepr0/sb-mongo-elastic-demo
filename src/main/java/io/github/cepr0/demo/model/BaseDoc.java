package io.github.cepr0.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDoc extends MongoEntity<String> implements Persistable<String> {

	@Id private String id;
	@Version private Integer version;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
