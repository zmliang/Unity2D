package com.jinkegroup.edu.se.contracts;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

//观察者
public interface BaseObserver<T> {
    void update(T t);
}
