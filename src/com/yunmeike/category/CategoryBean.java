package com.yunmeike.category;

import java.io.Serializable;
import java.util.List;

public class CategoryBean implements Serializable {
   String id;
   String name;
   int leve;
   List<CategoryBean> subList;
}
