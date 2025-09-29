# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This repository contains code for a workshop titled "Real-time streaming analytics and machine learning with Flink". The project demonstrates how to use Apache Flink for real-time data processing and making predictions with an XGBoost model in a streaming environment.

The project is split into two main components:

1. **machine-learning-model**: Contains a Jupyter notebook for data analysis and model training, along with the exported PMML model file.
2. **streaming-analytics**: A Java/Flink application that uses the exported model to make predictions in real-time.

## Build and Run Commands

### Machine Learning Model

To work with the Python-based machine learning model:

```bash
# Install Python dependencies (as mentioned in README)
pip install pandas==1.5.3 seaborn==0.12.2 numpy==1.23.5 scikit-learn==1.2.1 matplotlib==3.7.0 shap==0.46.0 xgboost==2.1.2 sklearn2pmml==0.111.1

# Open and run the notebook
jupyter notebook machine-learning-model/CustomerFeedback.ipynb
```

### Streaming Analytics (Flink Application)

To build and run the Flink streaming application:

```bash
# Navigate to the streaming-analytics directory
cd streaming-analytics

# Clean the project
mvn clean

# Compile the project
mvn compile

# Package the application into a runnable JAR
mvn package

# Run the application
mvn exec:java -Dexec.mainClass="org.myorg.quickstart.DataStreamJob"

# Alternatively, after packaging, run the JAR directly
java -jar target/flink-streaming-analytics-0.1.jar
```

## Code Architecture

### Machine Learning Pipeline

1. **Data Analysis and Model Training** (`machine-learning-model/CustomerFeedback.ipynb`):
   - Processes customer feedback data
   - Performs feature engineering and data analysis
   - Trains an XGBoost regression model
   - Exports the model to PMML format for use in Java

2. **PMML Model** (`machine-learning-model/myxgboostregressor.pmml`):
   - The serialized XGBoost model in PMML format
   - Used by the Java application for real-time predictions

### Flink Streaming Application

1. **Main Application** (`DataStreamJob.java`):
   - Entry point that sets up the Flink execution environment
   - Configures the data streams and operators
   - Connects the data sources and processing functions
   - Executes the streaming job

2. **Data Sources**:
   - `CustomerFeedbackSourceFunction`: Generates synthetic customer feedback data
   - `PurchaseSourceFunction`: Generates synthetic purchase data

3. **Stream Processing**:
   - `ConnectFeedbacksAndPurchases`: Joins feedback and purchase data by customer ID
   - `PredictSatisfactionScores`: Uses the PMML model to predict customer satisfaction scores

4. **Data Models**:
   - Various entity classes representing the data flowing through the system
   - `ModelInputFeatures`: Features provided to the ML model
   - `ModelOutputFeatures`: Prediction results from the ML model
   - `CustomerPurchaseHistory`: Combined data for a customer

## Tips for Development

- The project uses Lombok to reduce boilerplate code. Make sure you have the Lombok plugin installed in your IDE.
- The `PredictSatisfactionScores` process function loads the PMML model file from a relative path. Make sure the path is correct when running the application from different directories.
- Both data sources generate synthetic data, so there's no need for external data connections when running the example.
- The application demonstrates key Flink concepts such as:
  - Source functions
  - Key-based stream processing
  - State management with TTL (Time-To-Live)
  - Connecting multiple streams
  - Process functions