package com.example.springboot_es.noSql.repository;

import com.example.springboot_es.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository  extends ElasticsearchRepository<Goods,Long> {

    Page<Goods> findByName(String name,String price,String remarks,Pageable page);
}
