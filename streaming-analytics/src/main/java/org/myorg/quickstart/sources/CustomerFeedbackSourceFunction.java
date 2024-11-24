package org.myorg.quickstart.sources;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.myorg.quickstart.entities.CustomerFeedback;

import java.time.Duration;
import java.util.*;

public class CustomerFeedbackSourceFunction extends RichSourceFunction<CustomerFeedback> {
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
    public void run(SourceContext<CustomerFeedback> sourceContext) throws Exception {
        while (this.isRunning) {
            CustomerFeedback customerFeedback = new CustomerFeedback();
            // make a random customer feedback object
            customerFeedback.setCustomerId("customer" + random.nextInt(5));
            customerFeedback.setPurchaseId("purchase" + random.nextInt(5));
            // todo date of birth, timestamp
            if (random.nextBoolean())
                customerFeedback.setGender("Male");
            else
                customerFeedback.setGender("Female");
            customerFeedback.setCountry(countries.get(random.nextInt(0, countries.size() - 1)));
            customerFeedback.setProductQuality(random.nextInt(100));
            customerFeedback.setServiceQuality(random.nextInt(100));
            customerFeedback.setFeedbackScore(feedbackScores.get(random.nextInt(0, feedbackScores.size() - 1)));
            customerFeedback.setLoyaltyLevel(loyaltyLevels.get(random.nextInt(0, loyaltyLevels.size()) - 1));
            customerFeedback.setCustomerIncome((int) (random.nextFloat() * 100_000));
            sourceContext.collect(customerFeedback);
            Thread.sleep(Duration.ofSeconds(this.random.nextInt(5)));
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
