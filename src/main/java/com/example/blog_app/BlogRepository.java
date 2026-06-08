package com.example.blog_app;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {

    private final DataSource dataSource;

    public BlogRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 全件取得
    public List<Blog> findAll() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT id, title, notes FROM blogs";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("notes"); 
                
                blogs.add(new Blog(id, title, content));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return blogs;
    }

    // 1件取得
    public Blog findById(Long id) {
        String sql = "SELECT id, title, notes FROM blogs WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Blog(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("notes") // ★ notes列から取得
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 保存処理
    public void save(Blog blog) {
        String sql = "INSERT INTO blogs (title, notes) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}