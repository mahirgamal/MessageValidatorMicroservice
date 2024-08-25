
# Message Validator Microservice

## Overview

The `MessageValidatorMicroservice` project is a Java-based microservice designed for validating messages related to livestock events. It ensures that data conforms to the standards and formats required for effective data exchange within the livestock industry. This microservice is part of the Livestock Event Information Sharing Architecture (LEISA), facilitating reliable and standardized data validation and sharing among stakeholders.

## Related Projects

- [LEI Schema](https://github.com/mahirgamal/LEI-schema): Defines the standardized schema for livestock event information.
- [LEISA](https://github.com/mahirgamal/LEISA): The architecture framework for sharing livestock event information.
- [LEI2JSON](https://github.com/mahirgamal/LEI2JSON): A tool to convert LEI data into JSON format for easy processing.
- [AgriVet Treatment Grapher](https://github.com/mahirgamal/AgriVet-Treatment-Grapher): A Python-based tool designed to visualize treatment data for animals, helping veterinarians and researchers analyze treatment patterns and dosages.
- [Cattle Location Monitor](https://github.com/mahirgamal/Cattle-Location-Monitor): A system that monitors cattle location using GPS data to provide real-time insights into cattle movements and positioning.

## Features

- **Event Validation**: Validates livestock event data to ensure compliance with industry standards.
- **JSON Schema Validation**: Uses predefined JSON schemas to validate incoming data formats.
- **Error Handling**: Provides detailed error messages for invalid data to facilitate troubleshooting.

## Architecture

The application follows a modular architecture, aligning with Domain-Driven Design (DDD) principles:

### 1. Function Layer (`com.function`)

- **Purpose**: This layer acts as the Application Layer in DDD. It orchestrates the validation process by managing requests and invoking the appropriate domain services.

- **Components**:
  - **`Function.java`**: Contains core functions to handle incoming validation requests and manage the workflow.
  - **`ValidationRequest.java`**: Handles the validation of incoming messages, acting as a service that processes input data and routes it to the appropriate validation components.

### 2. Domain Layer (`com.domain`)

- **Purpose**: This layer contains the business logic for message validation, ensuring that all data complies with the standards required for livestock event information.

- **Components**:
  - **`MessageValidator.java`**: Implements the core validation logic. It checks the messages against predefined schemas and business rules.
  - **`SchemaLoader.java`**: Loads the necessary JSON schemas used for validating the event data.

## Project Structure

```
/MessageValidatorMicroservice
│
├── .git                      # Git configuration directory
├── .gitignore                # Git ignore file
├── pom.xml                   # Project Object Model file for Maven
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       ├── domain
│   │   │       │   ├── MessageValidator.java     # Handles message validation logic
│   │   │       │   └── SchemaLoader.java         # Loads JSON schemas for validation
│   │   │       │
│   │   │       └── function
│   │   │           ├── Function.java             # Core utility functions
│   │   │           └── ValidationRequest.java    # Handles validation requests
│   │   │
│   │   └── resources
│   │       ├── schemas                          # Directory containing JSON schemas
│   │       └── application.properties            # Configuration properties
│   │
│   └── test
│       └── java
│           └── com
│               ├── domain
│               │   ├── MessageValidatorTest.java # Unit tests for MessageValidator
│               │   └── SchemaLoaderTest.java     # Unit tests for SchemaLoader
│               └── function
│                   ├── FunctionTest.java         # Unit tests for Function
│                   └── ValidationRequestTest.java # Unit tests for ValidationRequest
│
└── target                      # Directory for compiled classes and build artifacts
```

## Requirements

- **Java 8** or higher
- **Maven** for building the project and managing dependencies

## Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/MessageValidatorMicroservice.git
   ```
2. **Navigate to the Project Directory:**
   ```bash
   cd MessageValidatorMicroservice
   ```
3. **Build the Project using Maven:**
   ```bash
   mvn clean install
   ```
4. **Run the Application:**
   ```bash
   java -jar target/MessageValidatorMicroservice.jar
   ```

## Usage

1. **Message Validation:**
   - Use the `MessageValidator` class to validate incoming livestock event data.
   - Example usage:
     ```java
     MessageValidator.validate(schemaFilePath, jsonMessage);
     ```

2. **Error Handling:**
   - The microservice provides detailed error reports for invalid messages, which can be used to troubleshoot and correct data issues.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Acknowledgments

This work originates from the Trakka project and builds on the existing TerraCipher Trakka implementation. We appreciate the support and resources provided by the Trakka project team. Special thanks to Dave Swain and Will Swain from TerraCipher for their guidance and assistance throughout this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/yourusername/MessageValidatorMicroservice/blob/main/LICENSE) file for details.

## Contact

If you have any questions, suggestions, or need assistance, please don't hesitate to contact us at [mhabib@csu.edu.au](mailto:mhabib@csu.edu.au) or [akabir@csu.edu.au](mailto:akabir@csu.edu.au).
