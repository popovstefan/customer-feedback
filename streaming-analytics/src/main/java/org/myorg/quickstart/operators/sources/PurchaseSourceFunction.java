package org.myorg.quickstart.operators.sources;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.myorg.quickstart.entities.PurchaseSourceObject;

import java.util.List;
import java.util.Random;

public class PurchaseSourceFunction extends RichSourceFunction<PurchaseSourceObject> {
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
    }

    @Override
    public void run(SourceContext<PurchaseSourceObject> sourceContext) throws Exception {
        while (this.isRunning) {
            PurchaseSourceObject purchaseSourceObject = new PurchaseSourceObject();
            // make a random purchase object
            purchaseSourceObject.setPurchaseId("purchase" + random.nextInt(5));
            purchaseSourceObject.setCustomerId("customer" + random.nextInt(5));
            purchaseSourceObject.setPurchaseId("product" + random.nextInt(5));
            // todo timestamp
            purchaseSourceObject.setUnitPrice(random.nextFloat() * 100);
            purchaseSourceObject.setNumUnits(random.nextInt(5));
            sourceContext.collect(purchaseSourceObject);
            Thread.sleep(random.nextInt(5) * 1000);
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
