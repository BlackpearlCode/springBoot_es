package com.example.springboot_es.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.example.springboot_es.entity.Goods;
import com.example.springboot_es.mapper.GoodsMapper;
import com.example.springboot_es.noSql.repository.GoodsRepository;
import com.example.springboot_es.service.GoodsService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service(value = "goodsimpl")
public class GoodsServiceImpl implements GoodsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);
    //通过 注解 注入 dao层接口对象：将xml的实现注入到 接口对象中
    @Autowired(required = false)
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public int importAll55(Goods goods) {
        //查询所有商品
        List<Goods> esProductList = goodsMapper.selectAll(goods);
        //接收对象集合，实现批量新增到ES：操作对象数据的 存储库中
        Iterable<Goods> esProductIterable = goodsRepository.saveAll(esProductList);
        System.out.println("esProductIterable====>>"+esProductIterable.toString());
        //统计 新增的数量
        Iterator<Goods> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        //根据商品ID从ES库中：移除数据
        goodsRepository.deleteById(id);
    }

    /**保存*/
    @Override
    public Goods create(Long id) {
        Goods result = null;
        //通过商品id，从数据库中查询商品数据
        List<Goods> esProductList = (List<Goods>) goodsMapper.selectByPrimaryKey(Math.toIntExact(id));
        if (esProductList.size() > 0) {
            //查询出数据后：出入ES库中
            Goods esProduct = esProductList.get(0);
            result = goodsRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        //判断集合对象 是否为空
        if (!CollectionUtils.isEmpty(ids)) {
            //定义集合对象
            List<Goods> esProductList = new ArrayList<>();
            //遍历：传入的ids集合
            for (Long id : ids) {
                //分组为ES库中的对象
                Goods esProduct = new Goods();
                esProduct.setRid(Math.toIntExact(id));
                //传入对象
                esProductList.add(esProduct);
            }
            //批量: 移除库中对象
            goodsRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<Goods> search(String keyword, Integer pageNum, Integer pageSize) {
        /*
         * PageRequest:spring-data类的一个公共类
         *  Pageable:封装了分页的参数，当前页，每页的显示的条数。
         */
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        //NameOrSubTitleOrKeywords :根据名称 简介 关键字：从ES中 检索数据

        return goodsRepository.findByName( keyword,keyword,keyword,pageable);
    }


    /**
     * 不带高亮查询
     */

    public List<Goods> noHightQuery(String keyword) throws IOException {
        //crm 就是@Document(indexName="crm")的名字，是索引也就是表名
        SearchRequest searchRequest=new SearchRequest("crm");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder()
        //在discusspost索引的name字段中查询
        .query(QueryBuilders.multiMatchQuery(keyword,"name"))
        .sort(SortBuilders.fieldSort("price").order(SortOrder.DESC))   //价格
        .from(0)  //指定从那条开始查询
        .size(10);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
        List<Goods> list=new ArrayList<>();
        for (SearchHit hit: searchResponse.getHits().getHits()) {
            Goods goods= JSONObject.parseObject(hit.getSourceAsString(),Goods.class);
            System.out.println(goods);
            list.add(goods);
        }
        return list;
    }
}
