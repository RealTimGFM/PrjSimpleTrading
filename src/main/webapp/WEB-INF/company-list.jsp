<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Stock Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        h1, h2 {
            color: #333;
        }
        .container {
            background-color: white;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .category-filter {
            margin-bottom: 20px;
        }
        .category-filter a {
            margin-right: 10px;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 3px;
            background-color: #eee;
            color: #333;
        }
        .category-filter a.selected {
            background-color: #4CAF50;
            color: white;
        }
        .view-link {
            color: #2196F3;
            text-decoration: none;
        }
        .view-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Inventory Management</h1>

    <!-- Category filter -->
    <div class="category-filter">
        <strong>Filter by Category:</strong>
        <a href="${pageContext.request.contextPath}/stonks"
           class="${empty selectedCategoryId ? 'selected' : ''}">All Stocks</a>

        <c:forEach var="category" items="${categories}">
            <a href="${pageContext.request.contextPath}/stonks?action=category&symbol=${category.symbol}"
               class="${selectedCategoryId == category.symbol ? 'selected' : ''}">${category.symbol}</a>
        </c:forEach>
    </div>

    <!-- Inventory table -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty stocks}">
                <tr>
                    <td colspan="6">No stocks found</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${stocks}">
                    <tr>
                        <td>${item.stockId}</td>
                        <td>${item.date}</td>
                        <td>${item.category.name}</td>
                        <td>$${item.close}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/stonks?action=view&id=${item.stockId}"
                               class="view-link">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>