package org.myorg.quickstart.sources;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.myorg.quickstart.entities.CustomerFeedbackSourceObject;

import java.time.Duration;
import java.util.*;

public class CustomerFeedbackSourceFunction extends RichSourceFunction<CustomerFeedbackSourceObject> {
    private transient boolean isRunning;
    private transient Random random;
    private transient List<String> countries;
    private transient List<String> feedbackScores;
    private transient List<String> loyaltyLevels;

    @Override
    public void open(OpenContext openContext) throws Exception {
        super.open(openContext);
        this.isRunning = true;
        this.random = new Random();
        this.countries = Arrays.asList("Canada", "France", "Germany", "UK", "USA");
        this.feedbackScores = Arrays.asList("High", "Low", "Medium");
        this.loyaltyLevels = Arrays.asList("Bronze", "Gold", "Silver");
    }

    @Override
    public void run(SourceContext<CustomerFeedbackSourceObject> sourceContext) throws Exception {
        while (this.isRunning) {
            CustomerFeedbackSourceObject customerFeedbackSourceObject = new CustomerFeedbackSourceObject();
            // make a random customer feedback object
            customerFeedbackSourceObject.setCustomerId("customer" + random.nextInt(5));
            customerFeedbackSourceObject.setPurchaseId("purchase" + random.nextInt(5));
            // todo date of birth, timestamp
            if (random.nextBoolean())
                customerFeedbackSourceObject.setGender("Male");
            else
                customerFeedbackSourceObject.setGender("Female");
            customerFeedbackSourceObject.setCountry(countries.get(random.nextInt(countries.size())));
            customerFeedbackSourceObject.setProductQuality(random.nextInt(100));
            customerFeedbackSourceObject.setServiceQuality(random.nextInt(100));
            customerFeedbackSourceObject.setFeedbackScore(feedbackScores.get(random.nextInt(feedbackScores.size())));
            customerFeedbackSourceObject.setLoyaltyLevel(loyaltyLevels.get(random.nextInt(loyaltyLevels.size())));
            customerFeedbackSourceObject.setCustomerIncome((int) (random.nextFloat() * 100_000));
            sourceContext.collect(customerFeedbackSourceObject);
            Thread.sleep(random.nextInt(5) * 1000);
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
