<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>All Students – SCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<nav class="navbar">
  <div class="navbar-brand"><span>🎓</span> Student Course Management</div>
  <div class="navbar-links">
    <a href="${pageContext.request.contextPath}/students/join">Enrollment</a>
    <a href="${pageContext.request.contextPath}/students" class="active">Students</a>
    <a href="${pageContext.request.contextPath}/courses">Courses</a>
    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-primary btn-sm">+ Add Student</a>
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
    <h1>👩‍🎓 All Students</h1>
    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-success">＋ Add Student</a>
  </div>

  <div class="card">
    <div class="card-header">Student Registry (${students.size()} records)</div>
    <div class="card-body" style="padding:0">
      <c:choose>
        <c:when test="${empty students}">
          <div class="empty-state">
            <div class="icon">👤</div>
            <p>No students yet. <a href="${pageContext.request.contextPath}/students/add">Add one!</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <div class="table-wrapper">
            <table>
              <thead>
                <tr><th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Instructor</th><th>Actions</th></tr>
              </thead>
              <tbody>
                <c:forEach var="student" items="${students}">
                  <tr>
                    <td>${student.id}</td>
                    <td class="student-cell">${student.name}</td>
                    <td style="font-size:.87rem;color:var(--muted)">${student.email}</td>
                    <td><span class="badge badge-course">${student.course.title}</span></td>
                    <td><span class="badge badge-instructor">${student.course.instructor}</span></td>
                    <td>
                      <a href="${pageContext.request.contextPath}/students/edit/${student.id}"
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
