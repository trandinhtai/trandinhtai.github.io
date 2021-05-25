package com.example.vonline.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckPromotion {
    private int discountType;

    private long discountValue;

    private long maximumDiscountValue;
}
