# PeerLink

PeerLink is a full-stack peer-to-peer file sharing and collaboration platform built with Spring Boot (Java backend) and Next.js (React frontend).

## Features
- Peer-to-peer file sharing
- Invite code system for secure sharing
- File upload and download
- Modern UI with Tailwind CSS
- Java backend services for file management

## Project Structure
```
PeerLink/
├── src/main/java/p2p/service/        # Java backend services
├── src/main/java/p2p/controller/     # Java backend controllers
├── ui/                               # Next.js frontend
│   ├── src/app/                      # App entry, layout, global styles
│   ├── src/components/               # React components (FileUpload, FileDownload, InviteCode)
│   ├── package.json                  # Frontend dependencies
│   ├── tailwind.config.js            # Tailwind CSS config
│   ├── tsconfig.json                 # TypeScript config
│   └── ...
├── .gitignore                        # Git ignore rules
└── README.md                         # Project documentation
```

## Getting Started

### Backend (Spring Boot)
1. Make sure you have Java 21+ and Maven installed.
2. Build and run the backend:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend (Next.js)
1. Navigate to the `ui` directory:
   ```sh
   cd ui
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Run the development server:
   ```sh
   npm run dev
   ```

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.
