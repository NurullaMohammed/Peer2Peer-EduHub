import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDatabaseExample {

    public static void main(String[] args) {
        // Database connection parameters
        String dbURL = "jdbc:mysql://localhost:3306/Nandini";
        String username = "root";
        String password = "Mikel$268";

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(dbURL, username, password);
		for(int i=1;i<9;i++){

            // Store an image in the database and retrieve its ID
            int imageId = storeImage(connection, i+".jpg");
		}

            // Retrieve an image from the database using its ID
            //retrieveImage(connection, imageId, "Roma3.jpg");

            // Close the database connection
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static int storeImage(Connection connection, String imagePath) throws SQLException, IOException {
    File imageFile = new File(imagePath);
    String query = "INSERT INTO images (img) VALUES (?)";

    try (FileInputStream fis = new FileInputStream(imageFile);
         PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setBinaryStream(1, fis, (int) imageFile.length());
        preparedStatement.executeUpdate();

        // Get the generated ID
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int imageId = generatedKeys.getInt(1);
            System.out.println("Image stored in the database with ID: " + imageId);
            return imageId;
        }
    }
    return -1; // Return -1 if the image couldn't be stored
}


    public static void retrieveImage(Connection connection, int imageId, String outputPath) throws SQLException, IOException {
        String query = "SELECT img FROM images WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, imageId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                    byte[] buffer = new byte[1];
                    while (resultSet.getBinaryStream("img").read(buffer) > 0) {
                        fos.write(buffer);
                    }
                    System.out.println("Image with ID " + imageId + " retrieved and saved as '" + outputPath + "'.");
                }
            } else {
                System.out.println("Image with ID " + imageId + " not found in the database.");
            }
        }
    }
}