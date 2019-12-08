<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="./game.css">
    <title>Game</title>
</head>
<body>

<% String roomId = (String) session.getAttribute("roomId"); %>
<% User user = (User) session.getAttribute("currentSessionUser"); %>

<main class="main-container">
    <div class="info">
        <div> 2/6 </div>
        <div> Randul tau. Jocul cu id-ul <%=roomId%> unde ni s-a alaturat user-ul <%=user.getUsername()%></div>
        <div></div>
    </div>
    <div class="game">
        <img height="50px" width="100px" src="resources/card.jpg">
    </div>
</main>
<footer>
    <div class="cards-container">
<%--        <img height="850" width="1332" src="resources/card.jpg">--%>
<%--        <img height="850" width="1332" src="resources/card.jpg">--%>
<%--        <img height="850" width="1332" src="resources/card.jpg">--%>
<%--        <img height="850" width="1332" src="resources/card.jpg">--%>
<%--        <img height="850" width="1332" src="resources/card.jpg">--%>
    </div>
</footer>
</body>
</html>
