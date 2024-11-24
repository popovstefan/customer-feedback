package org.myorg.quickstart.sources;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.myorg.quickstart.entities.Purchase;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class PurchaseSourceFunction extends RichSourceFunction<Purchase> {
    private transient boolean isRunning;
    private transient Random random;
    private transient List<String> countries;
    private transient List<String> feedbackScores;
    private transient List<String> loyaltyLevels;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.random = new Random();
    }

    @Override
    public void run(SourceContext<Purchase> sourceContext) throws Exception {
        while (this.isRunning) {
            Purchase purchase = new Purchase();
            // make a random purchase object
            purchase.setPurchaseId("purchase" + random.nextInt(5));
            purchase.setCustomerId("customer" + random.nextInt(5));
            purchase.setPurchaseId("product" + random.nextInt(5));
            // todo timestamp
            purchase.setUnitPrice(random.nextFloat() * 100);
            purchase.setNumUnits(random.nextInt(5));
            sourceContext.collect(purchase);
            Thread.sleep(Duration.ofSeconds(random.nextInt(5)));
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
