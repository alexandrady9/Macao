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
        <button class="finish-button" name="finish">Finish</button>
        <div> Randul tau. Jocul cu id-ul <%=roomId%> unde ni s-a alaturat user-ul <%=user.getUsername()%></div>
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
                <span class="card-game-type"> <%=gameCards.getCurrentCard()%>  </span>
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
        <button class="start-game-button" name="startGame">Start</button>
        <div></div>
        <div class="actions">
            <button class="next">Urmatorul</button>
            <button class="umflatura">Umflatura</button>
            <button class="take-card">Ia carte</button>
        </div>
    </div>
</main>
<footer>
    <div class="cards-container">
        <%for(int i = 0; i < userCards.getCards().size(); i++) {%>
            <button class ="card-user" value = "<%=userCards.getCards().get(i)%>">
                <span class="card-user-type"> <%=userCards.getCards().get(i)%>  </span>
            </button>
        <% } %>
    </div>
</footer>

<script>
    var next = document.getElementsByClassName("next");
    var umflatura = document.getElementsByClassName("umflatura");
    var takeCard = document.getElementsByClassName("takeCard");

    var startGame = document.getElementsByClassName("start=game-button");
    var finishGame = document.getElementsByClassName("finish-button");

    Array.from(next).forEach(function (nextBtn) {
        nextBtn.addEventListener('click', function () {
            fetch('game?action=next', {
                method: "POST"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    });

    Array.from(umflatura).forEach(function (button) {
        button.addEventListener('click', function () {
            fetch('game?action=umflatura', {
                method: "GET"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    });

    Array.from(takeCard).forEach(function (takeBtn) {
        takeBtn.addEventListener('click', function () {
            fetch('game?action=take', {
                method: "GET"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    });

    Array.from(finishGame).forEach(function (finishBtn) {
        finishBtn.addEventListener('click', function () {
            fetch('game?action=finish', {
                method: "POST"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    });

    Array.from(startGame).forEach(function (startBtn) {
        startBtn.addEventListener('click', function () {
            fetch('game?action=start', {
                method: "GET"
            })
                .then(function (data) {
                    window.location.href = data.url;
                })
        })
    });

</script>

</body>
</html>
