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
import java.util.List;

@RestController
public class GoodsController {

    @Resource(name="goodsimpl")
    private GoodsService goodsService;
    @RequestMapping(value = "/importAll",method = RequestMethod.POST)
    @ApiOperation(value = "导入所有数据库中的商品到es")
    @ResponseBody
    public Boolean importAllList(){
//        goodsService.delete(12L);
        int num=goodsService.importAll55();
        Boolean bool=false;
        if(num!=0){
            bool=true;
        }
        return bool;
    }

    @Test
   public void test1(){
        GoodsService gs=new GoodsServiceImpl();
        gs.delete(1L);

    }

}
