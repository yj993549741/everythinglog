package com.keymonster.everythinglog.web.admin;

import com.keymonster.everythinglog.poject.Comment;
import com.keymonster.everythinglog.poject.User;
import com.keymonster.everythinglog.service.BlogService;
import com.keymonster.everythinglog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * created by yangjie sheting on 2020/10/28
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private  String avatar;
    @GetMapping("/comment/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
         model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog::comment";
    }
    @PostMapping("comments")
    public String post(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user=(User)session.getAttribute("user");
        if (user==null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
