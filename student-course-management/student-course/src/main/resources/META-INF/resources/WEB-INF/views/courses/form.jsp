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
    <a href="${pageContext.request.contextPath}/courses" class="active">Courses</a>
  </div>
</nav>

<div class="page-wrapper">

  <c:if test="${not empty errorMsg}">
    <div class="alert alert-danger">⚠️ ${errorMsg}</div>
  </c:if>

  <div class="page-header">
    <h1>${course.id == null ? '➕' : '✏️'} ${pageTitle}</h1>
    <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">← Back</a>
  </div>

  <div class="card">
    <div class="card-header">${pageTitle}</div>
    <div class="card-body">

      <c:set var="formAction"
             value="${course.id == null ?
                     pageContext.request.contextPath.concat('/courses/save') :
                     pageContext.request.contextPath.concat('/courses/update/').concat(course.id)}"/>

      <form:form modelAttribute="course" action="${formAction}" method="post">
        <div class="form-grid">

          <div class="form-group full">
            <label for="title">Course Title *</label>
            <form:input path="title" id="title" placeholder="e.g. Machine Learning"/>
            <form:errors path="title" cssClass="error-text"/>
          </div>

          <div class="form-group full">
            <label for="instructor">Instructor Name *</label>
            <form:input path="instructor" id="instructor" placeholder="e.g. Prof. Sharma"/>
            <form:errors path="instructor" cssClass="error-text"/>
          </div>

        </div>

        <div style="margin-top:1.4rem; display:flex; gap:.75rem">
          <button type="submit" class="btn btn-success">
            ${course.id == null ? '💾 Save Course' : '✔ Update Course'}
          </button>
          <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">Cancel</a>
        </div>

      </form:form>
    </div>
  </div>
</div>
</body>
</html>
