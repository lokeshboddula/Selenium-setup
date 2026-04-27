# BDD with Cucumber Setup Guide

## 🎯 **What is BDD?**
Behavior Driven Development (BDD) is a collaborative approach to software development that focuses on:
- **Business-readable tests** using natural language
- **Collaboration** between developers, QA, and business stakeholders
- **Executable specifications** that serve as both documentation and tests

## 📋 **What Was Added**

### **1. Dependencies (pom.xml)**
- `cucumber-java`: Core Cucumber functionality
- `cucumber-testng`: TestNG integration
- `cucumber-picocontainer`: Dependency injection

### **2. Feature Files**
```
src/test/resources/features/
└── GoogleSearch.feature    # BDD scenarios in Gherkin
```

### **3. Step Definitions**
```
src/test/java/stepDefinitions/
├── GoogleSearchSteps.java  # Step implementations
└── CucumberHooks.java      # Setup/teardown hooks
```

### **4. Test Runner**
```
src/test/java/runners/
└── CucumberTestRunner.java # Cucumber + TestNG integration
```

### **5. Test Configuration**
- `src/test/cucumber-testng.xml`: Cucumber test suite
- Updated `pom.xml`: Includes both traditional and Cucumber tests

## 🚀 **How to Run BDD Tests**

### **Run All Tests (Traditional + BDD)**
```bash
mvn clean test
```

### **Run Only Cucumber Tests**
```bash
mvn clean test -DsuiteXmlFile=src/test/cucumber-testng.xml
```

### **Run Specific Feature**
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

## 📝 **Writing BDD Tests**

### **1. Feature File Structure**
```gherkin
Feature: Feature Name
  Background:    # Runs before each scenario
    Given setup step

  Scenario: Scenario description
    Given precondition
    When action
    Then verification

  Scenario Outline: Parameterized scenario
    Given precondition with <param>
    When action
    Then verification with <expected>

    Examples:
      | param | expected |
      | value1 | result1 |
```

### **2. Step Definitions**
```java
public class MySteps {
    @Given("I am on the {string} page")
    public void navigateToPage(String page) {
        // Implementation
    }

    @When("I perform {string}")
    public void performAction(String action) {
        // Implementation
    }

    @Then("I should see {string}")
    public void verifyResult(String expected) {
        // Implementation
    }
}
```

## 🔧 **Key Components Explained**

### **CucumberHooks.java**
- **@Before**: Initializes WebDriver and ExtentReports for each scenario
- **@After**: Cleans up WebDriver and flushes reports
- **Dependency Injection**: Provides WebDriver to step definitions

### **CucumberTestRunner.java**
- **@CucumberOptions**: Configures feature files, step definitions, and reporting
- **Parallel Execution**: Can be enabled by setting `parallel = true`

### **GoogleSearch.feature**
- **Gherkin Syntax**: Given/When/Then format
- **Scenario Outline**: Parameterized tests with Examples table
- **Tags**: Can be used to filter tests (`@smoke`, `@regression`)

## 📊 **Reporting**

### **Cucumber Reports**
- **HTML**: `target/cucumber-reports/cucumber.html`
- **JSON**: `target/cucumber-reports/cucumber.json`
- **JUnit**: `target/cucumber-reports/cucumber.xml`

### **ExtentReports Integration**
- All `log.info()` calls are captured in ExtentReports
- Scenario results are automatically logged
- Reports available at `test-output/ExtentReport.html`

## 🏗️ **CI/CD Integration**

### **GitHub Actions**
- Automatically runs both traditional and BDD tests
- Deploys ExtentReports to GitHub Pages

### **Jenkins**
- Pipeline supports both test types
- Reports archived per build

### **Docker**
- Containerized execution works for both test types

## 🎯 **Best Practices**

### **1. Feature Files**
- Keep scenarios simple and focused
- Use descriptive names
- Avoid technical details in Gherkin

### **2. Step Definitions**
- One step definition per action
- Reusable across multiple scenarios
- Include proper logging

### **3. Test Organization**
- Group related features in subdirectories
- Use tags for test filtering
- Maintain separate test suites if needed

### **4. Maintenance**
- Regular review of step definitions
- Remove unused steps
- Keep feature files current with application changes

## 🔄 **Migration Path**

### **From Traditional Tests to BDD**
1. **Identify key user journeys**
2. **Write feature files** for those journeys
3. **Implement step definitions**
4. **Gradually replace** traditional tests
5. **Keep both** running during transition

### **Parallel Execution**
```java
@CucumberOptions(
    // ... other options
    parallel = true  // Enable parallel execution
)
```

## 🐛 **Troubleshooting**

### **Common Issues**
- **Steps not found**: Check glue path in `@CucumberOptions`
- **WebDriver null**: Ensure proper dependency injection
- **Reports not generated**: Check ExtentReports initialization

### **Debug Mode**
```bash
mvn clean test -Dcucumber.options="--plugin pretty --glue stepDefinitions"
```

## 📚 **Next Steps**

1. **Add more features** based on your application
2. **Implement page objects** for better maintainability
3. **Add data-driven testing** with external data sources
4. **Configure parallel execution** for faster test runs
5. **Integrate with test management tools**

Your BDD setup is now complete and ready for collaborative test development! 🚀
