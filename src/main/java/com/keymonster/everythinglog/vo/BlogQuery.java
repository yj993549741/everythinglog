package com.keymonster.everythinglog.vo;

/**
 * created by yangjie sheting on 2020/10/25
 */
public class BlogQuery {
    private String title;
    private Long TypeId;
    private boolean recommend;

    public BlogQuery() {
    }
    public BlogQuery(Long id) {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return TypeId;
    }

    public void setTypeId(Long typeId) {
        TypeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", TypeId='" + TypeId + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
