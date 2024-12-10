package com.qbp.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色菜单表
 *
 * @author lll
 * @since 2024-12-09
 */
@Data
@TableName("menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父类id
     */
    private Long parentId;

    /**
     * 前端名称
     */
    private String webName;

    /**
     * 前端图标
     */
    private String webIcon;

    /**
     * 前端组件路径
     */
    private String wenPath;


}
