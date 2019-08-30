package io.github.cepr0.demo.model.base;

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
	@Version private Long version;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
