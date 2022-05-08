package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {
    public String get(String name) {
        return "Hello there, " + name;
    }
}
