<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.example.simpletradingapp.model.StockDataset" %>
<html>
<head>
  <title>Stock Data Display</title>
</head>
<body>
<h1>CSV Stock Data from ServletContext</h1>

<c:if test="${not empty allCsvData}">
  <c:forEach var="entry" items="${allCsvData}">
    <h2>${entry.key}</h2>
    <table border="1">
      <thead>
      <tr>
        <th>Date</th>
        <th>Open</th>
        <th>High</th>
        <th>Low</th>
        <th>Close</th>
        <th>Adj Close</th>
        <th>Volume</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="stock" items="${entry.value}">
        <tr>
          <td>${stock.date}</td>
          <td>${stock.open}</td>
          <td>${stock.high}</td>
          <td>${stock.low}</td>
          <td>${stock.close}</td>
          <td>${stock.adjClose}</td>
          <td>${stock.volume}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <br/>
  </c:forEach>
</c:if>

<c:if test="${empty allCsvData}">
  <p>No stock data found!</p>
</c:if>

</body>
</html>
