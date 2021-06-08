package com.wyy.engine.utils;

import net.sf.cglib.beans.BeanCopier;

/**
 * Bean增强类工具类
 *
 * <p>
 * 把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。
 * </p>
 *
 * @since 3.1.2
 */
public class BeanPlusUtil {


    /**
     * 单个对象属性拷贝
     *
     * @param source 源对象
     * @param clazz  目标对象Class
     * @param <T>    目标对象类型
     * @param <M>    源对象类型
     * @return 目标对象
     */
    public static <T, M> T toBean(M source, Class<T> clazz) {
        BeanCopier copier = BeanCopier.create(source.getClass(), clazz, false);
        T t = null;
        try {
            t = clazz.newInstance();
            copier.copy(source, t, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

}
