# SACER Dashboard

A Spring Boot application for managing Service Accounts (SA) and Certificates with a modern web interface.

## Features

### Service Account Dashboard
- **Table Display**: Shows SA Name, Dependent Components, Expiry Date, Last Updated Date
- **Password Rotation**: Button to rotate passwords (disabled unless 3 letters + 12 digits entered)
- **Filtering Options**: 
  - Filter by SA Name
  - Filter by Days to Expire (30, 60, 90 days)
- **Mock Data**: 50 service account records loaded every 5 minutes

### Certificate Dashboard
- **Table Display**: Shows Cert Name, Multiple Dependent Components, DN, Expiry Date, Last Updated Date
- **Password Rotation**: Button to rotate passwords (disabled unless 3 letters + 12 digits entered)
- **Filtering Options**:
  - Filter by Certificate Name
  - Filter by Days to Expire (30, 60, 90 days)
- **Mock Data**: 50 certificate records loaded every 5 minutes

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: H2 (In-Memory)
- **Frontend**: Thymeleaf, Bootstrap 5, jQuery
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd sacer
   ```

2. **Build the application**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Main application: http://localhost:8080
   - Service Account Dashboard: http://localhost:8080/sa-dashboard
   - Certificate Dashboard: http://localhost:8080/cert-dashboard
   - H2 Console: http://localhost:8080/h2-console

## Application Structure

```
src/
├── main/
│   ├── java/com/sacer/
│   │   ├── SacerApplication.java          # Main application class
│   │   ├── controller/
│   │   │   ├── DashboardController.java   # Main dashboard routes
│   │   │   ├── ServiceAccountController.java  # SA REST API
│   │   │   └── CertificateController.java     # Certificate REST API
│   │   ├── entity/
│   │   │   ├── ServiceAccount.java        # SA entity
│   │   │   └── Certificate.java           # Certificate entity
│   │   ├── repository/
│   │   │   ├── ServiceAccountRepository.java  # SA repository
│   │   │   └── CertificateRepository.java     # Certificate repository
│   │   └── service/
│   │       ├── ServiceAccountService.java     # SA business logic
│   │       ├── CertificateService.java        # Certificate business logic
│   │       └── DataLoaderService.java         # Mock data loader
│   └── resources/
│       ├── application.properties         # Application configuration
│       └── templates/
│           ├── sa-dashboard.html         # SA dashboard template
│           └── cert-dashboard.html       # Certificate dashboard template
```

## Key Features

### Password Validation
- Passwords must contain exactly 3 letters and 12 digits
- Total length: 15 characters
- Example: `ABC123456789012`

### Data Loading
- Mock data is generated and loaded every 5 minutes
- 50 Service Accounts with random expiry dates
- 50 Certificates with random expiry dates
- Data includes dependent components and realistic DN values

### Filtering
- **Name Filtering**: Search by SA/Certificate name (case-insensitive)
- **Expiry Filtering**: Filter by days to expire (30, 60, 90 days)
- **Real-time Updates**: Data refreshes automatically

### UI Features
- **Responsive Design**: Works on desktop and mobile
- **Color-coded Status**: 
  - Green: Normal (>30 days)
  - Yellow: Warning (≤30 days)
  - Red: Expired
- **Modal Dialogs**: Password rotation with validation
- **Loading States**: Spinner during data loading
- **Alert Messages**: Success/error notifications

## API Endpoints

### Service Accounts
- `GET /api/service-accounts` - Get all service accounts
- `GET /api/service-accounts/search` - Search with filters
- `POST /api/service-accounts/{id}/rotate-password` - Rotate password
- `GET /api/service-accounts/validate-password` - Validate password format

### Certificates
- `GET /api/certificates` - Get all certificates
- `GET /api/certificates/search` - Search with filters
- `POST /api/certificates/{id}/rotate-password` - Rotate password
- `GET /api/certificates/validate-password` - Validate password format

## Configuration

The application uses the following configuration in `application.properties`:

- **H2 Database**: In-memory database with console enabled
- **JPA**: Auto-create tables, show SQL
- **Thymeleaf**: Template caching disabled for development
- **Server**: Running on port 8080

## Development

### Adding New Features
1. Create entity classes in `entity/` package
2. Create repository interfaces in `repository/` package
3. Create service classes in `service/` package
4. Create controller classes in `controller/` package
5. Create Thymeleaf templates in `resources/templates/`

### Database Schema
The application automatically creates the following tables:
- `service_accounts` - Service account data
- `sa_dependent_components` - SA dependent components
- `certificates` - Certificate data
- `cert_dependent_components` - Certificate dependent components

## Troubleshooting

1. **Port already in use**: Change port in `application.properties`
2. **Data not loading**: Check console logs for errors
3. **Password validation not working**: Ensure exactly 3 letters + 12 digits
4. **H2 Console access**: Use credentials from `application.properties`

## License

This project is licensed under the MIT License.
