<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>${pageTitle} – SCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<nav class="navbar">
  <div class="navbar-brand"><span>🎓</span> Student Course Management</div>
  <div class="navbar-links">
    <a href="${pageContext.request.contextPath}/students/join">Enrollment</a>
    <a href="${pageContext.request.contextPath}/students">Students</a>
    <a href="${pageContext.request.contextPath}/courses">Courses</a>
  </div>
</nav>

<div class="page-wrapper">

  <c:if test="${not empty errorMsg}">
    <div class="alert alert-danger">⚠️ ${errorMsg}</div>
  </c:if>

  <div class="page-header">
    <h1>${student.id == null ? '➕' : '✏️'} ${pageTitle}</h1>
    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">← Back</a>
  </div>

  <div class="card">
    <div class="card-header">${pageTitle}</div>
    <div class="card-body">

      <c:set var="formAction"
             value="${student.id == null ?
                     pageContext.request.contextPath.concat('/students/save') :
                     pageContext.request.contextPath.concat('/students/update/').concat(student.id)}"/>

      <form:form modelAttribute="student" action="${formAction}" method="post">
        <div class="form-grid">

          <!-- Name -->
          <div class="form-group">
            <label for="name">Full Name *</label>
            <form:input path="name" id="name" placeholder="e.g. Mihir Shah"/>
            <form:errors path="name" cssClass="error-text"/>
          </div>

          <!-- Email -->
          <div class="form-group">
            <label for="email">Email Address *</label>
            <form:input path="email" id="email" type="email" placeholder="e.g. mihir@gmail.com"/>
            <form:errors path="email" cssClass="error-text"/>
          </div>

          <!-- Course dropdown -->
          <div class="form-group full">
            <label for="courseId">Enrolled Course *</label>
            <select name="courseId" id="courseId" required>
              <option value="">-- Select a course --</option>
              <c:forEach var="c" items="${courses}">
                <option value="${c.id}"
                  ${student.course != null && student.course.id == c.id ? 'selected' : ''}>
                  ${c.title} — ${c.instructor}
                </option>
              </c:forEach>
            </select>
          </div>

        </div>

        <div style="margin-top:1.4rem; display:flex; gap:.75rem">
          <button type="submit" class="btn btn-success">
            ${student.id == null ? '💾 Save Student' : '✔ Update Student'}
          </button>
          <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Cancel</a>
        </div>

      </form:form>
    </div>
  </div>
</div>
</body>
</html>
