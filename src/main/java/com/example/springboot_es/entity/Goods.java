package com.example.springboot_es.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = "crm")
public class Goods implements Serializable {

    @Id
    private Integer rid;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    private Double price;

    private String remarks;

    private static final long serialVersionUID = 1L;


}