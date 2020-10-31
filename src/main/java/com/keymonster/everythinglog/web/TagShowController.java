package com.keymonster.everythinglog.web;

import com.keymonster.everythinglog.poject.Tag;
import com.keymonster.everythinglog.service.BlogService;
import com.keymonster.everythinglog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * created by yangjie sheting on 2020/10/28
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = ("updateTime"),direction = Sort.Direction.DESC)Pageable
                       pageable, @PathVariable Long id, Model model){
        List<Tag>tags=tagService.listTagTop(10000);
        if (id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";

    }

}
