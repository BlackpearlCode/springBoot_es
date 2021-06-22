package com.example.springboot_es.noSql.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


@Data
public class GoodsAttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long productAttributeId;  //产品属性ID
    @Field(type = FieldType.Keyword) //属性值
    private String value;
    private Integer type;   //属性参数：0->规格；1->参数
    @Field(type=FieldType.Keyword)
    private String name;    //属性名称

}
