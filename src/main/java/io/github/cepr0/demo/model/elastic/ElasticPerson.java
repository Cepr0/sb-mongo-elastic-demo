package io.github.cepr0.demo.model.elastic;

import io.github.cepr0.crud.model.ContentAlias;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@ContentAlias("people")
@Data
@Document(indexName = "person")
public class ElasticPerson implements Serializable {
	@Id private String id;
	@Version private Long version;
	private String name;
}
