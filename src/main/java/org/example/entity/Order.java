package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

    private int id;
    private String title;
    private String price;
    private int amount;
    private int buyer_id;
    private Buyer buyer;

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

}
