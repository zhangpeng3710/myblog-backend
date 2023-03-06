package com.roc.blog.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "用户参数实体")
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
    @Schema(description = "用户名")
    @TableField("NAME")
    private String name;

    /**
     * 年龄
     */
    @Schema(description = "密码，6-18位，包含大小写、数字及特殊字符")
    @TableField("PWD")
    private String pwd;

    /**
     * 邮箱
     */
    @Schema(example = "zhangsan@lanweihong.com", description = "邮箱")
    @TableField("EMAIL")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "电话")
    @TableField("TEL")
    private String tel;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
