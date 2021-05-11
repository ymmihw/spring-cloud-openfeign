package com.ymmihw.spring.cloud.hystrix;

import com.ymmihw.spring.cloud.client.JSONPlaceHolderClient;
import com.ymmihw.spring.cloud.model.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }
}
