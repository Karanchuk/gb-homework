package lesson3.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product {
    private Integer id;
    private String title;
    private int cost;
}
