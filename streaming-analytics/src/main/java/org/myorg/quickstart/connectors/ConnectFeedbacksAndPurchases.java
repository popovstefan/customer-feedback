package org.myorg.quickstart.connectors;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;
import org.myorg.quickstart.entities.CustomerFeedbackSourceObject;
import org.myorg.quickstart.entities.PurchaseSourceObject;

public class ConnectFeedbacksAndPurchases extends KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, Object> {
    @Override
    public void processElement1(PurchaseSourceObject purchaseSourceObject, KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, Object>.Context context, Collector<Object> collector) throws Exception {

    }

    @Override
    public void processElement2(CustomerFeedbackSourceObject customerFeedbackSourceObject, KeyedCoProcessFunction<String, PurchaseSourceObject, CustomerFeedbackSourceObject, Object>.Context context, Collector<Object> collector) throws Exception {

    }

    @Override
    public void open(OpenContext openContext) throws Exception {
        super.open(openContext);
    }
}
