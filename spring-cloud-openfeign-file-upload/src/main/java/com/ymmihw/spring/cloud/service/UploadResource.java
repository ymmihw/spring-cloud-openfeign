package com.ymmihw.spring.cloud.service;

import org.springframework.web.multipart.MultipartFile;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface UploadResource {

    @RequestLine("POST /upload-mannual-client")
    @Headers("Content-Type: multipart/form-data")
    Response uploadFile(@Param("file") MultipartFile file);

}