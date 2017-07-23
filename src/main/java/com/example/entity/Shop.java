package com.example.entity;

//import org.hibernate.annotations.Entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;

//import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "t_shop")
@Document(indexName = "kyjes", type = "shop")
//@Setting(settingPath = 'elasticSearchSettings/analyzer.json')
public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;
    @Field(type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @Column(name = "Name", unique = false, nullable = false)
    private String name;
    @Field(type = FieldType.Float, analyzer = "ik_max_word")
    @Column(name = "Address")
    private String address;
    @Column(name = "Dname")
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String dname;
    @Column(name = "Cname")
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String cname;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

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
