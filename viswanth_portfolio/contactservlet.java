import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details (Update with your DB username/password)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/portfolio_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve form input parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        // Save into Database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO contact_messages (name, email, phone, message) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, phone);
                    pstmt.setString(4, message);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Render response confirmation page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Viswanth | Message Received</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"navbar\"><div class=\"logo\">VT</div></div>");
        out.println("<section class=\"page\">");
        out.println("<div class=\"card\" style=\"text-align: center;\">");
        out.println("<h2 style=\"color: #00e5ff;\">Thank You, " + name + "!</h2>");
        out.println("<p style=\"margin: 15px 0;\">Your message has been successfully received and stored.</p>");
        out.println("<p style=\"color: #94a3b8;\"><strong>Email:</strong> " + email + "</p>");
        out.println("<p style=\"color: #94a3b8; margin-bottom: 25px;\"><strong>Phone:</strong> " + phone + "</p>");
        out.println("<a href=\"index.html\" class=\"btn\">Back to Home</a>");
        out.println("</div>");
        out.println("</section>");
        out.println("<footer>© 2026 VISWANTH T</footer>");
        out.println("</body>");
        out.println("</html>");
    }
}