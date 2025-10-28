<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${action == 'edit'}">Edit Post</c:when>
            <c:otherwise>Create Post</c:otherwise>
        </c:choose>
    </title>

    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-800 font-sans">
    <div class="max-w-2xl mx-auto my-10 p-8 bg-white shadow-lg rounded-xl">
        <!-- Back link -->
        <p class="mb-6">
            <a href="${pageContext.request.contextPath}/posts" 
               class="text-blue-600 hover:underline flex items-center gap-1">
               ← Back to Posts
            </a>
        </p>

        <!-- Header -->
        <h1 class="text-3xl font-bold text-gray-900 mb-6">
            <c:choose>
                <c:when test="${action == 'edit'}">✏️ Edit Post</c:when>
                <c:otherwise>📝 Create Post</c:otherwise>
            </c:choose>
        </h1>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <div class="mb-4 p-3 bg-red-100 border border-red-300 text-red-700 rounded-lg">
                ${error}
            </div>
        </c:if>

        <!-- Form -->
        <form method="post" 
              action="<c:choose>
                          <c:when test='${action == "edit"}'>
                              ${pageContext.request.contextPath}/post/edit
                          </c:when>
                          <c:otherwise>
                              ${pageContext.request.contextPath}/post/create
                          </c:otherwise>
                      </c:choose>">

            <c:if test="${action == 'edit'}">
                <input type="hidden" name="id" value="${post.id}" />
            </c:if>

            <!-- Title -->
            <div class="mb-5">
                <label for="title" class="block font-semibold text-gray-700 mb-2">Title</label>
                <input id="title" name="title" type="text" value="${post.title}" required
                       class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:outline-none" />
            </div>

            <!-- Content -->
            <div class="mb-6">
                <label for="content" class="block font-semibold text-gray-700 mb-2">Content</label>
                <textarea id="content" name="content" rows="10"
                          class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:outline-none">${post.content}</textarea>
            </div>

            <!-- Buttons -->
            <div class="flex items-center gap-4">
                <button type="submit" 
                        class="bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700 transition">
                    💾 Save
                </button>
                <a href="${pageContext.request.contextPath}/posts"
                   class="text-gray-600 hover:text-gray-800 transition">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
