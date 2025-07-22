package com.geekplus.java.oms.dao;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import com.geekplus.java.oms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    public List<Product> getProductList();

    public Order getProductById(@Param("id") String id);
}
