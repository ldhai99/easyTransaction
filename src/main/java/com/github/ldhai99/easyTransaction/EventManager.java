package com.github.ldhai99.easyTransaction;

import java.util.*;

public abstract class EventManager {


    protected Map<String, LinkedList<EventListener>> eventListenerMap = new HashMap<String, LinkedList<EventListener>>();
    EventObject event;

    // 构成函数
    public EventManager() {

    }

    //触发缺省事件
    public boolean handle() {
        return handle(new EventObject());
    }

    // 触发事件处理
    public boolean handle(EventObject event) {

        this.event =  event;

        String eventName = event.getEventName();

        // 找不到事件，子类可以注册缺省
        if (!eventListenerMap.containsKey(eventName)) {
            addDefaltListener();
        }

        // 找到事件，执行
        if (eventListenerMap.containsKey(eventName)) {
            List<EventListener> eventListenerList = eventListenerMap.get(eventName);
            return handle(eventListenerList);
        }
        // 找不到事件，报错
        else {

            System.out.println("No event handler listen this event: " + eventName);

            event.setMessage("无此" + eventName + "事件");
            event.setSuccess(false);

            return false;
        }

    }

    // 处理服务开始
    public abstract  boolean handle(List<EventListener> listeners);

    // 注册缺省监听
    public EventManager addDefaltListener() {
        return  this;
    }
    // 注册缺省事件处理
    public EventManager addListener(EventListener listener) {
        return  add(Constant.DEFAULT_Event,listener);
    }
    public EventManager add(EventListener listener) {
        return  add(Constant.DEFAULT_Event,listener);
    }
    public EventManager addFirst(EventListener listener) {
        return  addFirst(Constant.DEFAULT_Event,listener);
    }
    public EventManager addLast(EventListener listener) {
        return  addLast(Constant.DEFAULT_Event,listener);
    }
    // 注册事件处理
    public EventManager add(String eventName, EventListener listener) {
        if (!eventListenerMap.containsKey(eventName)) {
            LinkedList<EventListener> eventListenerList = new LinkedList<EventListener>();
            eventListenerMap.put(eventName, eventListenerList);
        }
        eventListenerMap.get(eventName).add(listener);
        return  this;
    }
    // 注册事件处理为第一个
    public EventManager addFirst(String eventName, EventListener listener) {
        if (!eventListenerMap.containsKey(eventName)) {
            LinkedList<EventListener> eventListenerList = new LinkedList<EventListener>();
            eventListenerMap.put(eventName, eventListenerList);
        }
        eventListenerMap.get(eventName).addFirst(listener);
        return  this;
    }
    // 注册事件处理为最后一个
    public EventManager addLast(String eventName, EventListener listener) {
        if (!eventListenerMap.containsKey(eventName)) {
            LinkedList<EventListener> eventListenerList = new LinkedList<EventListener>();
            eventListenerMap.put(eventName, eventListenerList);
        }
        eventListenerMap.get(eventName).addLast(listener);
        return  this;
    }
}
