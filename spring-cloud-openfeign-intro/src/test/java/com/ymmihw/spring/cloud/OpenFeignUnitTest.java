package com.ymmihw.spring.cloud;


import com.ymmihw.spring.cloud.model.Post;
import com.ymmihw.spring.cloud.service.JSONPlaceHolderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class OpenFeignUnitTest {

    @Autowired
    private JSONPlaceHolderService jsonPlaceHolderService;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

    @Test
    public void whenGetPosts_thenListPostSizeGreaterThanZero() {

        List<Post> posts = jsonPlaceHolderService.getPosts();

        assertFalse(posts.isEmpty());
    }

    @Test
    public void whenGetPostWithId_thenPostExist() {

        Post post = jsonPlaceHolderService.getPostById(1L);

        assertNotNull(post);
    }

}
