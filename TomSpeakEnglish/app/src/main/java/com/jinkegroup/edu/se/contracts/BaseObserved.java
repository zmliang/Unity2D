package com.jinkegroup.edu.se.contracts;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public interface BaseObserved {

    void addObserver(BaseObserved watcher);
    void removeObserver(BaseObserved watcher);

    void notifyObserver();
}
