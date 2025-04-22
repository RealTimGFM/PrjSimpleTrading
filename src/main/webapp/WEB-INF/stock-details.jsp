<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Stock Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }
        .container {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            max-width: 800px;
            margin: auto;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        .detail-row {
            display: flex;
            padding: 10px 0;
            border-bottom: 1px solid #eee;
        }
        .detail-label {
            width: 30%;
            font-weight: bold;
        }
        .detail-value {
            width: 70%;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #2196F3;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 8px;
            border: 1px solid #ddd;
        }
        th {
            background-color: #eee;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Stock Details</h1>
    <p>
        Logged in as: <strong>${sessionScope.username}</strong>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </p>
    <c:if test="${not empty stock}">
        <div class="stock-details">
            <div class="detail-row"><div class="detail-label">ID:</div><div class="detail-value">${stock.stockId}</div></div>
            <div class="detail-row"><div class="detail-label">Symbol:</div><div class="detail-value">${stock.category.symbol}</div></div>
            <div class="detail-row"><div class="detail-label">Name:</div><div class="detail-value">${stock.category.name}</div></div>
            <div class="detail-row"><div class="detail-label">Date:</div><div class="detail-value"><fmt:formatDate value="${stock.date}" pattern="yyyy-MM-dd" /></div></div>
            <div class="detail-row"><div class="detail-label">Price (Close):</div><div class="detail-value">$<fmt:formatNumber value="${stock.close}" minFractionDigits="2" maxFractionDigits="2" /></div></div>
            <div class="detail-row"><div class="detail-label">Open:</div><div class="detail-value">$<fmt:formatNumber value="${stock.open}" minFractionDigits="2" maxFractionDigits="2" /></div></div>
            <div class="detail-row"><div class="detail-label">High:</div><div class="detail-value">$<fmt:formatNumber value="${stock.high}" minFractionDigits="2" maxFractionDigits="2" /></div></div>
            <div class="detail-row"><div class="detail-label">Low:</div><div class="detail-value">$<fmt:formatNumber value="${stock.low}" minFractionDigits="2" maxFractionDigits="2" /></div></div>
            <div class="detail-row"><div class="detail-label">Volume:</div><div class="detail-value">${stock.volume}</div></div>
        </div>
    </c:if>

    <c:if test="${empty stock}">
        <p>No stock data found.</p>
    </c:if>

    <h2>View History</h2>
    <button onclick="toggleHistory()">Show History</button>

    <div id="historySection" style="display:none; margin-top: 20px;">
        <c:if test="${not empty historyList}">
            <table>
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Open</th>
                    <th>High</th>
                    <th>Low</th>
                    <th>Close</th>
                    <th>Volume</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="h" items="${historyList}">
                    <tr>
                        <td><fmt:formatDate value="${h.date}" pattern="yyyy-MM-dd" /></td>
                        <td><fmt:formatNumber value="${h.open}" minFractionDigits="2" maxFractionDigits="2" /></td>
                        <td><fmt:formatNumber value="${h.high}" minFractionDigits="2" maxFractionDigits="2" /></td>
                        <td><fmt:formatNumber value="${h.low}" minFractionDigits="2" maxFractionDigits="2" /></td>
                        <td><fmt:formatNumber value="${h.close}" minFractionDigits="2" maxFractionDigits="2" /></td>
                        <td>${h.volume}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    <hr>
    <h2>💸 Buy Stock</h2>

    <form method="post" action="${pageContext.request.contextPath}/buy-stock">
        <input type="hidden" name="symbol" value="${stock.category.symbol}" />
        <label for="quantity">Quantity:</label>
        <input type="number" name="quantity" id="quantity" required min="1" />
        <button type="submit">Buy Now</button>
    </form>
    <hr>
    <h2>📉 Sell Stock</h2>

    <form method="post" action="${pageContext.request.contextPath}/sell-stock">
        <input type="hidden" name="symbol" value="${stock.category.symbol}" />
        <label for="sellQty">Quantity to Sell:</label>
        <input type="number" name="quantity" id="sellQty" required min="1" />
        <button type="submit">Sell Now</button>
    </form>
    <a class="back-link" href="${pageContext.request.contextPath}/company-list">← Back to Company List</a>
</div>

<script>
    function toggleHistory() {
        var sec = document.getElementById("historySection");
        sec.style.display = (sec.style.display === "none") ? "block" : "none";
    }
</script>
</body>
</html>
