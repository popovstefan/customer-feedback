package org.myorg.quickstart.sources;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.myorg.quickstart.entities.Purchase;

import java.time.Duration;

public class PurchaseSourceFunction implements SourceFunction<Purchase> {
    private boolean isRunning = true;

    @Override
    public void run(SourceContext<Purchase> sourceContext) throws Exception {
        while (this.isRunning) {
            sourceContext.collect(new Purchase());
            Thread.sleep(Duration.ofSeconds(1));
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
