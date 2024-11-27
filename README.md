# Workshop: "Real-time streaming analytics and machine learning with Flink"

This is a code repository for a workshop titled Real-time streaming analytics and machine learning with Flink in a series of workshops held by the DataScience@UL-FRI Initiative.

## Workshop Details
- **Date and Time:** November 28, 2024, 17:00 - 20:00
- **Location:** UL-FRI, P04
- **Instructors:** Stefan Popov & Domen Požrl

## Instructor Information
- **Stefan Popov:** Data Scientist at Sportradar, Master's degree in Information and Communication Technologies. [LinkedIn Profile](https://www.linkedin.com/in/popovstefan/)
- **Domen Požrl:** Data Scientist at Sportradar, Master's degree in Computer Science. [LinkedIn Profile](https://www.linkedin.com/in/domen-po%C5%BErl-665692326/)

## Event Description
The workshop is primarily aimed at programmers (academics, professionals, students) who are familiar with the basics of machine learning and want to learn how to apply it to a real-time environment. 

You will have the chance to tryout a data science project in Java via working with Apache Flink. Flink is a data streaming framework used in many companies for real-time data transformations. It's open-source, and has strong development community behind it. In this workshop, we'll demonstrate, on a synhetic example, how we can use Flink to wrangle raw data sets, do feature engineering, and make predictions with Python-trained XGboost model, on-the-fly, in real-time, as the data arrives.

## Workshop Agenda
- **17:00 - 17:35:** Introduction to the workshop, problem description, exploratory data analysis, data modeling
- **17:35 - 18:20:** Introduction to Flink: dataflow programming, streaming API
- **18:20 - 18:40:** _Break_
- **18:40 - 19:00:** Moving from batch scenario, to a real-time environment: streaming topology, Flink operators
- **19:00 - 19:20:** Porting the Python-trained XGboost model to Java/Flink
- **19:20 - 19:40:** Tips for improving the solution, hands-on exercises
- **19:40 - 20:00:** Q&A, sharing practical experiences 

## Prerequisites
- Basic familiarity with Java, Python, and machine learning is required, knowledge of Apache Maven (or similar build automation tool) will be helpful, yet is not mandatory.
- Please bring a laptop with installed Java, version >= 1.8 (you'll need a JDK), and Apache Maven, version >= 3.8.6. If you have trouble loading the `streaming-analytics` folder into your IDE (we recommend Intelij), you can follow the official Flink project configuration [documentation](https://nightlies.apache.org/flink/flink-docs-release-1.20/docs/dev/configuration/overview/) to setup your environment. Please come some minutes earlier if you need help setting up your machine.

### Installation guide on Windows 10
#### Should also work very similarly on Windows 11
1. Download and install python >= 3.9 from the [official website.](https://www.python.org/downloads/)
2. Download and install Java from the [official website.](https://www.java.com/en/download/manual.jsp)
3. Download Apache Maven from the [official website](https://maven.apache.org/download.cgi) and follow the rules provided with [this blog post.](https://phoenixnap.com/kb/install-maven-windows)
4. To run the Flink app download and install IntelliJ from [official website](https://www.jetbrains.com/idea/download/?section=windows), make sure to scroll down a bit to the Community Edition
5. To run the Jupyter Notebook either download and install [VS Code](https://code.visualstudio.com/) or [PyCharm Community Edition](https://www.jetbrains.com/pycharm/download/?section=windows) or run the following command in the terminal `python -m notebook` which will start a local jupyter server. 
6. Download this repository to your computer and navigate to it inside the Command Prompt or Windows Power Shell
7. Install python dependancies with `pip install -r requirements.txt`
8. Open the folder `streaming-analytics` as a project inside IntelliJ
9. We use lombok to automatically generate getters, setters and similar bolierplate code so, if IntelliJ is underlining some lines, for instance missing `getCustomerId` method, install Lombok plugin by going to Settings -> Plugins and searching for it. IntelliJ might also already recommend it.
10. On the right side of the IntelliJ window you should see a maven sidebar. Open it up and navigate under the Lifecycle section. There you can first run the `Clean` cycle followed by the `Compile` cycle. After this is done, you should be able to run the code. 


## Software and Packages
- **Required Software:** Java, Maven, Python
- **Python Packages:** `pandas==1.5.3`, `seaborn==0.12.2`, `numpy==1.23.5`, `sklearn==1.2.1`, `matplotlib==3.7.0`, `shap=0.46.0`, `xgboost==2.1.2`, `sklearn2pmml==0.111.1`
- **Java dependencies:** see the `pom.xml` for details

## Resources & Literature
- The data set we'll work with is taken from [Kaggle](https://www.kaggle.com/datasets/jahnavipaliwal/customer-feedback-and-satisfaction/)
- All Flink materials are taken from [Stream Processing with Apache Flink: Fundamentals, Implementation, and Operation of Streaming Applications](https://www.amazon.com/Stream-Processing-Apache-Flink-Implementation/dp/149197429X), chapters/sections: 1, 2, 3a, 4, 5, 8c, 8d, 9a.i, 9a.e.

# Acknowledgements

We are immensely grateful to the Data Science Initiative for their support and for providing us with the opportunity to conduct the "Real-time streaming analytics and machine learning with Flink" workshop. This workshop is part of a valuable series that plays a significant role in advancing our understanding and application of data science. Special thanks to the entire team at the Data Science Initiative for their efforts in organizing and promoting this series, thereby enabling a platform for knowledge sharing and professional growth. Their dedication to fostering a vibrant data science community is truly appreciated.