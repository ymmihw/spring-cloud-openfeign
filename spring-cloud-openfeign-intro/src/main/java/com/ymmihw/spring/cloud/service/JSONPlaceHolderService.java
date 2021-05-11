package com.ymmihw.spring.cloud.service;

import com.ymmihw.spring.cloud.model.Post;

import java.util.List;

public interface JSONPlaceHolderService {

    List<Post> getPosts();

    Post getPostById(Long id);
}
