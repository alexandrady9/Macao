<%@ page import="model.GameCards" %>
<%@ page import="model.User" %>
<%@ page import="model.UserCards" %>
<%@ page import="java.util.List" %>
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

<% Long roomId = (Long) session.getAttribute("roomId");
    User user = (User) session.getAttribute("currentSessionUser");
    GameCards gameCards = (GameCards) session.getAttribute("gameCards");
    List<UserCards> usersCards = (List<UserCards>) session.getAttribute("usersCards");

    UserCards userCards = (UserCards) session.getAttribute("userCards");
%>

<main class="main-container">
    <div class="info">
        <button id="finish-game" class="finish-button" name="finish">Finish</button>
        <div> It's your turn,  <%=usersCards.get(gameCards.getCurrentPositionForUser()).getUser().getUsername()%>.
            Game with id <%=roomId%>, where the user <%=user.getUsername()%> joined us.
        </div>
        <div></div>
    </div>
    <div class="game-wrapper">
        <div class="users">
            <table>
                <tr>
                    <td><i class="fa fa-users"></i> Players:</td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <%for (int i = 0; i < usersCards.size(); i++) { %>
                <tr>
                    <td>Player <%=i + 1%> : <%=usersCards.get(i).getUser().getUsername()%>
                    </td>
                </tr>
                <%}%>
            </table>
        </div>
        <div class="game">
            <button class="card-game">
                <span class="card-game-type"> <%=gameCards.getCurrentCard().getNumber().getNumberCode()%>
                    <%=gameCards.getCurrentCard().getSuit()%>  </span>
            </button>
        </div>
        <div class="deck">
            <%if ( gameCards.getCards().size() > 0 ) {%>
            <img height="200" width="150" src="resources/card-back.png"/>
            <% } else {%>
            <img height="200" width="150" src="resources/empty-deck.png"/>
            <%}%>
        </div>
    </div>

    <div class="actions-wrapper">
        <%if ( gameCards.getIsStartGame() == 0 ) {%>

        <button id="start-game"
                currentUser="<%=usersCards.get(gameCards.getCurrentPositionForUser()).getUser().getId()%>"
                currentSessionUser="<%=user.getId()%>"
                class="start-game-button" name="start">Start
        </button>

        <% } %>
        <div class="actions">
            <button id="next">Next</button>
            <button id="draw" cardsToDraw="<%=gameCards.getCardsToDraw()%>">Draw</button>
            <button id="take-card">Take card</button>
        </div>
    </div>

    <div class="error-wrapper">
        <div id="alert" class="alert">
            <span id="close-alert" class="close-error">&times;</span>
            <span id="alert-message"></span>
        </div>
    </div>
</main>
<footer>
    <div class="cards-container">
        <%for (int i = 0; i < userCards.getCards().size(); i++) {%>
        <button class="card-user"
                cardId="<%=i%>"
                cardSuit="<%=userCards.getCards().get(i).getSuit()%>"
                cardNumber="<%=userCards.getCards().get(i).getNumber().getNumberCode()%>"
                currentCardSuit="<%=gameCards.getCurrentCard().getSuit()%>"
                currentCardNumber="<%=gameCards.getCurrentCard().getNumber().getNumberCode()%>">

                <span class="card-user-type"> <%=userCards.getCards().get(i).getNumber().getNumberCode()%>
                                                <%=userCards.getCards().get(i).getSuit()%></span>
        </button>
        <% } %>
    </div>
</footer>

<script>

    var putCardFromDeck = document.getElementsByClassName("card-user");

    var next = document.getElementById("next");
    var draw = document.getElementById("draw");
    var takeCard = document.getElementById("take-card");

    var startGame = document.getElementById("start-game");
    var finishGame = document.getElementById("finish-game");

    var closeAlert = document.getElementById("close-alert");
    var alert = document.getElementById("alert");
    var alertMessage = document.getElementById("alert-message");

    var counterForDraw;
    var cardsToDraw = draw.getAttribute("cardsToDraw");

    Array.from(putCardFromDeck).forEach(function (putCard) {
        putCard.addEventListener('click', function () {
            var cardId = putCard.getAttribute("cardId");
            var cardSuit = putCard.getAttribute("cardSuit");
            var cardNumber = putCard.getAttribute("cardNumber");
            var currentCardSuit = putCard.getAttribute("currentCardSuit");
            var currentCardNumber = putCard.getAttribute("currentCardNumber");

            if (cardNumber === "Joker") {
                if ((currentCardSuit === "Diamond" || currentCardSuit === "Heart") && cardSuit === "Black") {
                    alert.style.opacity = "1";
                    alertMessage.innerHTML = "Joker-ul se poate da doar peste aceeasi culoare. Incearca o alta carte sau ia o carte.";
                } else if ((currentCardSuit === "Club" || currentCardSuit === "Spade") && cardSuit === "Red") {
                    alert.style.opacity = "1";
                    alertMessage.innerHTML = "Joker-ul se poate da doar peste aceeasi culoare. Incearca o alta carte sau ia o carte.";
                } else {
                    // if (cardSuit === "Red") {
                    //     counterForDraw += 10;
                    // }
                    // else if (cardSuit === "Black") {
                    //     counterForDraw += 5;
                    // }
                    fetch('game?cardId=' + cardId, {
                        method: "POST"
                    })
                        .then(function (data) {
                        });
                }
            } else if (cardNumber === "2" || cardNumber === "3" || cardNumber === "4") {
                // if (cardNumber === "2") {
                //     counterForDraw += 2;
                // } else if (cardNumber === "3") {
                //     counterForDraw += 3;
                // } else if (cardNumber === "4") {
                //     counterForDraw = 0;
                // }
                fetch('game?cardId=' + cardId, {
                    method: "POST"
                })
                    .then(function (data) {
                    });
            } else if ((cardNumber !== currentCardNumber) && (cardSuit !== currentCardSuit)) {
                alert.style.opacity = "1";
                alertMessage.innerHTML = "Numarul sau simbolul nu corespund. Incearca o alta carte sau ia o carte.";
            } else {
                fetch('game?cardId=' + cardId, {
                    method: "POST"
                })
                    .then(function (data) {
                    });
            }
        })
    });

    draw.addEventListener('click', function () {
        if(cardsToDraw !== "0") {
            fetch('game?action=draw', {
                method: "POST"
            })
                .then(function (data) {
                });
        } else {
            alert.style.opacity = "1";
            alertMessage.innerHTML = "Nu sunt carti de umflat!";
        }
    });

    //if (counterForDraw === 0) {
        next.addEventListener('click', function () {
            fetch('game?action=next', {
                method: "POST"
            })
                .then(function (data) {
                });
        });

        takeCard.addEventListener('click', function () {
            fetch('game?action=take', {
                method: "POST"
            })
                .then(function (data) {
                });
        });
    // }
    // else {
    //     alert.style.opacity = "1";
    //     alertMessage.innerHTML = "Trebuie sa umfli si dupa poti da next!";
    // }

    finishGame.addEventListener('click', function () {
        fetch('game?action=finish', {
            method: "POST"
        })
            .then(function (data) {
                window.location.href = data.url;
            });
    });

    startGame.addEventListener('click', function () {
        var currentUser = startGame.getAttribute("currentUser");
        var currentSessionUser = startGame.getAttribute("currentSessionUser");

        if (currentUser === currentSessionUser) {
            fetch('game?isStarted= ' + 1, {
                method: "POST"
            })
                .then(function (data) {
                });
        }

        else {
            alert.style.opacity = "1";
            alertMessage.innerHTML = "Numai organizatorul poate sa dea start la joc!";
        }
    });


    closeAlert.addEventListener('click', function () {
        alert.style.opacity = "0";
    });

</script>

</body>
</html>
