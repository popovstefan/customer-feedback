package org.myorg.quickstart.sources;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.myorg.quickstart.entities.CustomerFeedback;

import java.time.Duration;

public class CustomerFeedbackSourceFunction implements SourceFunction<CustomerFeedback> {
    private boolean isRunning = true;

    @Override
    public void run(SourceContext<CustomerFeedback> sourceContext) throws Exception {
        while (this.isRunning) {
            sourceContext.collect(new CustomerFeedback());
            Thread.sleep(Duration.ofSeconds(1));
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
