package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.dao.CommentRepository;
import com.keymonster.everythinglog.poject.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by yangjie sheting on 2020/10/25
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=new Sort("creatTime");
        List<Comment>comments=commentRepository.findByBlogIdAndParentCommentNull(blogId,sort) ;
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     * */
    private List<Comment>eachComment(List<Comment>comments){
        List<Comment>commentsView=new ArrayList<>();
        for (Comment comment:comments){
            Comment c =new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各个子代到第一子代集合中
         combineChildren(commentsView);
        return commentsView;
    }
    /**
     * */
    //combine 结合
    private void combineChildren(List<Comment>comments){
        for (Comment comment:comments){
            List<Comment>replys1=comment.getReplyComments();
            for (Comment reply1:replys1){
                //循环迭代，摘除子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合中为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys=new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment>tempReplys=new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @Param comment 被迭代的对象
     * @return
     * */
    private void recursively(Comment comment){
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0){
            List<Comment>replys=comment.getReplyComments();
            for (Comment reply:replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
