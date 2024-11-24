package org.myorg.quickstart.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

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
}
