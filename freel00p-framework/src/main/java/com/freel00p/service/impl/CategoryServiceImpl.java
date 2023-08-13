package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.constants.SystemConstants;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddCategoryVo;
import com.freel00p.domain.entity.Article;
import com.freel00p.domain.entity.Category;
import com.freel00p.domain.vo.CategoryListVo;
import com.freel00p.domain.vo.CategoryVo;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.service.ArticleService;
import com.freel00p.service.CategoryService;
import com.freel00p.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.freel00p.constants.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.freel00p.constants.SystemConstants.CATEGORY_STATUS_NORMAL;

/**
* @author freeloop
* @description 针对表【fl_category(分类表)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //获取所有已发布的文章
        LambdaQueryWrapper<Article> articleWrapper= new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //提取所有的分类id,并去除重复id
        Set<Long> categoryIds = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        //根据分类id查询出所有的分类信息
        List<Category> categoryList = this.listByIds(categoryIds);
        List<Category> collect = categoryList.stream()
                .filter(category -> category.getStatus().equals(CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanUtil.copyToList(collect, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryListVo> listCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        return BeanUtil.copyToList(list, CategoryListVo.class);
    }

    @Override
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String status) {
        //构造查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotEmpty(status),Category::getStatus,status);
        wrapper.like(StrUtil.isNotEmpty(name),Category::getName,name);
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        Page<Category> retPage = page(categoryPage, wrapper);
        List<CategoryListVo> categoryListVos = BeanUtil.copyToList(retPage.getRecords(), CategoryListVo.class);

        PageVo pageVo = new PageVo();
        pageVo.setTotal(retPage.getTotal());
        pageVo.setRows(categoryListVos);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(AddCategoryVo addCategoryVo) {
        Category category = BeanUtil.copyProperties(addCategoryVo, Category.class);
        this.save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategory(CategoryListVo categoryListVo) {
        Category category = BeanUtil.copyProperties(categoryListVo, Category.class);
        this.updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeCategory(Long id) {
        this.removeById(id);
        return ResponseResult.okResult();
    }
}




