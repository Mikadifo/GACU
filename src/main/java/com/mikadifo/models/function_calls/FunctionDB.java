package com.mikadifo.models.function_calls;

import java.util.List;

@FunctionalInterface
public interface FunctionDB {

    public List<?> selectAll();

}
