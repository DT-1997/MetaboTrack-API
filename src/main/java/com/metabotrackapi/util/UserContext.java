package com.metabotrackapi.util;

public class UserContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * Set Current User ID
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * Get Current User ID
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * Remove Current User ID
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}

