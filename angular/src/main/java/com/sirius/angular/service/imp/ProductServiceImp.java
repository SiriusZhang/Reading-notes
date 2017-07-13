package com.sirius.angular.service.imp;

import com.sirius.angular.entity.Product;
import com.sirius.angular.mapper.ProductMapper;
import com.sirius.angular.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("com.sirius.angular.service.ProductService")
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProducts() {
        return productMapper.selectByPrimaryKey(null);
    }
}