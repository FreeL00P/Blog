package com.freel00p.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddCategoryVo;
import com.freel00p.domain.entity.Category;
import com.freel00p.domain.vo.CategoryListVo;
import com.freel00p.domain.vo.CategoryVo;
import com.freel00p.domain.vo.ExcelCategoryVo;
import com.freel00p.enums.AppHttpCodeEnum;
import com.freel00p.service.CategoryService;
import com.freel00p.utils.WebUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanUtil.copyToList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    /**
     * 分页查询分类列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult queryList(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.queryList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryVo addCategoryVo){
        return categoryService.addCategory(addCategoryVo);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryListVo categoryListVo){
        return categoryService.updateCategory(categoryListVo);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.removeCategory(id);
    }

}
