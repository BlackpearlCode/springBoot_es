package com.example.springboot_es.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.springboot_es.mapper.GoodsMapper;
import com.example.springboot_es.entity.Goods;

import java.util.List;

@Mapper
public interface GoodsService{

    //从数据库中导入所有商品到ES
    public  int importAll55(Goods goods);

    //根据id删除商品
    public void delete(Long id);

    //通过商品id，从数据库中查询商品数据，存入到ES搜索库中
    public Goods create(Long id);

    //移除ES 搜索库中的 商品记录
    public void delete(List<Long> ids);

    //根据关键字搜索 ES库中的 商品数据; 通过关键字，名称或者副标题
    public Page<Goods> search(String name, Integer pageNum, Integer pageSize);

}
