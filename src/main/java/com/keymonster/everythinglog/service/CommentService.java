package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.poject.Comment;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface CommentService {
    List<Comment>listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
