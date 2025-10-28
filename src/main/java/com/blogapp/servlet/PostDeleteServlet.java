package com.blogapp.servlet;

import com.blogapp.dao.PostDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PostDeleteServlet", urlPatterns = {"/post/delete"})
public class PostDeleteServlet extends HttpServlet {
    private PostDAO dao;

    @Override
    public void init() {
        dao = (PostDAO) getServletContext().getAttribute("postDao");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/posts");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
