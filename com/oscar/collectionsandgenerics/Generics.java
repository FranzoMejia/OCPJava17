package com.oscar.collectionsandgenerics;

import java.util.Collection;
import java.util.List;

public interface Generics {
    Collection<Integer> myMethod(List<?> list);
}
