<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>${course.title} – SCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<nav class="navbar">
  <div class="navbar-brand"><span>🎓</span> Student Course Management</div>
  <div class="navbar-links">
    <a href="${pageContext.request.contextPath}/students/join">Enrollment</a>
    <a href="${pageContext.request.contextPath}/students">Students</a>
    <a href="${pageContext.request.contextPath}/courses" class="active">Courses</a>
  </div>
</nav>

<div class="page-wrapper">

  <div class="page-header">
    <h1>📖 ${course.title}</h1>
    <div style="display:flex;gap:.5rem">
      <a href="${pageContext.request.contextPath}/courses/edit/${course.id}" class="btn btn-warning">✏️ Edit</a>
      <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">← Back</a>
    </div>
  </div>

  <div class="card" style="margin-bottom:1.5rem">
    <div class="card-header">Course Information</div>
    <div class="card-body">
      <div class="detail-grid">
        <div class="detail-item">
          <span class="label">Course Title</span>
          <span class="value">${course.title}</span>
        </div>
        <div class="detail-item">
          <span class="label">Instructor</span>
          <span class="value"><span class="badge badge-instructor">${course.instructor}</span></span>
        </div>
        <div class="detail-item">
          <span class="label">Enrolled Students</span>
          <span class="value">${course.students.size()}</span>
        </div>
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-header">Students in ${course.title}</div>
    <div class="card-body" style="padding:0">
      <c:choose>
        <c:when test="${empty course.students}">
          <div class="empty-state">
            <div class="icon">📭</div>
            <p>No students enrolled in this course yet.</p>
          </div>
        </c:when>
        <c:otherwise>
          <div class="table-wrapper">
            <table>
              <thead>
                <tr><th>#</th><th>Name</th><th>Email</th><th>Action</th></tr>
              </thead>
              <tbody>
                <c:forEach var="s" items="${course.students}" varStatus="st">
                  <tr>
                    <td>${st.count}</td>
                    <td class="student-cell">${s.name}</td>
                    <td style="font-size:.87rem;color:var(--muted)">${s.email}</td>
                    <td>
                      <a href="${pageContext.request.contextPath}/students/edit/${s.id}"
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
