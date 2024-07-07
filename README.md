🌐 Currency Exchange
📋 Project Overview
Currency Exchange is a robust REST application designed to provide real-time currency exchange information and perform conversion operations. Leveraging the Open Exchange Rates API, this application offers up-to-the-minute exchange rates and a suite of currency-related functionalities.
🚀 Key Features

💱 Currency Conversion

Input: source currency, target currency, amount
Output: converted amount


🔄 Comparison with Popular Currencies

Input: source currency, amount
Output: equivalent amounts in EUR, USD, UAH, RUB


📊 Current Exchange Rates

Input: base currency
Output: exchange rates relative to all available currencies


📅 Historical Exchange Rates

Input: date
Output: exchange rates on the specified date


ℹ️ Currency Information

Input: none
Output: full names and descriptions of currency codes



🛠 Technologies

☕ Java
🍃 Spring Boot
🌐 REST API
💹 Open Exchange Rates API

🧪 Testing
Postman is recommended for testing the application. A Postman collection with sample requests is included in the repository.
🔧 Installation and Setup

Clone the repository:
bashCopygit clone https://github.com/yourusername/currency-exchange.git

Navigate to the project directory:
bashCopycd currency-exchange

Build the project:
bashCopy./mvnw clean package

Run the application:
bashCopyjava -jar target/currency-exchange-0.0.1-SNAPSHOT.jar


🔗 API Endpoints

GET /currencyExchange/convert: Currency conversion
GET /currencyExchange/compare: Comparison with popular currencies
GET /currencyExchange/exchangeRate: Current exchange rates
GET /currencyExchange/historicalExchangeRate: Historical exchange rates
GET /currencyExchange/currencyInformation: Currency information

🤝 Contributing
We welcome contributions to the project! Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.
📄 License
This project is licensed under the MIT License.
🙏 Acknowledgements

Open Exchange Rates for providing the currency exchange rate data
All contributors who have helped shape this project


⭐ Feel free to star the repo if you find this project useful!
