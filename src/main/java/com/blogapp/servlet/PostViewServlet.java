package com.blogapp.servlet;

import com.blogapp.dao.PostDAO;
import com.blogapp.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PostViewServlet", urlPatterns = {"/post/view"})
public class PostViewServlet extends HttpServlet {
    private PostDAO dao;

    @Override
    public void init() {
        dao = (PostDAO) getServletContext().getAttribute("postDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("id");
        if (sid == null) {
            resp.sendRedirect(req.getContextPath() + "/posts");
            return;
        }
        try {
            Post p = dao.findById(Long.parseLong(sid));
            if (p == null) {
                resp.sendRedirect(req.getContextPath() + "/posts");
                return;
            }
            req.setAttribute("post", p);
            req.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
