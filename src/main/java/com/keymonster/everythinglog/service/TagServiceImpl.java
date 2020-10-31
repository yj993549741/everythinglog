package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.NotFoundException;
import com.keymonster.everythinglog.dao.TagRepository;
import com.keymonster.everythinglog.poject.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
@Service
public class TagServiceImpl  implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag saveTag(Tag type) {
        return tagRepository.save(type);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAll(convertToList(ids));
    }

    private List<Long>convertToList(String ids){
        List<Long>list=new ArrayList<>();
        if (!"".equals(ids)&&ids!=null){
            String[]idArray=ids.split(",");
            for (int i=0;i<idArray.length;i++){
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.findOne(id);
        if (t==null){
            throw new NotFoundException("不存在此标签！");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
            tagRepository.delete(id);
    }
}
