package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddLinkDto;
import com.freel00p.domain.vo.LinkGetVo;
import com.freel00p.service.LinkService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LinkController
 *
 * @author fj
 * @since 2023/8/13 9:32
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult queryList(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.queryList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable Long id){
        return linkService.getLink(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkGetVo linkGetVo){
        return linkService.updateLink(linkGetVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.removeLink(id);
    }

}
