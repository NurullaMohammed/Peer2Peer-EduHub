import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageUploader extends JFrame {
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageUploader frame = new ImageUploader();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ImageUploader() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnUpload = new JButton("Upload Image");
        btnUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        storeImage(selectedFile);
                        System.out.println("Image stored in the database.");
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btnUpload.setBounds(10, 10, 150, 30);
        contentPane.add(btnUpload);
    }

    private void storeImage(File imageFile) throws SQLException, IOException {
        String dbURL = "jdbc:mysql://localhost:3306/Nandini";
        String username = "root";
        String password = "Mikel$268";

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
             FileInputStream fis = new FileInputStream(imageFile)) {
            String query = "INSERT INTO images (img) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setBinaryStream(1, fis, (int) imageFile.length());
                preparedStatement.executeUpdate();
            }
        }
    }
}
