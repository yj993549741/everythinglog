package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.NotFoundException;
import com.keymonster.everythinglog.dao.BlogRepository;
import com.keymonster.everythinglog.poject.Blog;
import com.keymonster.everythinglog.util.MarkdownUtils;
import com.keymonster.everythinglog.util.MyBeanUtils;
import com.keymonster.everythinglog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

/**
 * created by yangjie sheting on 2020/10/25
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.findOne(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery query) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate>predicates=new ArrayList<>();
                if (!"".equals(query.getTitle())&&query.getTitle()!=null){
                    predicates.add(cb.like(root.get("title"),"%"+query.getTitle()+"%"));
                }
                if (query.getTypeId()!=null){
                    predicates.add(cb.equal(root.get("type").get("id"),query.getTypeId()));
                }
                if (query.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"),query.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
       return blogRepository.findAll(new Specification<Blog>() {
           @Override
           public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cd) {
               Join join =root.join("tags");
               return cd.equal(join.get("id"),tagId);
           }
       },pageable);

    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String >years=blogRepository.findGroupYear();
        Map<String ,List<Blog>>map=new HashMap<>();
        for (String year:years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.findOne(id);
        if (b==null){
            throw new NotFoundException("该博客不存在!");

        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {

        blogRepository.delete(id);
    }
}
