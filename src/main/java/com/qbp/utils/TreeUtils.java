package com.qbp.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树操作工具
 */
public class TreeUtils {
    private TreeUtils() {
    }


    /**
     * 树构建
     *
     * @param items             构建集合
     * @param idExtractor       提取节点ID的函数
     * @param parentIdExtractor 提取父节点ID的函数
     * @param childrenSetter    设置子节点列表的函数
     * @param <T>               树节点类型
     * @return 树结构的根节点列表
     */
    public static <T> List<T> buildTree(List<T> items, Function<T, Long> idExtractor, Function<T, Long> parentIdExtractor, Function<T, List<T>> childrenSetter) {
        Map<Long, T> map = new HashMap<>();
        List<T> roots = new ArrayList<>();

        // 初始化map
        for (T item : items) {
            map.put(idExtractor.apply(item), item);
        }

        // 构建树
        for (T item : items) {
            Long parentId = parentIdExtractor.apply(item);
            if (parentId == 0) {
                roots.add(item);
            } else {
                T parent = map.get(parentId);
                if (parent != null) {
                    List<T> children = childrenSetter.apply(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        childrenSetter.apply(parent).addAll(children);
                    }
                    children.add(item);
                }
            }
        }

        return roots;
    }

    /**
     * 列表转换
     *
     * @param sourceList  源对象列表
     * @param targetClass 目标对象的类类型
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象列表
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(source -> {
                    try {
                        T target = targetClass.getDeclaredConstructor().newInstance();
                        BeanUtils.copyProperties(source, target);
                        return target;
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to convert object", e);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 树构建并转换
     *
     * @param sourceList        源对象列表
     * @param targetClass       目标对象的类类型
     * @param idExtractor       提取目标对象ID的函数
     * @param parentIdExtractor 提取目标对象父节点ID的函数
     * @param childrenSetter    设置目标对象子节点列表的函数
     * @param <S>               源对象类型
     * @param <T>               目标对象类型
     * @return 树结构的根节点列表
     */
    public static <S, T> List<T> buildTreeWithConversion(
            List<S> sourceList,
            Class<T> targetClass,
            Function<T, Long> idExtractor,
            Function<T, Long> parentIdExtractor,
            Function<T, List<T>> childrenSetter
    ) {
        List<T> convertedItems = convertList(sourceList, targetClass);

        return buildTree(
                convertedItems,
                idExtractor,
                parentIdExtractor,
                childrenSetter
        );
    }
}
