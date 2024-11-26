package org.myorg.quickstart.entities;

import lombok.*;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"purchaseId", "customerId", "productId"})
public class PurchaseSourceObject {
    private String purchaseId;
    private String customerId;
    private String productId;
    private long timestamp;
    private double unitPrice;
    private int numUnits;
}
