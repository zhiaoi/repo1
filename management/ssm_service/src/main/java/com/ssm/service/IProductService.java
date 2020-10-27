package com.ssm.service;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IProductService {

    public List<Product> findAll() throws Exception;

    void save(Product product);
}
