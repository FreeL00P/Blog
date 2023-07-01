package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.Category;
import com.freel00p.service.CategoryService;
import com.freel00p.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【fl_category(分类表)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

}




