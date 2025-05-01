package com.empresa.testgen.elements;

import java.util.List;

public class MethodInfo {


    public String name;
    public String params;

    public List<String> getMocks() {
        return mocks;
    }

    public void setMocks(List<String> mocks) {
        this.mocks = mocks;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> mocks;

}