package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: ProductControllerTest
 * Author:   longzhonghua
 * Date:     2019/5/5 11:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTest {
    //每页数量
    private Integer PAGESIZE=10;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void createIndex() throws Exception {
        //1) 创建一个Settings对象，相当于配置信息，主要配置集群名称。
        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster")
                .build();
        //2) 创建一个客户端client对象
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //3) 使用client对象创建一个索引库
        client.admin().indices().prepareCreate("index_hello")
                //执行操作
                .get();
        //4) 关闭client对象
        client.close();
    }

    @Test
    public void save() {
        long id= System.currentTimeMillis();
        Product product = new Product(id,
                "红富士","水果",7.99,"/img/p1.jpg","这是一个测试商品");
        productRepository.save(product);

        System.out.println(product.getId());
    }


    @Test
    public void getProduct() {
        Product product = productRepository.findByName("红富士");
        System.out.println(product.getId());

    }
    @Test
    public void update() {
        long id=1628613562459L;
  Product product = new Product(id,
          "金帅","水果",7.99,"/img/p1.jpg","金帅也和红富士一样，非常好吃，脆脆的");
   productRepository.save(product);
    }

    @Test
    public void getProductById() {
        Product product = productRepository.findById(1628613562459L);
        System.out.println(product.getName()+product.getBody());
    }
    @Test
    public void delete() {
        long id=1557032203515L;
      productRepository.deleteById(id);
    }

    @Test
    public void getAll() {
        Iterable<Product> list = productRepository.findAll(Sort.by("id").ascending());
        for (Product product : list) {
            System.out.println(product);
        }

    }

}