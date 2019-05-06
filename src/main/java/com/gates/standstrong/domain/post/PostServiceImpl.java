package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {

    private PostRepository postRepository;

    @Inject
    public PostServiceImpl(PostRepository postRepository){
        super(postRepository);
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPendingPostsToMothers() {
        return postRepository.getPendingPostsToMothers();
    }
}
