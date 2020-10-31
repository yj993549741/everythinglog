package com.keymonster.everythinglog.dao;

import com.keymonster.everythinglog.poject.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
   Tag findByName(String name);
   @Query("select t from Tag t")
   List<Tag>findTop(Pageable pageable);
}
