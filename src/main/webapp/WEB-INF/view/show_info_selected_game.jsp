<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Результаты</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-center">
    <div class="w3-col w3-container" style="width:20%">
        <c:url var="index" value="/index"></c:url>
        <button class="w3-btn w3-round-large" onclick="window.location.href='${index}'">На главную</button>
    </div>
    <div class="w3-col w3-container" style="width:60%">
        <h2>Результаты по сыгранным партиям в ${param.selectedGame}</h2>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>

</div>
<div class="w3-container">

    <div class="w3-col w3-container" style="width:5%"></div>
    <div class="w3-col w3-container" style="width:45%">
        <h3> Список игроков</h3>

        <table class="w3-table-all w3-centered w3-card-4">
            <tr class="w3-blue">
                <th>Рейтинг</th>
                <th>Username</th>
                <th>Очки</th>
                <th>Сыграно игр</th>
            </tr>

            <c:forEach var="player" items="${players}" varStatus="rating">
                <tr>
                    <td>${rating.count}</td>
                    <td>${player.username}</td>
                    <td>${player.score}</td>
                    <td>${player.count}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="w3-col w3-container" style="width:50%"></div>
</div>


<div class="w3-container">

    <div class="w3-col w3-container" style="width:5%"></div>
    <div class="w3-col w3-responsive w3-container" style="width:90%">
        <h3> Список сыгранных игр</h3>
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
            </tr>

            <c:forEach var="party" items="${parties}" varStatus="number">

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

                </tr>
            </c:forEach>

        </table>
    </div>
    <div class="w3-col w3-container" style="width:5%"></div>

</div>


<div class="w3-responsive">

</div>

</body>
</html>
