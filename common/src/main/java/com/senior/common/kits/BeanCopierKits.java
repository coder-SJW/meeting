/*
 * @(#) BeanCopierKits.java 2020-11-14
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.common.kits;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * Bean复制工具 <br/>
 * <p>
 * 目前Spring的BeanUtils
 *
 * @author senior
 */
@Slf4j
public class BeanCopierKits {
    /**
     * Bean 复制
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     */
    public static <S, T> void copyProperties(S source, T target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }

    /**
     * Bean 复制
     *
     * @param source
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyProperties(S source, Class<T> targetClazz) {
        return copyProperties(source, targetClazz, null);
    }

    /**
     * Bean 复制
     *
     * @param source
     * @param targetClazz
     * @param consumer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyProperties(S source, Class<T> targetClazz, BiConsumer<S, T> consumer) {
        if (source == null) {
            return null;
        }
        try {
            Preconditions.checkArgument(targetClazz != null);
            T target = targetClazz.newInstance();
            copyProperties(source, target);
            if (consumer != null) {
                consumer.accept(source, target);
            }
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Bean 复制
     *
     * @param sources
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyProperties(List<S> sources, Class<T> targetClazz) {
        if (sources == null) {
            return null;
        }
        return sources.stream().map(source -> copyProperties(source, targetClazz, null)).collect(Collectors.toList());
    }

    /**
     * Bean 复制
     *
     * @param sources
     * @param targetClazz
     * @param consumer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyProperties(List<S> sources, Class<T> targetClazz, BiConsumer<S, T> consumer) {
        if (sources == null) {
            return null;
        }
        return sources.stream().map(source -> copyProperties(source, targetClazz, consumer))
                .collect(Collectors.toList());
    }

    /**
     * Bean 复制
     *
     * @param sources
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Set<T> copyProperties(Set<S> sources, Class<T> targetClazz) {
        if (sources == null) {
            return null;
        }
        return sources.stream().map(source -> copyProperties(source, targetClazz, null)).collect(Collectors.toSet());
    }

    /**
     * Bean 复制
     *
     * @param sources
     * @param targetClazz
     * @param consumer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Set<T> copyProperties(Set<S> sources, Class<T> targetClazz, BiConsumer<S, T> consumer) {
        if (sources == null) {
            return null;
        }
        return sources.stream().map(source -> copyProperties(source, targetClazz, consumer))
                .collect(Collectors.toSet());
    }

    /**
     * 获取复制帮助类
     *
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> BeanCopyHelper<S, T> copy(Class<T> targetClazz) {
        return new BeanCopierKits.BeanCopyHelper<S, T>().targetClazz(targetClazz);
    }

    /**
     * 生成key
     *
     * @param source
     * @param target
     * @return
     */
    private static String generateKey(Class<?> source, Class<?> target) {
        return source.toString() + "-" + target.toString();
    }

    /**
     * 复制帮助类
     *
     * @param <S>
     * @param <T>
     */
    public static class BeanCopyHelper<S, T> {
        /**
         * 复制完的操作
         */
        private BiConsumer<S, T> after;

        /**
         * 目标类型
         */
        private Class<T> targetClazz;

        /**
         * 设置复制完的操作
         *
         * @param after
         * @return
         */
        public BeanCopyHelper<S, T> after(BiConsumer<S, T> after) {
            this.after = after;
            return this;
        }

        /**
         * 设置目标类型
         *
         * @param targetClazz
         * @return
         */
        BeanCopyHelper<S, T> targetClazz(Class<T> targetClazz) {
            this.targetClazz = targetClazz;
            return this;
        }

        /**
         * 复制对象
         *
         * @param source
         * @return
         */
        public T from(S source) {
            return BeanCopierKits.copyProperties(source, targetClazz, after);
        }

        /**
         * 复制Set
         *
         * @param sources
         * @return
         */
        public Set<T> fromSet(Set<S> sources) {
            return BeanCopierKits.copyProperties(sources, targetClazz, after);
        }

        /**
         * 复制List
         *
         * @param sources
         * @return
         */
        public List<T> fromList(List<S> sources) {
            return BeanCopierKits.copyProperties(sources, targetClazz, after);
        }
    }
}
