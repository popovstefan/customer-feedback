package org.myorg.quickstart.operators.connectors;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;
import org.myorg.quickstart.entities.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

public class ConnectFeedbacksAndPurchases extends KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, CustomerPurchaseHistory> {
    private MapState<Long, String> recentPurchases;

    @Override
    public void processElement1(PurchaseSourceObject purchaseSourceObject,
                                KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, CustomerPurchaseHistory>.Context context,
                                Collector<CustomerPurchaseHistory> collector) throws Exception {
        recentPurchases.put(purchaseSourceObject.getTimestamp(), purchaseSourceObject.getPurchaseId());
    }

    @Override
    public void processElement2(CustomerFeedbackSourceObject customerFeedbackSourceObject,
                                KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, CustomerPurchaseHistory>.Context context,
                                Collector<CustomerPurchaseHistory> collector) throws Exception {
        CustomerPurchaseHistory scorePrediction = new CustomerPurchaseHistory();
        scorePrediction.setCustomerId(customerFeedbackSourceObject.getCustomerId());
        ModelInputFeatures modelInputFeatures = new ModelInputFeatures();
        modelInputFeatures.setProductQuality(customerFeedbackSourceObject.getProductQuality());
        modelInputFeatures.setServiceQuality(customerFeedbackSourceObject.getServiceQuality());
        modelInputFeatures.setIncome(customerFeedbackSourceObject.getCustomerIncome());
        modelInputFeatures.setAge(customerFeedbackSourceObject.getAge());
        Iterator<Map.Entry<Long, String>> iterator = recentPurchases.iterator();
        int purchaseFrequency = 0;
        while (iterator.hasNext()) {
            purchaseFrequency++;
            iterator.next();
        }
        modelInputFeatures.setPurchaseFrequency(purchaseFrequency);
        int genderMale = 0, genderFemale = 0;
        if (customerFeedbackSourceObject.getGender().equals("Male"))
            genderMale = 1;
        if (customerFeedbackSourceObject.getGender().equals("Female"))
            genderFemale = 1;
        modelInputFeatures.setGenderFemale(genderFemale);
        modelInputFeatures.setGenderMale(genderMale);
        int loyaltyLevelBronze = 0, loyaltyLevelSilver = 0, loyaltyLevelGold = 0;
        if (customerFeedbackSourceObject.getLoyaltyLevel().equals("Bronze"))
            loyaltyLevelBronze = 1;
        if (customerFeedbackSourceObject.getLoyaltyLevel().equals("Silver"))
            loyaltyLevelSilver = 1;
        if (customerFeedbackSourceObject.getLoyaltyLevel().equals("Gold"))
            loyaltyLevelGold = 1;
        modelInputFeatures.setLoyaltyLevelBronze(loyaltyLevelBronze);
        modelInputFeatures.setLoyaltyLevelSilver(loyaltyLevelSilver);
        modelInputFeatures.setLoyaltyLevelGold(loyaltyLevelGold);
        int feedbackScoreLow = 0, feedbackScoreMedium = 0, feedbackScoreHigh = 0;
        if (customerFeedbackSourceObject.getFeedbackScore().equals("Low"))
            feedbackScoreLow = 1;
        if (customerFeedbackSourceObject.getFeedbackScore().equals("Medium"))
            feedbackScoreMedium = 1;
        if (customerFeedbackSourceObject.getFeedbackScore().equals("High"))
            feedbackScoreHigh = 1;
        modelInputFeatures.setFeedbackScoreLow(feedbackScoreLow);
        modelInputFeatures.setFeedbackScoreMedium(feedbackScoreMedium);
        modelInputFeatures.setFeedbackScoreHigh(feedbackScoreHigh);
        int countryCanada = 0, countryFrance = 0, countryGermany = 0, countryUK = 0, countryUSA = 0;
        if (customerFeedbackSourceObject.getCountry().equals("Canada"))
            countryCanada = 1;
        if (customerFeedbackSourceObject.getCountry().equals("Germany"))
            countryGermany = 1;
        if (customerFeedbackSourceObject.getCountry().equals("France"))
            countryFrance = 1;
        if (customerFeedbackSourceObject.getCountry().equals("UK"))
            countryUK = 1;
        if (customerFeedbackSourceObject.getCountry().equals("USA"))
            countryUSA = 1;
        modelInputFeatures.setCountryCanada(countryCanada);
        modelInputFeatures.setCountryFrance(countryFrance);
        modelInputFeatures.setCountryGermany(countryGermany);
        modelInputFeatures.setCountryUK(countryUK);
        modelInputFeatures.setCountryUSA(countryUSA);

        scorePrediction.setModelInputFeatures(modelInputFeatures);

        // set a default model output object
        ModelOutputFeatures modelOutputFeatures = new ModelOutputFeatures();
        modelOutputFeatures.setSatisfactionScorePrediction(-1);

        scorePrediction.setModelOutputFeatures(modelOutputFeatures);

        collector.collect(scorePrediction);
    }

    @Override
    public void open(OpenContext openContext) throws Exception {
        StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(Duration.ofDays(14))
                .updateTtlOnCreateAndWrite()
                .neverReturnExpired()
                .build();
        MapStateDescriptor<Long, String> stateDescriptor = new MapStateDescriptor<>("RecentPurchases", TypeInformation.of(Long.class), TypeInformation.of(String.class));
        stateDescriptor.enableTimeToLive(ttlConfig);
        recentPurchases = this.getRuntimeContext().getMapState(stateDescriptor);
    }
}
