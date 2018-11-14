package com.chrischeng.router.demo.util;

import com.google.gson.Gson;

public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toJson(Object obj) {
        return obj != null ? gson.toJson(obj) : "";
    }
}
