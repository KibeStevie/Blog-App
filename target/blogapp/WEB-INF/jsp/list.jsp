<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Blog - Posts</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-800 font-sans">
    <div class="max-w-3xl mx-auto my-10 p-6 bg-white shadow-lg rounded-xl">
        <header class="flex justify-between items-center mb-6 border-b pb-4">
            <h1 class="text-3xl font-bold text-blue-600">📝 Blog Posts</h1>
            <a href="${pageContext.request.contextPath}/post/create" 
               class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition">
                ➕ New Post
            </a>
        </header>

        <c:if test="${empty posts}">
            <div class="text-center text-gray-500 py-10">
                <p>No posts yet. <a href="${pageContext.request.contextPath}/post/create" class="text-blue-600 hover:underline">Create one!</a></p>
            </div>
        </c:if>

        <c:forEach var="p" items="${posts}">
            <div class="border-b border-gray-200 py-6">
                <h2 class="text-2xl font-semibold text-gray-800 mb-2">
                    <a href="${pageContext.request.contextPath}/post/view?id=${p.id}" class="hover:text-blue-600 transition">
                        ${p.title}
                    </a>
                </h2>
                <p class="text-sm text-gray-500 mb-4">Posted on ${p.createdAt}</p>

                <div class="flex gap-3 text-sm">
                    <a href="${pageContext.request.contextPath}/post/view?id=${p.id}" 
                       class="text-blue-600 hover:underline">View</a>
                    <a href="${pageContext.request.contextPath}/post/edit?id=${p.id}" 
                       class="text-yellow-600 hover:underline">Edit</a>
                    <form action="${pageContext.request.contextPath}/post/delete" method="post" class="inline">
                        <input type="hidden" name="id" value="${p.id}" />
                        <button type="submit" 
                                class="text-red-600 hover:underline"
                                onclick="return confirm('Delete this post?')">Delete</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
