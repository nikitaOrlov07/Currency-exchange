# 🌐 Currency Exchange

## 📋 Project Overview

Currency Exchange is a robust REST application designed to provide real-time currency exchange information and perform conversion operations. Leveraging the Open Exchange Rates API, this application offers up-to-the-minute exchange rates and a suite of currency-related functionalities.

## 🚀 Key Features

### 💱 Currency Conversion
- **Input:** Source currency, target currency, amount
- **Output:** Converted amount

### 🔄 Comparison with Popular Currencies
- **Input:** Source currency, amount
- **Output:** Equivalent amounts in EUR, USD, UAH, RUB

### 📊 Current Exchange Rates
- **Input:** Base currency
- **Output:** Exchange rates relative to all available currencies

### 📅 Historical Exchange Rates
- **Input:** Date
- **Output:** Exchange rates on the specified date

### ℹ️ Currency Information
- **Input:** None
- **Output:** Full names and descriptions of currency codes

## 🛠 Technologies
- ☕ **Java**
- 🍃 **Spring Boot**
- 🌐 **REST API**
- 💹 **Open Exchange Rates API**

## 🧪 Testing
Postman is recommended for testing the application. A Postman collection with sample requests is included in the repository.

## 🔧 Installation and Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/currency-exchange.git
    ```

2. **Navigate to the project directory:**
    ```bash
    cd currency-exchange
    ```

3. **Build the project:**
    ```bash
    ./mvnw clean package
    ```

4. **Run the application:**
    ```bash
    java -jar target/currency-exchange-0.0.1-SNAPSHOT.jar
    ```

## 🔗 API Endpoints

- **GET /currencyExchange/convert:** Currency conversion
- **GET /currencyExchange/compare:** Comparison with popular currencies
- **GET /currencyExchange/exchangeRate:** Current exchange rates
- **GET /currencyExchange/historicalExchangeRate:** Historical exchange rates
- **GET /currencyExchange/currencyInformation:** Currency information


⭐ Feel free to star the repo if you find this project useful!
