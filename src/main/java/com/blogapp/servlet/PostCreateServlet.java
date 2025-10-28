package com.blogapp.servlet;

import com.blogapp.dao.PostDAO;
import com.blogapp.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(name = "PostCreateServlet", urlPatterns = {"/post/create"})
public class PostCreateServlet extends HttpServlet {
    private PostDAO dao;

    @Override
    public void init() {
        dao = (PostDAO) getServletContext().getAttribute("postDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "create");
        req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || title.trim().isEmpty()) {
            req.setAttribute("error", "Title is required");
            req.setAttribute("action", "create");
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            return;
        }
        try {
            Post p = new Post();
            p.setTitle(title);
            p.setContent(content);
            p.setCreatedAt(LocalDateTime.now());
            dao.create(p);
            resp.sendRedirect(req.getContextPath() + "/post/view?id=" + p.getId());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
