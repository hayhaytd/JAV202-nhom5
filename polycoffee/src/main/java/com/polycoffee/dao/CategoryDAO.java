package com.polycoffee.dao;

import com.polycoffee.entity.Category;

public class CategoryDAO extends CrudDAO<Category, Integer> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}