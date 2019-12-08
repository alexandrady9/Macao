<%@ page
        contentType="text/html; charset=windows-1256"
        pageEncoding="windows-1256"
        import="model.User" %>
<%@ page import="model.Room" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="rooms.css">
    <title>Macao</title>
</head>

<% User user = (User) session.getAttribute("currentSessionUser"); %>
<% List<Room> rooms = (List<Room>) session.getAttribute("rooms"); %>

<body>
<header class="logout">
    <a style="margin-right: 100px">Welcome   <%=user.getUsername() %></a>
    <a href="index.jsp" class="logout-button" type="submit" name="join">Logout</a>
</header>
<main class="main">
    <h1 class="room-title">Rooms</h1>
    <div class="card-container">
        <%for (int i = 0; i < rooms.size(); i++) {%>

        <button type="submit" value="<%=rooms.get(i).getId()%>" class="button game-card">
            <%Room room = rooms.get(i);%>
            <span class="number-players"> <%=room.getJoinedUsers()%> / 6 </span>
            <span class="join-button">Join</span>
        </button>

        <% } %>
    </div>
</main>
<footer class="footer">
    <form class="new-game-form" action="rooms" method="post">
        <button class="new-game-button" type="submit" name="newGame">New Game</button>
    </form>
</footer>

<script>
    var buttons = document.getElementsByClassName("button");

    Array.from(buttons).forEach(function (button) {
        button.addEventListener('click', function () {
            var value = button.value;
            fetch('rooms?' + 'roomId=' + value, {
                method: "POST"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    })

</script>

</body>

</html>