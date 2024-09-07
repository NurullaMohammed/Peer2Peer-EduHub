import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ImageRetriever extends JFrame {
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageRetriever frame = new ImageRetriever();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ImageRetriever() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnRetrieve = new JButton("Retrieve Image");
        btnRetrieve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    retrieveImage("output.jpg");
                    System.out.println("Image retrieved and saved as 'output.jpg'");
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnRetrieve.setBounds(10, 10, 150, 30);
        contentPane.add(btnRetrieve);
    }

    private void retrieveImage(String outputPath) throws SQLException, IOException {
        String dbURL = "jdbc:mysql://localhost:3306/Nandini";
        String username = "root";
        String password = "Mikel$268";

        try (Connection connection = DriverManager.getConnection(dbURL, username, password)) {
            String query = "SELECT img FROM images WHERE id = ?"; // Replace with the appropriate image ID
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, 1); // Replace with the ID of the image you want to retrieve
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    byte[] imageData = resultSet.getBytes("img");
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
                    File output = new File(outputPath);
                    ImageIO.write(image, "jpg", output);
                }
            }
        }
    }
}
