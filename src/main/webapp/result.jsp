<%--
  Created by IntelliJ IDEA.
  User: Bobby
  Date: 2025-04-09
  Time: 5:04 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Parameter Results</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 40px;
      line-height: 1.6;
    }
    .container {
      width: 800px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 5px;
      background-color: #f9f9f9;
    }
    h1 {
      color: #333;
      border-bottom: 1px solid #ddd;
      padding-bottom: 10px;
    }
    .result-section {
      margin-bottom: 20px;
      padding: 15px;
      background-color: white;
      border-radius: 5px;
      box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }
    .param-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }
    .param-table th, .param-table td {
      padding: 8px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    .param-table th {
      background-color: #f2f2f2;
    }
    .code-block {
      background-color: #f5f5f5;
      padding: 15px;
      border-radius: 4px;
      font-family: monospace;
      overflow-x: auto;
    }
    .back-button {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 4px;
    }
    .back-button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Parameter Processing Results</h1>
  <p>Request processed at: ${timestamp}</p>

  <div class="result-section">
    <h2>Processed Parameters</h2>
    <p><strong>Name:</strong> ${processedName}</p>
    <p><strong>Role:</strong> ${processedRole}</p>
    <p><strong>Message:</strong> ${processedMessage}</p>
  </div>

  <div class="result-section">
    <h2>All Request Parameters</h2>
    <table class="param-table">
      <tr>
        <th>Parameter Name</th>
        <th>Parameter Value</th>
      </tr>
      <c:forEach items="${allParams}" var="param">
        <tr>
          <td>${param.key}</td>
          <td>${param.value}</td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <div class="result-section">
    <h2>Request Information</h2>
    <p><strong>Request URL:</strong> ${requestURL}</p>
    <p><strong>Query String:</strong> ${queryString}</p>
  </div>

  <div class="result-section">
    <h2>Servlet Code Highlight</h2>
    <div class="code-block">
                <pre>// Extract parameters
String name = request.getParameter("name");
String role = request.getParameter("role");
String message = request.getParameter("message");

// Process parameters
request.setAttribute("processedName", name.toUpperCase());

// Get all parameter names
Enumeration<String> paramNames = request.getParameterNames();
while (paramNames.hasMoreElements()) {
    String paramName = paramNames.nextElement();
    String paramValue = request.getParameter(paramName);
    // Do something with each parameter
}</pre>
    </div>
  </div>

  <a href="index.html" class="back-button">Back to Form</a>
</div>
</body>
</html>
