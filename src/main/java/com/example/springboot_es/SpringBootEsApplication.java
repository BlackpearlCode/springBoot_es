package com.example.springboot_es;

import com.example.springboot_es.noSql.repository.GoodsRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@MapperScan({"com.example.springboot_es","com.example.springboot_es.service"})
@EnableElasticsearchRepositories()
public class SpringBootEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEsApplication.class, args);
    }



}
