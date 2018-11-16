package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Xueyong on 2018/5/21.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
