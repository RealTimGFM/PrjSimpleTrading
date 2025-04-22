<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Login</title></head>
<body>
<h1>Login</h1>
<form action="login" method="post">
    Username: <input type="text" name="username" required><br><br>
    Password: <input type="password" name="password" required><br><br>
    <button type="submit">Login</button>
</form>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
<a href="signup.jsp">Don't have an account? Sign Up</a>
</body>
</html>
