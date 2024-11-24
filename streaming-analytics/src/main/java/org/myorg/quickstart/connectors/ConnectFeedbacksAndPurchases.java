package org.myorg.quickstart.connectors;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.state.*;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;
import org.myorg.quickstart.entities.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

public class ConnectFeedbacksAndPurchases extends KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, SatisfactionScorePrediction> {
    private MapState<Long, String> recentPurchases;

    @Override
    public void processElement1(PurchaseSourceObject purchaseSourceObject,
                                KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, SatisfactionScorePrediction>.Context context,
                                Collector<SatisfactionScorePrediction> collector) throws Exception {
        recentPurchases.put(purchaseSourceObject.getTimestamp(), purchaseSourceObject.getPurchaseId());
    }

    @Override
    public void processElement2(CustomerFeedbackSourceObject customerFeedbackSourceObject,
                                KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, SatisfactionScorePrediction>.Context context,
                                Collector<SatisfactionScorePrediction> collector) throws Exception {
        SatisfactionScorePrediction scorePrediction = new SatisfactionScorePrediction();
        scorePrediction.setCustomerId(customerFeedbackSourceObject.getCustomerId());
        ModelInputFeatures modelInputFeatures = new ModelInputFeatures();
        modelInputFeatures.setProductQuality(customerFeedbackSourceObject.getProductQuality());
        modelInputFeatures.setServiceQuality(customerFeedbackSourceObject.getServiceQuality());
        modelInputFeatures.setIncome(customerFeedbackSourceObject.getCustomerIncome());
        // todo calculate age from the date of birth of the customer
        modelInputFeatures.setAge(0);
        Iterator<Map.Entry<Long, String>> iterator = recentPurchases.iterator();
        int purchaseFrequency = 0;
        while (iterator.hasNext()) {
            purchaseFrequency++;
            iterator.next();
        }
        modelInputFeatures.setPurchaseFrequency(purchaseFrequency);
        // todo, fix these placeholders with proper enums, and edit in the source functions
        int genderMale = 0, genderFemale = 0;
        modelInputFeatures.setGenderFemale(genderFemale);
        modelInputFeatures.setGenderMale(genderMale);
        int loyaltyLevelBronze = 0, loyaltyLevelSilver = 0, loyaltyLevelGold = 0;
        modelInputFeatures.setLoyaltyLevelBronze(loyaltyLevelBronze);
        modelInputFeatures.setLoyaltyLevelSilver(loyaltyLevelSilver);
        modelInputFeatures.setLoyaltyLevelGold(loyaltyLevelGold);
        int feedbackScoreLow = 0, feedbackScoreMedium = 0, feedbackScoreHigh = 0;
        modelInputFeatures.setFeedbackScoreLow(feedbackScoreLow);
        modelInputFeatures.setFeedbackScoreMedium(feedbackScoreMedium);
        modelInputFeatures.setFeedbackScoreHigh(feedbackScoreHigh);
        int countryCanada = 0, countryFrance = 0, countryGermany = 0, countryUK = 0, countryUSA = 0;
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
