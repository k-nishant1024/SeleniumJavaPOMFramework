Description:

This repository contains a Selenium Java Page Object Model (POM) Framework, designed to facilitate test automation for web applications. The framework utilizes Selenium WebDriver, TestNG, and Maven to create a structured and maintainable test suite. By employing the POM design pattern, it enhances the reusability of test code and improves code readability.

Key Features:

Page Object Model (POM): The framework follows the POM design pattern, where each web page is represented as a separate class. This separation of concerns makes the test code more modular and easy to maintain.

TestNG Integration: TestNG is integrated into the framework to manage test execution and report generation. TestNG allows users to perform parallel test execution, handle dependencies, and generate detailed test reports.

Maven Build Tool: Maven is used as the build tool, enabling easy project setup and dependency management. It simplifies the process of adding external libraries and plugins.

Configuration Management: The framework incorporates a configuration management approach using properties files, which allows users to manage test data and configurations effectively.

Reusable Utility Functions: Commonly used utility functions, such as handling browser windows, capturing screenshots, and waiting for elements, are implemented as reusable methods for ease of use.

Getting Started:

Follow these steps to set up and use the Selenium Java POM Framework:

Prerequisites:

Java Development Kit (JDK) installed on your system.
Maven installed on your system.
An Integrated Development Environment (IDE) like Eclipse or IntelliJ.
Clone the Repository:

Clone this repository to your local machine using the following command:

bash
Copy code
git clone https://github.com/k-nishant1024/SeleniumJavaPOMFramework.git
Import Project:

Import the project into your preferred IDE (Eclipse or IntelliJ) as a Maven project.

Configure Test Data:

Update the test data and configuration details in the config.properties file located in the src/test/resources directory.

Write Test Cases:

Write your test cases using the Page Object Model approach. Create separate Java classes for each web page under test and encapsulate the locators and actions within these classes.

Run Test Suite:

Execute the test suite by running the TestNG XML file (testng.xml) located at the root of the project. TestNG will take care of running the tests and generating the test reports.

Contributing:

If you would like to contribute to this project, please follow these steps:

Fork the repository to your GitHub account.
Create a new branch for your feature/bug fix.
Make your changes and commit them with descriptive commit messages.
Push your changes to your forked repository.
Create a pull request to merge your changes into the main repository.
