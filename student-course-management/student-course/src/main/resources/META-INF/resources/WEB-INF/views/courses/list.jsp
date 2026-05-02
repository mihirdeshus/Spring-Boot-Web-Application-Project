<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>All Courses – SCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<nav class="navbar">
  <div class="navbar-brand"><span>🎓</span> Student Course Management</div>
  <div class="navbar-links">
    <a href="${pageContext.request.contextPath}/students/join">Enrollment</a>
    <a href="${pageContext.request.contextPath}/students">Students</a>
    <a href="${pageContext.request.contextPath}/courses" class="active">Courses</a>
    <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-primary btn-sm">+ Add Course</a>
  </div>
</nav>

<div class="page-wrapper">

  <c:if test="${not empty successMsg}">
    <div class="alert alert-success">✅ ${successMsg}</div>
  </c:if>
  <c:if test="${not empty errorMsg}">
    <div class="alert alert-danger">⚠️ ${errorMsg}</div>
  </c:if>

  <div class="page-header">
    <h1>📚 All Courses</h1>
    <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-success">＋ Add Course</a>
  </div>

  <div class="card">
    <div class="card-header">Course Catalogue (${courses.size()} records)</div>
    <div class="card-body" style="padding:0">
      <c:choose>
        <c:when test="${empty courses}">
          <div class="empty-state">
            <div class="icon">📭</div>
            <p>No courses yet. <a href="${pageContext.request.contextPath}/courses/add">Add one!</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <div class="table-wrapper">
            <table>
              <thead>
                <tr><th>ID</th><th>Course Title</th><th>Instructor</th><th>Students Enrolled</th><th>Actions</th></tr>
              </thead>
              <tbody>
                <c:forEach var="course" items="${courses}">
                  <tr>
                    <td>${course.id}</td>
                    <td><strong>${course.title}</strong></td>
                    <td><span class="badge badge-instructor">${course.instructor}</span></td>
                    <td>${course.students.size()}</td>
                    <td style="display:flex;gap:.4rem">
                      <a href="${pageContext.request.contextPath}/courses/${course.id}"
                         class="btn btn-primary btn-sm">👁 View</a>
                      <a href="${pageContext.request.contextPath}/courses/edit/${course.id}"
                         class="btn btn-warning btn-sm">✏️ Edit</a>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
</body>
</html>
