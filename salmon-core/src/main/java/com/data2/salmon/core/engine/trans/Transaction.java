package com.data2.salmon.core.engine.trans;

/**
 * @author data2
 */
public interface Transaction {

    void start();

    void commit();
}
