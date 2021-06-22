package com.example.springboot_es.mapper;

import com.example.springboot_es.entity.Goods;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);


    List<Goods> selectAll();
}