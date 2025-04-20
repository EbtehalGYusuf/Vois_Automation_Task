
## Contents

There are two folders in this repository each project in folder :

1. VOIS_Youtube: Contains Youtube frontend automation cases

2. VOIS_REST_ASSURED: Contains RESTAPI Test cases
# VOIS Youtube frontend testing

This framework is used to execute test cases on "Youtube" Website


## Framework Design Pattern

The used design pattern is PageObject

Design Pattern:
1. Core Classes -->Contain locators and interactions for ex. any class in core.pageobjects.qpros
2. Base Classes -->Extend core classes having all methods 
3. Test Set Classes:
    a.Test Cases Classes -->Contain implemented test cases 
    b. Configuration Class -->Contains all
    required methods for initializing driver and opening URL for ex. any class in configurations Packet
4. All required dependencies are added in pom.xml
## Parallel Tests

To run test method in parallel the following configs is added to testNG file:
    1. parallel="methods"
    2. Before and After Methods are created thread safe in configurations class that test class extends it

    ________________________________

## Executing Automation Tests and Reports Generation

Run the following command to run code and generate report "mvn clean test allure:serve"
