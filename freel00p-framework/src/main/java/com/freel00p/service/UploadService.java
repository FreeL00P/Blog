package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadService
 *
 * @author fj
 * @since 2023/8/4 14:03
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
