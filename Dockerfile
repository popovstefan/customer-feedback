# Use maven image to build the application
FROM maven:3.8.6-openjdk-8-slim AS build

# Set working directory
WORKDIR /app

# Copy the model file
COPY machine-learning-model/myxgboostregressor.pmml /app/model/myxgboostregressor.pmml

# Copy the source code
COPY streaming-analytics /app/streaming-analytics

# Build the application
WORKDIR /app/streaming-analytics
RUN mvn clean package -DskipTests

# Use Java image for the runtime environment
FROM openjdk:8-slim

# Set working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/streaming-analytics/target/flink-streaming-analytics-0.1.jar /app/flink-streaming-analytics.jar

# Copy the model file
COPY --from=build /app/model /app/model

# Add a run script to handle the relative path issue in the code
RUN echo '#!/bin/bash\n\
# Create a symbolic link to make the relative path work\n\
mkdir -p ../machine-learning-model\n\
ln -sf /app/model/myxgboostregressor.pmml ../machine-learning-model/myxgboostregressor.pmml\n\
# Run the application\n\
java -jar /app/flink-streaming-analytics.jar\n\
' > /app/run.sh && chmod +x /app/run.sh

# Expose Flink web UI port (optional)
EXPOSE 8081

# Set the entrypoint
ENTRYPOINT ["/app/run.sh"]