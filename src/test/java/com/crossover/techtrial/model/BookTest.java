package com.crossover.techtrial.model;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class BookTest {

    private static final Class<Book> bookClass = Book.class;

    @Test
    public void test() {
        Assertions.assertPojoMethodsFor(bookClass).testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER).areWellImplemented();
        Assertions.assertPojoMethodsFor(bookClass).testing(Method.HASH_CODE).areWellImplemented();
        Assertions.assertPojoMethodsFor(bookClass).testing(Method.EQUALS).areWellImplemented();

    }

}
