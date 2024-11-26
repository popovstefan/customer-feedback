package org.myorg.quickstart.entities;


import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ModelInputFeatures {
    private double productQuality;
    private double serviceQuality;
    private double income;
    private int age;
    private int purchaseFrequency;
    private int genderFemale;
    private int genderMale;
    private int loyaltyLevelBronze;
    private int loyaltyLevelSilver;
    private int loyaltyLevelGold;
    private int feedbackScoreLow;
    private int feedbackScoreMedium;
    private int feedbackScoreHigh;
    private int countryCanada;
    private int countryFrance;
    private int countryGermany;
    private int countryUK;
    private int countryUSA;

    public Map<String, Double> getFeatures() {
        Map<String, Double> result = new HashMap<>();
        result.put("ProductQuality", productQuality);
        result.put("ServiceQuality", serviceQuality);
        result.put("Income", income);
        result.put("Age", (double) age);
        result.put("PurchaseFrequency", (double) purchaseFrequency);

        result.put("Gender_Male", (double) genderMale);
        result.put("Gender_Female", (double) genderFemale);

        result.put("LoyaltyLevel_Bronze", (double) loyaltyLevelBronze);
        result.put("LoyaltyLevel_Silver", (double) loyaltyLevelSilver);
        result.put("LoyaltyLevel_Gold", (double) loyaltyLevelGold);

        result.put("FeedbackScore_Low", (double) feedbackScoreLow);
        result.put("FeedbackScore_Medium", (double) feedbackScoreMedium);
        result.put("FeedbackScore_High", (double) feedbackScoreHigh);

        result.put("Country_Canada", (double) countryCanada);
        result.put("Country_France", (double) countryFrance);
        result.put("Country_Germany", (double) countryGermany);
        result.put("Country_UK", (double) countryUK);
        result.put("Country_USA", (double) countryUSA);
        return result;
    }
}
