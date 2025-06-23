# Equipment Value Calculator

A Java application that calculates market and auction values for equipment based on historical ratios and equipment costs.

## Overview

This application processes equipment data to calculate two types of values:
- **Market Value**: `cost × marketRatio`
- **Auction Value**: `cost × auctionRatio`

The system uses year-specific ratios when available, falling back to default ratios when year-specific data is not found.

## What's Included

- Calculate equipment values for specific years
- Error handling for invalid inputs
- Support for fallback to default ratios
- Unit tests

## Project Structure

```
src/
├── main/
│   ├── java/com/company/
│   │   ├── App.java                           # Main application entry point
│   │   ├── data/                              # Data models
│   │   │   ├── Classification.java
│   │   │   ├── Equipment.java
│   │   │   ├── EquipmentValue.java
│   │   │   ├── Equipments.java
│   │   │   ├── SaleDetails.java
│   │   │   ├── Schedule.java
│   │   │   └── ScheduleYear.java
│   │   ├── service/
│   │   │   ├── EquipmentService.java          # Core business logic
│   │   │   └── provider/
│   │   │       ├── EquipmentExternalAPI.java  # Mock API implementation
│   │   │       └── EquipmentProvider.java     # Provider interface
│   │   └── utils/error/                       # Custom exceptions
│   │       ├── BadRequestException.java
│   │       ├── InvalidDataException.java
│   │       └── NotFoundException.java
│   └── resources/
│       └── mock/
│           └── api-response.json              # Mock equipment data
└── test/
    └── java/com/company/
        ├── data/
        │   └── SerializationDeserializationTest.java  # JSON serialization/deserialization testing
        └── service/
            └── EquipmentServiceTest.java              # Core business testing
```

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Dependencies

- **Jackson**: JSON serialization/deserialization
- **Lombok**: Reducing boilerplate code
- **SLF4J + Logback**: Logging framework
- **JUnit 5**: Unit testing
- **Mockito**: Mocking for tests

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/brunozambiazi/equipment-value.git
cd equipment-value
```

### Build the Project

```bash
mvn clean compile
```

### Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.company.App"
```

Or compile and run directly:

```bash
mvn clean package
java -cp target/classes com.company.App
```

## Usage

The application demonstrates the equipment value calculation with predefined test cases:

- Equipment ID `67352` for year `2007`
- Equipment ID `87964` for year `2011`

### Expected Output

```
...
EquipmentValue(marketValue=216331.27056, auctionValue=126125.1822)
---
Error calculating value for equipment 87964 in year 2011: Equipment 87964 not found
---
```

## Error Handling

The application handles different error scenarios:

- **BadRequestException**: Invalid input parameters _(null ID or year)_
- **NotFoundException**: Equipment ID not found in data source
- **InvalidDataException**: Equipment missing required cost data

## Testing

Run the test suite to verify functionality:

```bash
mvn test
```

Testing includes:
- Unit tests for the service layer with Mockito
- Serialization/deserialization tests for data models
- Error handling verification
