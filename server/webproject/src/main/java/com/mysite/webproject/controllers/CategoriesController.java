package com.mysite.webproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.webproject.dal.CategoryRepository;
import com.mysite.webproject.model.Category;
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/get")
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

}
