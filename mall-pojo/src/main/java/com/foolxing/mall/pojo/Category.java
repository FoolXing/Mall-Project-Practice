package com.foolxing.mall.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category implements Serializable {


    /**
     * 类别Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 状态（1：正常 0：停用）
     */
    private Integer status;

    /**
     * 逻辑删除 1 表示删除，0 表示未删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
