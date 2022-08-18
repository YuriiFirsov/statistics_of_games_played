<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title> Главная </title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-center">
    <div class="w3-col w3-container" style="width:20%">
    </div>
    <div class="w3-col w3-container" style="width:60%">
        <h2>Статистика проведенных игр</h2>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>

</div>

<div class="w3-container">

    <div class="w3-col w3-container" style="width:40%">
        <h3> Чтобы получить информацию по конкретной игре:</h3>
        <form:form action="show_info_selected_game" method="get">
            Выберите игру:
            <select name="selectedGame">
                <c:forEach var="game" items="${games}">
                    <option value="${game.name}">
                            ${game.name}
                    </option>
                </c:forEach>
            </select>
            <input class="w3-btn w3-border w3-round w3-blue" type="submit" value="Показать информацию"/>
        </form:form>
    </div>

    <div class="w3-col w3-container" style="width:25%">

        <h3> Список игр</h3>

        <table class="w3-table-all w3-centered w3-card-4">
            <tr class="w3-blue">
                <th>Название</th>
                <th>Удалить</th>
            </tr>

            <c:forEach var="game" items="${games}">

                <c:url var="deleteGameButton" value="/delete_game">
                    <c:param name="gameId" value="${game.id}"/>
                </c:url>

                <tr>
                    <td>${game.name}</td>
                    <td>
                        <input type="button" value="Удалить" onclick="window.location.href = '${deleteGameButton}'">
                    </td>
                </tr>

            </c:forEach>

        </table>

        <input class="w3-btn w3-border w3-round w3-blue" type="button" value="Добавить игру"
               onclick="window.location.href = 'add_game'"/>


    </div>

    <div class="w3-col w3-container" style="width:35%">
        <h3> Список игроков</h3>

        <table class="w3-table-all w3-centered w3-card-4">
            <tr class="w3-blue">
                <th>Username</th>
                <th>Имя</th>
                <th>Всего игр</th>
                <th>Удалить</th>
            </tr>

            <c:forEach var="player" items="${players}">

                <c:url var="deletePlayerButton" value="/delete_player">
                    <c:param name="playerId" value="${player.id}"/>
                </c:url>

                <tr>
                    <td>${player.username}</td>
                    <td>${player.name}</td>
                    <td>${player.count}</td>
                    <td>
                        <input type="button" value="Удалить" onclick="window.location.href= '${deletePlayerButton}'">
                    </td>
                </tr>
            </c:forEach>
        </table>

        <input class="w3-btn w3-border w3-round w3-blue" type="button" value="Добавить игрока"
               onclick="window.location.href ='add_player'">
    </div>
</div>

<div class="w3-container">

    <div class="w3-col w3-container" style="width:100%">
        <div class="w3-container">
            <div class="w3-col w3-container" style="width:20%">
                <input class="w3-btn w3-border w3-round w3-blue" type="button" value="Добавить партию"
                       onclick="window.location.href ='add_party'">
            </div>
            <div class="w3-col w3-container w3-center" style="width:60%">
                <h3> Список сыгранных игр</h3>
            </div>
            <div class="w3-col w3-container" style="width:20%"></div>

        </div>

        <table class="w3-table-all w3-centered w3-card-4">
            <tr class="w3-blue">
                <th>Номер</th>
                <th>Игра</th>
                <th>Время</th>
                <th>Игрок 1:</th>
                <th>очки</th>
                <th>Игрок 2:</th>
                <th>очки</th>
                <th>Игрок 3:</th>
                <th>очки</th>
                <th>Игрок 4:</th>
                <th>очки</th>
                <th>Изменить</th>
                <th>Удалить</th>
            </tr>

            <c:forEach var="party" items="${parties}" varStatus="number">

                <c:url var="deletePartyButton" value="/delete_party">
                    <c:param name="partyId" value="${party.id}"/>
                </c:url>

                <c:url var="updatePartyButton" value="/update_party">
                    <c:param name="partyId" value="${party.id}"/>
                </c:url>

                <tr>
                    <td>${number.count}</td>
                    <td>${party.gameName}</td>
                    <td>${party.partyDate}</td>

                    <c:forEach var="i" begin="0" end="3">
                        <c:catch var="exception">
                            <td>${party.resultDtoList.get(i).playerName}</td>
                            <td>${party.resultDtoList.get(i).playerScore}</td>
                        </c:catch>
                        <c:if test="${exception != null}">
                            <td></td>
                        </c:if>

                    </c:forEach>

                    <td>
                        <input type="button" value="Изменить" onclick="window.location.href= '${updatePartyButton}'">
                    </td>

                    <td>
                        <input type="button" value="Удалить" onclick="window.location.href= '${deletePartyButton}'">
                    </td>
                </tr>
            </c:forEach>

        </table>

    </div>
</div>


</body>
</html>