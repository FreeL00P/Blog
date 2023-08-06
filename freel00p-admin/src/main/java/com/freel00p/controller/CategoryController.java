package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.vo.CategoryListVo;
import com.freel00p.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CategoryController
 *
 * @author fj
 * @since 2023/8/6 20:59
 */
@RequestMapping("/content/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listCategory(){
        List<CategoryListVo> ret= categoryService.listCategory();
        return ResponseResult.okResult(ret);
    }
}
