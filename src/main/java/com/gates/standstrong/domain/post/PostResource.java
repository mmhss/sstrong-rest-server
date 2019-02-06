package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + PostResource.RESOURCE_URL)
public class PostResource extends BaseResource<Post, PostDto> {

    public static final String RESOURCE_URL = "/posts";

    @Inject
    public PostResource(PostService postService, PostMapper postMapper){
        super(postService, postMapper, Post.class, QPost.post );
    }

}
