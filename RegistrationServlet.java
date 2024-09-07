import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.Statement;


public class RegistrationServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        dataSource = (DataSource) getServletContext().getAttribute("jdbc/mydb");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String gender = request.getParameter("gender");
        String hostler = request.getParameter("hostler");
        String branch = request.getParameter("branch");
        String rollNumber = request.getParameter("rollNumber");
        int year = Integer.parseInt(request.getParameter("year"));
        String semester = request.getParameter("semester");
        float cgpa = Float.parseFloat(request.getParameter("cgpa"));
        String yearOfPass = request.getParameter("yearOfPass");

        // Handle profile image upload
        Part filePart = request.getPart("profilePicture");
        String profileImagePath = null;
        if (filePart != null) {
            String fileName = UUID.randomUUID().toString() + "_" + filePart.getSubmittedFileName();
            File uploads = new File(getServletContext().getRealPath("/uploads"));
            if (!uploads.exists()) {
                uploads.mkdirs();
            }
            File file = new File(uploads, fileName);
            try (InputStream input = filePart.getInputStream()) {
                java.nio.file.Files.copy(input, file.toPath());
            }
            profileImagePath = "/uploads/" + fileName;
        }

        try (Connection connection = dataSource.getConnection()) {
            // Insert into registration table
            String sql = "INSERT INTO registration (fullName, dob, email, mobile, gender, hostler, branch, rollNumber, year, semester, cgpa, yearOfPass, profilePicture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, fullName);
                statement.setDate(2, java.sql.Date.valueOf(dob));
                statement.setString(3, email);
                statement.setString(4, mobile);
                statement.setString(5, gender);
                statement.setString(6, hostler);
                statement.setString(7, branch);
                statement.setString(8, rollNumber);
                statement.setInt(9, year);
                statement.setString(10, semester);
                statement.setFloat(11, cgpa);
                statement.setDate(12, java.sql.Date.valueOf(yearOfPass));
                statement.setString(13, profileImagePath);
                statement.executeUpdate();

                // Get generated ID for inserted record
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int registrationId = generatedKeys.getInt(1);

                    // Handle subjects
                    String[] subjects = request.getParameterValues("subjects");
                    if (subjects != null) {
                        String subjectSql = "INSERT INTO selected_subjects (registration_id, subject) VALUES (?, ?)";
                        try (PreparedStatement subjectStatement = connection.prepareStatement(subjectSql)) {
                            for (String subject : subjects) {
                                subjectStatement.setInt(1, registrationId);
                                subjectStatement.setString(2, subject);
                                subjectStatement.addBatch();
                            }
                            subjectStatement.executeBatch();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
        response.sendRedirect("submit.html"); // Redirect to a success page
    }
}
