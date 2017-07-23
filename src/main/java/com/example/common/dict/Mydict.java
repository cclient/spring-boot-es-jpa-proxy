package com.example.common.dict;

import java.util.List;


public interface Mydict {
    Boolean saveDictToFile(List<String> dics, String path);

    List<String> LoadDictFromFile(String path);

    List<String> LoadDictFromFile();
}
