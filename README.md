# Raskelni

A comprehensive platform with web and mobile interfaces built with microservices architecture.

## Project Overview

Raskelni is a full-stack application with three main components:

1. **Back-end (Spring Boot)**: Microservices architecture for robust and scalable server-side functionality
2. **Front-end (React.js)**: Modern web interface for desktop and browser access
3. **Mobile (Flutter)**: Cross-platform mobile application for iOS and Android devices

## Technology Stack

### Back-end (Spring Boot)

- **Java Version**: 11
- **Spring Boot**: 2.6.10
- **Spring Cloud**: 2021.0.5
- **Database**: MySQL
- **Key Dependencies**:
  - Spring Data JPA
  - Spring Security
  - OAuth2 Authentication
  - Eureka Discovery Service
  - API Gateway
  - RabbitMQ for messaging
  - Lombok

### Front-end (React.js)

- **Framework**: React.js
- **Package Manager**: npm/yarn
- **Build Tools**: Webpack (included with React)

### Mobile (Flutter)

- **Framework**: Flutter
- **Platforms**: Android, iOS, Web

## Project Structure

```
Raskelni/
├── Back-end(springBoot)/        # Spring Boot microservices
│   ├── authorization/           # Authentication and authorization service
│   ├── discovery/               # Eureka service discovery
│   ├── gateway/                 # API Gateway
│   ├── notification/            # Notification service
│   └── resources/               # Main business logic and resources
├── Front-end(Reactjs)/          # React.js web application
│   ├── public/                  # Static assets
│   └── src/                     # Source code
└── Mobile/                      # Flutter mobile application
    ├── android/                 # Android-specific code
    ├── ios/                     # iOS-specific code
    └── lib/                     # Main Flutter code
```

## Prerequisites

- **JDK 11**
- **Node.js** (14.x or higher)
- **npm** or **yarn**
- **MySQL** (5.7 or higher)
- **Flutter** (latest stable version)
- **Android Studio** (for mobile development)
- **Xcode** (for iOS development, macOS only)

## Getting Started

### 1. Back-end Setup

1. **Clone the repository**:
   ```bash
   git clone [repository-url]
   cd Raskelni
   ```

2. **Set up MySQL database**:
   - Create a database for the application
   - Update database configuration in each service's `application.properties` or `application.yml` file

3. **Start the services in the following order**:
   ```bash
   # Start Discovery Service first
   cd Back-end\(springBoot\)/discovery
   mvn spring-boot:run
   
   # Start Gateway Service
   cd ../gateway
   mvn spring-boot:run
   
   # Start Authorization Service
   cd ../authorization
   mvn spring-boot:run
   
   # Start Notification Service
   cd ../notification
   mvn spring-boot:run
   
   # Start Resources Service
   cd ../resources
   mvn spring-boot:run
   ```

### 2. Front-end Setup

1. **Navigate to the React.js project**:
   ```bash
   cd Front-end\(Reactjs\)
   ```

2. **Install dependencies**:
   ```bash
   npm install
   # or
   yarn
   ```

3. **Start the development server**:
   ```bash
   npm start
   # or
   yarn start
   ```

4. **Access the web application**:
   - Open your browser and navigate to `http://localhost:3000`

### 3. Mobile App Setup

1. **Navigate to the Flutter project**:
   ```bash
   cd Mobile
   ```

2. **Install dependencies**:
   ```bash
   flutter pub get
   ```

3. **Run the application**:
   ```bash
   # For Android
   flutter run -d android
   
   # For iOS (macOS only)
   flutter run -d ios
   
   # For web
   flutter run -d chrome
   ```

## Configuration

- **Back-end Configuration**: Each microservice has its own configuration in `src/main/resources/application.properties` or `application.yml`
- **Front-end Configuration**: Environment variables can be set in `.env` files
- **Mobile Configuration**: Configuration is managed in `lib/config/` directory

## Deployment

### Back-end Deployment
- Each microservice can be deployed as a standalone Spring Boot application
- Build JAR files using: `mvn clean package`
- Deploy JAR files to your server environment

### Front-end Deployment
- Build the production version: `npm run build` or `yarn build`
- Deploy the contents of the `build` directory to your web server

### Mobile Deployment
- Generate APK for Android: `flutter build apk`
- Generate IPA for iOS: `flutter build ios`
- Follow standard app store submission procedures for publishing

## Additional Information

- **API Documentation**: Available at `http://localhost:8080/swagger-ui.html` when the backend is running
- **Monitoring**: Spring Boot Actuator endpoints are enabled for monitoring service health

## Troubleshooting

- Ensure all microservices are running in the correct order
- Verify database connection details
- Check network connectivity between services
- Inspect logs for detailed error messages

## Contributors

- [Fakhfakh Ahmed]


