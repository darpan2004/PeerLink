
# PeerLink – Secure Peer Sharing

PeerLink is a CLI-based peer-to-peer application built with Java, Spring Boot, and Maven. It enables secure file sharing and real-time chat between peers, supporting up to 10 concurrent sessions and transfers up to 1GB.

## Features
- CLI-based peer-to-peer file sharing and chat
- Supports up to 10 concurrent secure file transfer and chat sessions
- Layered architecture for session management, file handling, and messaging
- RESTful endpoints for peer discovery, session setup, file exchange, and real-time text communication
- Secure transfers up to 1GB

## Project Structure
```
PeerLink/
├── src/main/java/p2p/service/        # Java backend services (file sharing, session management, messaging)
├── src/main/java/p2p/controller/     # Java backend controllers (REST endpoints)
├── uploads/                          # Uploaded/shared files
├── .gitignore                        # Git ignore rules
└── README.md                         # Project documentation
```

## Getting Started

### Prerequisites
- Java 21+
- Maven

### Build and Run
1. Build the project:
   ```sh
   mvn clean install
   ```
2. Run the CLI application:
   ```sh
   mvn spring-boot:run
   ```

## License
This project is licensed under the MIT License.
