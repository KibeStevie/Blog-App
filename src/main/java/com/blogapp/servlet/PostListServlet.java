package com.blogapp.servlet;

import com.blogapp.dao.PostDAO;
import com.blogapp.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostListServlet", urlPatterns = {"/posts"})
public class PostListServlet extends HttpServlet {
    private PostDAO dao;

    @Override
    public void init() {
        dao = (PostDAO) getServletContext().getAttribute("postDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Post> posts = dao.findAll();
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
