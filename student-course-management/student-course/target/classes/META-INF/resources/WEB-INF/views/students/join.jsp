<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Enrollment Catalog – SCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<nav class="navbar">
  <div class="navbar-brand"><span>🎓</span> Student Course Management</div>
  <div class="navbar-links">
    <a href="${pageContext.request.contextPath}/students/join" class="active">Enrollment</a>
    <a href="${pageContext.request.contextPath}/students">Students</a>
    <a href="${pageContext.request.contextPath}/courses">Courses</a>
    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-primary btn-sm">+ Add Student</a>
  </div>
</nav>

<div class="page-wrapper">

  <c:if test="${not empty successMsg}">
    <div class="alert alert-success">✅ ${successMsg}</div>
  </c:if>

  <div class="page-header">
    <h1>📋 Student–Course Enrollment</h1>
    <span style="color:var(--muted);font-size:.9rem">
      ${data.size()} enrollment(s) — INNER JOIN query result
    </span>
  </div>

  <!-- Stats row -->
  <div class="stat-row">
    <div class="stat-card">
      <div class="stat-icon">👩‍🎓</div>
      <div class="stat-info">
        <div class="value">${data.size()}</div>
        <div class="label">Total Enrollments</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">📚</div>
      <div class="stat-info">
        <div class="value">
          <a href="${pageContext.request.contextPath}/courses" style="text-decoration:none;color:var(--primary)">View Courses →</a>
        </div>
        <div class="label">Manage Courses</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">➕</div>
      <div class="stat-info">
        <div class="value">
          <a href="${pageContext.request.contextPath}/students/add" style="text-decoration:none;color:var(--primary)">Add Student →</a>
        </div>
        <div class="label">Enroll New Student</div>
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-header">📊 Enrollment Table (INNER JOIN: students ⋈ courses)</div>
    <div class="card-body" style="padding:0">
      <c:choose>
        <c:when test="${empty data}">
          <div class="empty-state">
            <div class="icon">📭</div>
            <p>No enrollments yet. <a href="${pageContext.request.contextPath}/students/add">Add a student!</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Student Name</th>
                  <th>Email</th>
                  <th>Course</th>
                  <th>Instructor</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="row" items="${data}" varStatus="s">
                  <tr>
                    <td>${s.count}</td>
                    <td class="student-cell">${row.studentName}</td>
                    <td style="font-size:.87rem;color:var(--muted)">${row.studentEmail}</td>
                    <td class="course-cell">
                      <span class="badge badge-course">${row.courseTitle}</span>
                    </td>
                    <td><span class="badge badge-instructor">${row.instructor}</span></td>
                    <td>
                      <a href="${pageContext.request.contextPath}/students/edit/${row.studentId}"
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
