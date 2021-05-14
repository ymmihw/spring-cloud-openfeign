package com.ymmihw.spring.cloud;

import com.ymmihw.spring.cloud.service.UploadClient;
import com.ymmihw.spring.cloud.service.UploadResource;
import feign.Feign;
import feign.Response;
import feign.form.spring.SpringFormEncoder;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OpenFeignFileUploadLiveTest {

    @Autowired
    private UploadClient uploadClient;

    private static String FILE_NAME = "fileupload.txt";

    @Test
    public void whenFeignBuilder_thenFileUploadSuccess() throws IOException, InterruptedException {
        MockWebServer server = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                Buffer body = request.getBody();
                try {
                    MultiPartStringParser multiPartStringParser = new MultiPartStringParser(body.readString(Charset.defaultCharset()));
                    multiPartStringParser.getDiskFileItems().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new MockResponse().setResponseCode(500);
                }
                return new MockResponse().setResponseCode(200);
            }
        };
        server.setDispatcher(dispatcher);
        server.start();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        UploadResource fileUploadResource = Feign.builder().encoder(new SpringFormEncoder())
                .target(UploadResource.class, server.url("/upload").toString());
        Response response = fileUploadResource.uploadFile(multipartFile);
        assertTrue(response.status() == 200);
        server.shutdown();

    }

    @Test
    public void whenAnnotatedFeignClient_thenFileUploadSuccess() throws IOException {
        MockWebServer server = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                Buffer body = request.getBody();
                try {
                    MultiPartStringParser multiPartStringParser = new MultiPartStringParser(body.readString(Charset.defaultCharset()));
                    FileItem fileItem = multiPartStringParser.getDiskFileItems().get(0);
                    return new MockResponse().setResponseCode(200).setBody(fileItem.getFieldName());
                } catch (Exception e) {
                    e.printStackTrace();
                    return new MockResponse().setResponseCode(500);
                }
            }
        };
        server.setDispatcher(dispatcher);
        server.start();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(FILE_NAME).getFile());
        assertTrue(file.exists());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));

        URI determinedBasePathUri = URI.create(server.url("/upload").toString());
        String s = uploadClient.fileUpload(determinedBasePathUri, multipartFile);
        assertEquals("file", s);
        server.shutdown();
    }
}
