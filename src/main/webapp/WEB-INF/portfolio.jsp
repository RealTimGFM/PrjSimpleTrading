<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <title>Your Portfolio</title>
</head>
<body>
<h1>Your Portfolio</h1>

<c:if test="${empty portfolio}">
  <p>You own 0 stocks.</p>
</c:if>

<c:if test="${not empty portfolio}">
  <table border="1" cellpadding="8">
    <thead>
    <tr>
      <th>Symbol</th>
      <th>Name</th>
      <th>Quantity</th>
      <th>Avg Buy Price</th>
      <th>Total Value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${portfolio}">
      <tr>
        <td>${item.symbol}</td>
        <td>${item.name}</td>
        <td>${item.quantity}</td>
        <td>$<fmt:formatNumber value="${item.avgPrice}" minFractionDigits="2"/></td>
        <td>$<fmt:formatNumber value="${item.totalValue}" minFractionDigits="2"/></td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<br>
<a href="${pageContext.request.contextPath}/company-list">← Back to Company List</a>
</body>
</html>
