package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.dao.BlogRepository;
import com.keymonster.everythinglog.poject.Blog;
import com.keymonster.everythinglog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface BlogService {
    Blog getBlog(Long id);
    Blog  getAndConvert(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery query);
    Page<Blog>listBlog(Pageable pageable);
    Page<Blog>listBlog(Long tagId,Pageable pageable);
    Page<Blog>listBlog(String query,Pageable pageable);
    List<Blog>listRecommendBlogTop(Integer size);
    Map<String ,List<Blog>>archiveBlog();
    Long countBlog();
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
}
