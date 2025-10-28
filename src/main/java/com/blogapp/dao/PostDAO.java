package com.blogapp.dao;

import com.blogapp.model.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public PostDAO(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public List<Post> findAll() throws SQLException {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT id, title, content, created_at FROM posts ORDER BY created_at DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Post p = mapRow(rs);
                list.add(p);
            }
        }
        return list;
    }

    public Post findById(long id) throws SQLException {
        String sql = "SELECT id, title, content, created_at FROM posts WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                return null;
            }
        }
    }

    public void create(Post post) throws SQLException {
        String sql = "INSERT INTO posts(title, content, created_at) VALUES (?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreatedAt()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) post.setId(keys.getLong(1));
            }
        }
    }

    public void update(Post post) throws SQLException {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setLong(3, post.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Post mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
        return new Post(id, title, content, createdAt);
    }
}
