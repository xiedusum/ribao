package com.whz.ribao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/18.
 */
@MappedSuperclass
public abstract class BaseBao extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String module;
    private String description;
    private String name;
    private Integer plan;
    private Integer practical;
    @Column(length = 65535, columnDefinition = "Text")
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @ManyToOne
    private UserInfo userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getPractical() {
        return practical;
    }

    public void setPractical(Integer practical) {
        this.practical = practical;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public abstract String getGroupBy();
}
