# ui-testing-java-selenium4-junit5

Sample UI Testing using Java Selenium4 + Junit5


---
## How to run in local machine

## 💻 *Pre-requisites*
- [![Git](https://img.shields.io/badge/-Git-F05032?style=flat&logo=git&logoColor=FFFFFF)](https://git-scm.com/downloads)
- [![Java 8](https://img.shields.io/badge/-Java%208-red?style=flat&logo=java&logoColor=FFFFFF)](https://www.oracle.com/ph/java/technologies/downloads) or higher
- [![Maven](https://img.shields.io/badge/-Maven-C71A36?style=flat&logo=apache-maven&logoColor=FFFFFF)](https://maven.apache.org/download.cgi)
---
## Java Selenium Implementation
### Clone the project
```
git clone https://github.com/kentdomaoal/ui-testing-java-selenium4-junit5.git
```
``` 
cd ui-testing-java-selenium4-junit5
```

### Running the test
``` 
mvn clean test
``` 
---
## Selenium Cucumber Implementation
### Clone the project from branch `selenium-cucumber`
```
git clone -b selenium-cucumber https://github.com/kentdomaoal/ui-testing-java-selenium4-junit5.git ui-testing-selenium-cucumber
```
``` 
cd ui-testing-selenium-cucumber
```

### Running the test
``` 
mvn clean test
``` 
### View html report
- Cucumber Report can be found on this directory on your project root.
    ```
    /target/cucumber-reports/Cucumber.html
    ```
- Moreover, Extended Cucumber report can be found on this directory.
    ```
    /target/cucumber/cucumber-html-reports/overview-features.html
    ```
  
- Alternatively, run below command to generate report using [Cluecumber](https://github.com/trivago/cluecumber/tree/main/maven) plugin
    ``` 
    mvn cluecumber:reporting
    ```
  And report can be found on this directory
    ```
    /target/generated-report/index.html
    ```