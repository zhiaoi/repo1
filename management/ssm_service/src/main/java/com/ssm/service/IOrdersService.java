package com.ssm.service;

import com.ssm.domain.Orders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IOrdersService {
    public List<Orders> findAll(int page,int size) throws Exception;

    Orders findById(String id)throws Exception;
}
