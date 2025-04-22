<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Sign Up</title></head>
<body>
<h1>Create Account</h1>
<form action="signup" method="post">
  Username: <input type="text" name="username" required><br><br>
  Email: <input type="email" name="email" required><br><br>
  Password: <input type="password" name="password" required><br><br>
  <button type="submit">Sign Up</button>
</form>
<c:if test="${not empty error}">
  <p style="color: red">${error}</p>
</c:if>
<a href="login.jsp">Already have an account? Login</a>
</body>
</html>
