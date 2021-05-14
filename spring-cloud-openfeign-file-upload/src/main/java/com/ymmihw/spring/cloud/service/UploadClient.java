package com.ymmihw.spring.cloud.service;

import com.ymmihw.spring.cloud.config.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@FeignClient(name = "file", url = "http://localhost:8080", configuration = FeignSupportConfig.class)
public interface UploadClient {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String fileUpload(@RequestPart(value = "file") MultipartFile file);

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String fileUpload(URI baseUrl, @RequestPart(value = "file") MultipartFile file);
}
