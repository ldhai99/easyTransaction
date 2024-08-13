package io.github.ldhai99.easyTransaction;

public interface EventListener {

    public boolean callback(TransManager event) throws Exception;

}