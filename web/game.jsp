<%@ page import="model.User" %>
<%@ page import="model.GameCards" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UserCards" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="./game.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Game</title>
</head>
<body>

<%  Long roomId = (Long) session.getAttribute("roomId");
    User user = (User) session.getAttribute("currentSessionUser");
    GameCards gameCards = (GameCards) session.getAttribute("gameCards");
    List<UserCards> usersCards = (List<UserCards>) session.getAttribute("usersCards");

    UserCards userCards = (UserCards) session.getAttribute("userCards");
%>

<main class="main-container">
    <div class="info">
        <button id="finish-game" class="finish-button" name="finish">Finish</button>
        <div> Randul tau. Jocul cu id-ul <%=roomId%> unde ni s-a alaturat user-ul <%=user.getUsername()%></div>
        <div></div>
    </div>
    <div class="game-wrapper">
        <div class="users">
            <table>
                <tr>
                    <td> <i class="fa fa-users"></i> Jucatori:</td>
                </tr>
                <tr><td></td></tr>
                <%for (int i = 0; i < usersCards.size(); i++) { %>
                <tr>
                    <td>Jucatorul <%=i+1%> : <%=usersCards.get(i).getUser().getUsername()%></td>
                </tr>
                <%}%>
            </table>
        </div>
        <div class="game">
            <button class ="card-game">
                <span class="card-game-type"> <%=gameCards.getCurrentCard().getNumber().getNumberCode()%>
                    <%=gameCards.getCurrentCard().getSuit()%>  </span>
            </button>
        </div>
        <div class="deck">
            <%if(gameCards.getCards().size() > 0) {%>
            <img height="200" width="150" src="resources/card-back.png"/>
            <% } else {%>
            <img height="200" width="150" src="resources/empty-deck.png"/>
            <%}%>
        </div>
    </div>

    <div class="actions-wrapper">
        <button id="start-game" class="start-game-button" name="start">Start</button>
        <div class="actions">
            <button id="next">Urmatorul</button>
            <button id="draw">Umflatura</button>
            <button id="take-card">Ia carte</button>
        </div>
    </div>
</main>
<footer>
    <div class="cards-container">
        <%for(int i = 0; i < userCards.getCards().size(); i++) {%>
            <button class ="card-user" value = "<%=userCards.getCards().get(i)%>">

                <span class="card-user-type"> <%=userCards.getCards().get(i).getNumber().getNumberCode()%> +
                                                <%=userCards.getCards().get(i).getSuit()%>"</span>
            </button>
        <% } %>
    </div>
</footer>

<script>
    var next = document.getElementById("next");
    var draw = document.getElementById("draw");
    var takeCard = document.getElementById("take-card");

    var startGame = document.getElementById("start-game");
    var finishGame = document.getElementById("finish-game");

    next.addEventListener('click', function () {
            fetch('game?action=next', {
                method: "POST"
            })
                .then(function (data) {
                    //window.location.href = data.url;
                })
    });

    draw.addEventListener('click', function () {
            fetch('game?action=draw', {
                method: "POST"
            })
                .then(function (data) {
                    //window.location.href = data.url;
                })
    });

    takeCard.addEventListener('click', function () {
            fetch('game?action=take', {
                method: "POST"
            })
                .then(function (data) {
                    //window.location.href = data.url;
                })
    });

    finishGame.addEventListener('click', function () {
            fetch('game?action=finish', {
                method: "POST"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
    });

    startGame.addEventListener('click', function () {
            fetch('game?action=start', {
                method: "POST"
            })
                .then(function (data) {
                    //window.location.href = data.url;
                })
    });

</script>

</body>
</html>
