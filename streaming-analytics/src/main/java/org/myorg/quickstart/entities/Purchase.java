package org.myorg.quickstart.entities;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"purchaseId", "customerId", "productId"})
public class Purchase {
    private String purchaseId;
    private String customerId;
    private String productId;
    private long timestamp;
    private double unitPrice;
    private int numUnits;
}
