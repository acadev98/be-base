package com.acadev.baseprojectname.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.acadev.baseprojectname.database.entity.Products;

public interface ProductsRepository extends PagingAndSortingRepository<Products, Integer>, JpaRepository<Products, Integer> {
}