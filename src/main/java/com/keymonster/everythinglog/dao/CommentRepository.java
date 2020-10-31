package com.keymonster.everythinglog.dao;

import com.keymonster.everythinglog.poject.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment>findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
