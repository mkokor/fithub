<p align="center">
  <img width='500px' height='170px' src='logo.png' alt='icon' style='margin: auto;'>
</p>
<h1 align='center'>Fithub</h1>

# 🏋️‍♂️ What is FitHub

FitHub is a full-stack application developed by students of the Faculty of Electrical Engineering in Sarajevo:

- https://github.com/mkokor
- https://github.com/nkokor

The application was developed as part of the Advanced Web Technologies course with the idea of facilitating gym management.
Spring Boot was used for the backend and React for the frontend.

# 🦾 Features
In addition to the basic user registration functionality, FitHub includes:

- Online trainer selection
- Viewing and accessing the latest news and offers provided by the gym
- Viewing personalized weekly meal plans and downloading them as PDF documents (for clients)
- Updating weekly meal plans (for trainers)
- Viewing group training schedules and registering for available sessions (for clients)
- Submitting music suggestions for sessions (for clients)
- Viewing the most successful clients
- Accessing personal results and downloading progress history as an Excel document (for clients)
- Updating client results (for trainers)
- Updating membership payment records (for trainers)
- A real-time chat platform where users can communicate with their trainer and other clients

# 🔧 Installation
1. Download the project or clone the repository using the following commands:

```
gh repo clone mkokor/fithub
```


The project can be started in two ways, via Docker or manually. Since CPU load when running via Docker is extremely high (over 250%), it is recommended not to start the application this way, but rather manually, which will be described below.

First, start the backend project according to the following instructions:
1. Import the backend project into your development environment.
2. Ensure empty databases with the following names on port 3306: fithub_auth, fithub_chat, fithub_membership, fithub_mealplan, fithub_training.
3. In the auth-service under src/main/resources, add an email.properties file, and in the training-service under src/main/resources, add a spotify-api.properties file.

   This is necessary to enable sending codes to users' emails and communication with the Spotify API, which is used for music suggestions. From the code, you can infer which fields need to be added.
5. For each subproject, perform a series of actions: Maven clean -> Update Maven project -> Maven install -> run the executable file as a Java application (the executable file is located in the src folder within the folder MICROSERVICE_TITLE-service).

   Start the subprojects in the following order: fithub-config-server -> eureka-service-registry -> system-events-service -> auth-service -> mealplan-service -> chat-service -> training-service -> membership-service -> api-gateway.
6. The backend is now running.

To start the frontend project, follow these steps:
1. Navigate to the frontend folder (fithub-fe).
2. Download the necessary dependencies using the following command:

```
npm install
```
3. Start the application with the following command:

```
npm start
```


