package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface PostRepository extends BaseRepository<Post> {

    @Query("SELECT post FROM Post post WHERE post.status='" + Post.STATUS_PENDING + "' AND post.direction = 'ToMother'")
    List<Post> getPendingPostsToMothers();

}
