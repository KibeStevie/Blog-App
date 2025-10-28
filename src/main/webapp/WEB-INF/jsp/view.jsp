<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${post.title}</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-800 font-sans">
    <div class="max-w-3xl mx-auto my-10 p-8 bg-white shadow-lg rounded-xl">
        <!-- Back link -->
        <p class="mb-6">
            <a href="${pageContext.request.contextPath}/posts" 
               class="text-blue-600 hover:underline flex items-center gap-1">
               ← Back to Posts
            </a>
        </p>

        <!-- Post Title -->
        <h1 class="text-4xl font-bold text-gray-900 mb-3">${post.title}</h1>

        <!-- Metadata -->
        <div class="text-sm text-gray-500 mb-6">
            Posted on <span class="font-medium">${post.createdAt}</span>
        </div>

        <!-- Post Content -->
        <div class="prose max-w-none text-gray-700 leading-relaxed whitespace-pre-wrap">
            ${post.content}
        </div>

        <!-- Action Buttons -->
        <div class="mt-8 flex gap-4">
            <a href="${pageContext.request.contextPath}/post/edit?id=${post.id}" 
               class="px-5 py-2 bg-yellow-500 text-white rounded-lg hover:bg-yellow-600 transition">
               ✏️ Edit Post
            </a>
            <a href="${pageContext.request.contextPath}/posts" 
               class="px-5 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition">
               🔙 Back
            </a>
        </div>
    </div>
</body>
</html>
