<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Redirect root URL to the /posts route
    response.sendRedirect(request.getContextPath() + "/posts");
%>