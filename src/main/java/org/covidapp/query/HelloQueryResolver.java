package org.covidapp.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

@Service
public class HelloQueryResolver implements GraphQLQueryResolver {
    public String hello(String who) {
        return "Hello " + who;
    }
}