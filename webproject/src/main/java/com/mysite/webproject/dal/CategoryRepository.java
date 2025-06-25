package com.mysite.webproject.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysite.webproject.model.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category,Long>{
    public Category findByName(String name);
}
