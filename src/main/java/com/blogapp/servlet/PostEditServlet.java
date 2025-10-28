package com.blogapp.servlet;

import com.blogapp.dao.PostDAO;
import com.blogapp.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PostEditServlet", urlPatterns = {"/post/edit"})
public class PostEditServlet extends HttpServlet {
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
            req.setAttribute("action", "edit");
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            Post p = dao.findById(id);
            if (p == null) {
                resp.sendRedirect(req.getContextPath() + "/posts");
                return;
            }
            p.setTitle(title);
            p.setContent(content);
            dao.update(p);
            resp.sendRedirect(req.getContextPath() + "/post/view?id=" + id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
