package com.qbp.model.vo;

import com.qbp.model.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuTreeVO extends Menu {
    private List<MenuTreeVO> children;
}
