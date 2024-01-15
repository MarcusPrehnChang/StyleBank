package com.example.stylebank.model;
import com.example.stylebank.model.clothing.Tag;

import java.util.List;
public class CombinedData {
    public List<Tag> tagList;
    public List<String> filter;

    public CombinedData(List<Tag> tags, List<String> filt){
        this.tagList = tags;
        this.filter = filt;
    }

}
