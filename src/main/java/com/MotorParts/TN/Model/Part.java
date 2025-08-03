package com.MotorParts.TN.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Parts")

public class Part {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long part_id;
    private String name;
    private String manufacturer;
    private Double stock_price;
    private Integer stockQuantity;
    private Integer minStockLevel;
}
