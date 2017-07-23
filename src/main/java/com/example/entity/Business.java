package com.example.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "business")
@Document(indexName = "kyjes", type = "business")
public class Business implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;
    @Field(type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @Column(name = "Name", unique = false, nullable = false)
    private String name;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
