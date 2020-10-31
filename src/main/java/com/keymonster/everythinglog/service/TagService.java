package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.poject.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface TagService {

    Tag saveTag(Tag type);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    Page<Tag>listTag(Pageable pageable);
    List<Tag>listTag();
    List<Tag>listTagTop(Integer size);
    List<Tag>listTag(String ids);
    Tag updateTag(Long id,Tag type);
    void deleteTag(Long id);
}
