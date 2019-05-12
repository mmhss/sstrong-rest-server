package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseServiceImpl;
import com.gates.standstrong.domain.mother.MotherService;
import com.gates.standstrong.utils.JSONUtils;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {

    private PostRepository postRepository;
    private MotherService motherService;

    @Inject
    public PostServiceImpl(PostRepository postRepository, MotherService motherService){
        super(postRepository);
        this.postRepository = postRepository;
        this.motherService = motherService;
    }

    @Override
    public List<Post> getPendingPostsToMothers() {
        return postRepository.getPendingPostsToMothers();
    }

    @Override
    public Post buildPost(String callback) {
        Post post = new Post();
        post.setDirection("ToCounsellor");
        try {
            String message = JSONUtils.parse(callback,"message");
            if("text".equals(JSONUtils.parse(message,"type"))){
                post.setMessage(JSONUtils.parse(message,"text"));
            }

            if("sticker".equals(JSONUtils.parse(message,"type"))){
                post.setMedia(JSONUtils.parse(message,"media"));
                post.setMessage(JSONUtils.parse(message,"media"));
            }

            String sender = JSONUtils.parse(callback, "sender");
            post.setMother(motherService.findMother(JSONUtils.parse(sender, "id")));

            String timestamp = JSONUtils.parse(callback, "timestamp");
            post.setPostedDate(new Timestamp(Long.parseLong(timestamp)).toLocalDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        post.setStatus("PENDING");
        return post;
    }
}
