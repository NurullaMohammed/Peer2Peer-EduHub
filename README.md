
# Peer2Peer EduHub

**Peer2Peer EduHub** is a web application designed to connect senior students with junior students for academic support. This repository contains the code for an interface that facilitates this interaction, using technologies such as HTML, JavaScript, CSS, Java Servlets, JSP pages, and Tomcat servers.

## Features

- **Registration System:** Students can register by providing personal details and academic information. During registration, students specify their favorite and expected subjects.
- **Database Integration:** Registered student data is stored in a database.
- **Student Matching:** The system allows students to find senior students who excel in subjects where they need help. It matches juniors with seniors based on CGPA and subject interests.
- **User Interface:** The application includes:
  - A home page for navigating the application.
  - A sidebar with a list of subjects.
  - Profiles of students who are experts in selected subjects.
  - Details of students, including their expertise and contact information.

## Technology Stack

- **Frontend:** HTML, JavaScript, CSS
- **Backend:** Java Servlets, JSP pages
- **Server:** Apache Tomcat
- **Database:** (Specify the database used, if applicable)

## Setup and Usage

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/peer2peer-eduhub.git
   ```

2. **Deploy to Tomcat:**
   - Copy the WAR file or extracted files into the `webapps` directory of your Tomcat server.
   - Restart Tomcat to deploy the application.

3. **Access the Application:**
   - Navigate to `http://localhost:8080/yourapp` in your web browser.

4. **Features:**
   - **Registration:** Access the registration page to sign up as a student.
   - **Login:** Log in to the system to access your profile and interact with the subject list.
   - **Subject List:** View and select subjects from the sidebar to find students who are experts in those areas.

## Development Notes

- The application is tested locally using a Tomcat server and Java Servlets.
- Current functionality includes registration, login, and basic profile viewing.
- Future improvements may include enhanced matching algorithms and additional user features.

## Contributing

Feel free to fork the repository and submit pull requests with improvements or bug fixes.
