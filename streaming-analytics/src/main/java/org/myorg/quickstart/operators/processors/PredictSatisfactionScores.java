package org.myorg.quickstart.operators.processors;

import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.myorg.quickstart.entities.CustomerPurchaseHistory;
import org.myorg.quickstart.entities.ModelOutputFeatures;

import java.io.File;
import java.util.Map;

public class PredictSatisfactionScores extends ProcessFunction<CustomerPurchaseHistory, CustomerPurchaseHistory> {
    @Override
    public void processElement(CustomerPurchaseHistory customerPurchaseHistory,
                               ProcessFunction<CustomerPurchaseHistory, CustomerPurchaseHistory>.Context context,
                               Collector<CustomerPurchaseHistory> collector) throws Exception {
        // Building a model evaluator from a PMML file
        Evaluator evaluator = new LoadingModelEvaluatorBuilder()
                .load(new File("../machine-learning-model/myxgboostregressor.pmml"))
                .build();

        // Evaluating the model
        Map<String, Double> arguments = customerPurchaseHistory.getModelInputFeatures().getFeatures();
        Map<String, ?> results = evaluator.evaluate(arguments);

        // Decoupling results from the JPMML-Evaluator runtime environment
        results = EvaluatorUtil.decodeAll(results);


//        System.out.printf("Results: %s%n", results);
        float satisfactionScorePrediction = -1;
        if (results.containsKey("SatisfactionScore"))
            satisfactionScorePrediction = (float) results.get("SatisfactionScore");

        ModelOutputFeatures modelOutputFeatures = new ModelOutputFeatures();
        modelOutputFeatures.setSatisfactionScorePrediction(satisfactionScorePrediction);

        customerPurchaseHistory.setModelOutputFeatures(modelOutputFeatures);
        collector.collect(customerPurchaseHistory);
    }

    @Override
    public void open(OpenContext openContext) throws Exception {
        super.open(openContext);
    }
}
