package com.chrischeng.router.parser;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RouteUriParser {

    public static RouteUriInfo parse(Uri uri) {
        return new RouteUriInfo(parseRoute(uri), parseParams(uri));
    }

    private static String parseRoute(Uri uri) {
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getScheme());
        sb.append("://");
        sb.append(uri.getHost());
        sb.append(uri.getPath());

        return sb.toString();
    }

    private static Map<String, String> parseParams(Uri uri) {
        Map<String, String> params = new HashMap<>();

        Set<String> paramNames = uri.getQueryParameterNames();
        if (!paramNames.isEmpty()) {
            for (String paramName : paramNames)
                params.put(paramName, uri.getQueryParameter(paramName));
        }

        return params;
    }
}
