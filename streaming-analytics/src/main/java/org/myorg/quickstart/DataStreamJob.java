/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myorg.quickstart;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.myorg.quickstart.operators.connectors.ConnectFeedbacksAndPurchases;
import org.myorg.quickstart.entities.CustomerFeedbackSourceObject;
import org.myorg.quickstart.entities.PurchaseSourceObject;
import org.myorg.quickstart.entities.CustomerPurchaseHistory;
import org.myorg.quickstart.operators.processors.PredictSatisfactionScores;
import org.myorg.quickstart.operators.sources.CustomerFeedbackSourceFunction;
import org.myorg.quickstart.operators.sources.PurchaseSourceFunction;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        // Sets up the execution environment, which is the main entry point
        // to building Flink applications.
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // declare the SourceFunctions
        SourceFunction<PurchaseSourceObject> purchaseSourceFunction = new PurchaseSourceFunction();
        SourceFunction<CustomerFeedbackSourceObject> customerFeedbackSourceFunction = new CustomerFeedbackSourceFunction();

        // add them to the execution environment, which turns them into data streams
        DataStream<PurchaseSourceObject> purchaseDataStream = env.addSource(purchaseSourceFunction);
        DataStream<CustomerFeedbackSourceObject> customerFeedbackDataStream = env.addSource(customerFeedbackSourceFunction);

        // key-by, then connect the two streams, and process them
        DataStream<CustomerPurchaseHistory> customerPurchaseHistoryDataStream = purchaseDataStream
                .keyBy(PurchaseSourceObject::getCustomerId)
                .connect(customerFeedbackDataStream.keyBy(CustomerFeedbackSourceObject::getCustomerId))
                .process(new ConnectFeedbacksAndPurchases())
                .name(ConnectFeedbacksAndPurchases.class.getName())
                .uid(ConnectFeedbacksAndPurchases.class.getName());

        // make predictions on the customers' purchase histories
        DataStream<CustomerPurchaseHistory> satisfactionScores = customerPurchaseHistoryDataStream
                .process(new PredictSatisfactionScores())
                .name(PredictSatisfactionScores.class.getName())
                .uid(PredictSatisfactionScores.class.getName());

        satisfactionScores.print();

        // Execute program, beginning computation.
        env.execute("Customer Satisfaction Score Prediction");
    }
}
