package com.keymonster.everythinglog.dao;

import com.keymonster.everythinglog.poject.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface TypeRepository extends JpaRepository<Type,Long> {


     Type findByName(String name);
     @Query("select t from Type t")
     List<Type>findTop(Pageable pageable);

}
