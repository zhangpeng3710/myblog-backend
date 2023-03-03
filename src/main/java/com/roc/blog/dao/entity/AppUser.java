package com.roc.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis-plus-auto-generator
 * @since 2023--03-03 13:52:31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("APP_USER")
public class AppUser extends Model<AppUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 年龄
     */
    @TableField("PWD")
    private String pwd;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 手机号
     */
    @TableField("TEL")
    private String tel;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
