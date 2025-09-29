# Docker Instructions

This guide provides instructions on how to build and run the Docker image for the customer feedback Flink application.

## Prerequisites

- Docker installed on your machine
- Docker Hub account (if you want to publish the image)

## Building the Docker Image

To build the Docker image, navigate to the root directory of the repository and run:

```bash
docker build -t customer-feedback-flink .
```

This will create an image named `customer-feedback-flink`.

## Running the Docker Container

To run the application in a Docker container:

```bash
docker run --name customer-feedback-app customer-feedback-flink
```

The application will start processing the synthetic data streams and printing the results to the console.

## Publishing to Docker Hub

To push the image to Docker Hub:

1. Tag the image with your Docker Hub username:
   ```bash
   docker tag customer-feedback-flink YOUR_USERNAME/customer-feedback-flink:latest
   ```

2. Login to Docker Hub:
   ```bash
   docker login
   ```

3. Push the image:
   ```bash
   docker push YOUR_USERNAME/customer-feedback-flink:latest
   ```

## Notes

- The Docker image contains both the compiled Flink application and the XGBoost model.
- The container uses a run script to ensure the model file is accessible at the expected relative path.
- The Flink web UI is exposed on port 8081, though in this standalone application mode it may not be accessible.

## Customization

If you need to modify the application:

1. Make your changes to the source code
2. Rebuild the Docker image
3. Run the new container

For more advanced Flink deployments (e.g., with JobManager and TaskManager separation), you would need to modify the Dockerfile and configuration accordingly.