package com.crossover.techtrial.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class MemberTest {

    private static final Class<Member> memberClass = Member.class;

    @Test
    public void test() {
        Assertions.assertPojoMethodsFor(memberClass).testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER).areWellImplemented();


    }
}
