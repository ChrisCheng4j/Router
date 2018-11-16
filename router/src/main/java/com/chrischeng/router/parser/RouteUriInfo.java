package com.chrischeng.router.parser;

import java.util.HashMap;
import java.util.Map;

public final class RouteUriInfo {

    public String route;
    public Map<String, String> params;

    RouteUriInfo(String route, Map<String, String> params) {
        this.route = route;
        this.params = new HashMap<>();
        this.params.putAll(params);
    }
}
