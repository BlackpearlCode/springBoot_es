package com.example.springboot_es.controller;


import com.example.springboot_es.entity.Goods;
import com.example.springboot_es.service.GoodsService;
import com.example.springboot_es.service.impl.GoodsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
public class GoodsController {

    //查询
    @Resource(name="goodsimpl")
    private GoodsService goodsService;

    @Autowired
    private GoodsServiceImpl goodsServiceImpl;



    @RequestMapping(value = "/importAll",method = RequestMethod.POST)
    @ApiOperation(value = "导入所有数据库中的商品到es")
    @ResponseBody
    public Boolean importAllList(Goods goods){
//        goodsService.delete(12L);
        int num=goodsService.importAll55(goods);
        Boolean bool=false;
        if(num!=0){
            bool=true;
        }
        return bool;
    }

    //不带高亮查询
    @ResponseBody
    @RequestMapping(value = "/noHightQuery",method = RequestMethod.GET)
    public List<Goods> noHightQuery(String keyword) throws IOException {
        return goodsServiceImpl.noHightQuery(keyword);
    }

    //带高亮查询
    @ResponseBody
    @RequestMapping(value = "/HightQuery",method = RequestMethod.GET)
    public List<Goods> HightQuery(String keyword) throws IOException {
        return goodsServiceImpl.highlightQuery(keyword);
    }


}
