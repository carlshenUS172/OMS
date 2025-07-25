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

    public Product getProductById(@Param("id") String id);

    public Product addProduct(@Param("productName") String productName, @Param("quantity") int quantity);

    int deductQuantity(@Param("productId") String productId, @Param("quantityToDeduct") int quantityToDeduct);

    int getQuantity(String productId);

    void setQuantity(@Param("productId") String productId, @Param("quantity") int quantity);
}
