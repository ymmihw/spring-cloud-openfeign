package com.ymmihw.spring.cloud.service.impl;

import com.ymmihw.spring.cloud.client.JSONPlaceHolderClient;
import com.ymmihw.spring.cloud.model.Post;
import com.ymmihw.spring.cloud.service.JSONPlaceHolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JSONPlaceHolderServiceImpl implements JSONPlaceHolderService {
    private final JSONPlaceHolderClient jsonPlaceHolderClient;

    @Override
    public List<Post> getPosts() {
        return jsonPlaceHolderClient.getPosts();
    }

    @Override
    public Post getPostById(Long id) {
        return jsonPlaceHolderClient.getPostById(id);
    }
}
