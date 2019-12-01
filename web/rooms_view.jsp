<%@ page language="java"
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="model.User" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="rooms.css">
    <title>Macao</title>
</head>

<body>
<header class="logout">
    <% User user = (User) session.getAttribute("currentSessionUser"); %>
    <a style="margin-right: 10px">Welcome   <%=user.getUsername() %></a>
    <button class="logout-button" type="submit" name="join">Logout</button>
</header>
<main class="main">
    <h1 class="room-title">Rooms</h1>
    <div class="card-container">
        <div class="game-card">
            <span class="number-players"> 2/6 </span>
            <span class="join-button">Join</span>
        </div>
    </div>
</main>
<footer class="footer">
    <button class="new-game-button" type="submit" name="newGame">New Game</button>
</footer>
</body>

</html>