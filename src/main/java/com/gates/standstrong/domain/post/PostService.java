package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseService;

import java.util.List;

public interface PostService extends BaseService<Post> {

    List<Post> getPendingPostsToMothers();

    Post buildPost(String callback);
}
