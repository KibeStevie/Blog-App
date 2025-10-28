package com.blogapp.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.blogapp.dao.PostDAO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBInitListener implements ServletContextListener {

    // Change these values based on your setup
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/blog_db";
    private static final String USER = "postgres";
    private static final String PASS = "skibs8155";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Load PostgreSQL driver explicitly (optional for modern JDBC)
            Class.forName("org.postgresql.Driver");

            try (Connection c = DriverManager.getConnection(JDBC_URL, USER, PASS);
                 Statement s = c.createStatement()) {

                String ddl = """
                    CREATE TABLE IF NOT EXISTS posts (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                s.execute(ddl);

                ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM posts");
                rs.next();
                if (rs.getInt(1) == 0) {
                    s.executeUpdate("""
                        INSERT INTO posts(title, content)
                        VALUES
                        ('Welcome to the blog', 'This is your first post in PostgreSQL!'),
                        ('Second post', 'You can now add, edit, and delete posts.')
                    """);
                }
            }

            // Store DAO for servlets
            ServletContext ctx = sce.getServletContext();
            PostDAO dao = new PostDAO(JDBC_URL, USER, PASS);
            ctx.setAttribute("postDao", dao);

            System.out.println("✅ PostgreSQL initialized successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to initialize PostgreSQL connection", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // no cleanup needed
    }
}
