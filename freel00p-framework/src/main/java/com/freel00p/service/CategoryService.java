package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddCategoryVo;
import com.freel00p.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.vo.CategoryListVo;

import java.util.List;

/**
* @author freeloop
* @description 针对表【fl_category(分类表)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryListVo> listCategory();

    ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryVo addCategoryVo);

    ResponseResult updateCategory(CategoryListVo categoryListVo);

    ResponseResult removeCategory(Long id);
}
