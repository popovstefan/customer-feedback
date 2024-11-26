package org.myorg.quickstart.entities;


import lombok.*;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"customerId", "purchaseId"})
public class CustomerFeedbackSourceObject {
    private String customerId;
    private String purchaseId;
    private long dateOfBirth;
    private long timestamp;
    private String gender;
    private String country;
    private int productQuality;
    private int serviceQuality;
    private String feedbackScore;
    private String loyaltyLevel;
    private int customerIncome;
}
