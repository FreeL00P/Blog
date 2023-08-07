package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.TagListDto;
import com.freel00p.domain.entity.Tag;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.domain.vo.TagVo;
import com.freel00p.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HelloController
 *
 * @author fj
 * @since 2023/8/5 21:39
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * @return Tag列表
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping()
    public ResponseResult saveTag(@RequestBody TagListDto tag){
        tagService.saveTag(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult removeTagById(@PathVariable Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("{id}")
    public ResponseResult getTagInfo(@PathVariable Long id){

        return ResponseResult.okResult(tagService.getById(id));
    }

    @PutMapping()
    public ResponseResult updateTag(@RequestBody Tag tag){
        return ResponseResult.okResult(tagService.updateById(tag));
    }

    @GetMapping("listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> tagVoList= tagService.listAllTag();
        return ResponseResult.okResult(tagVoList);
    }
}
