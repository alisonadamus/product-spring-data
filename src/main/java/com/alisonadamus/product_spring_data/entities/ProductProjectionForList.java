package com.alisonadamus.product_spring_data.entities;

import java.math.BigDecimal;

public interface ProductProjectionForList {

    Long getId();

    String getName();

    BigDecimal getCost();

    String getCategoryName();
}
