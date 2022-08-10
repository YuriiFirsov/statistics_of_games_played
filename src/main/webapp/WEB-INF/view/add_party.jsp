<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Добавление партии</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-center">
    <div class="w3-col w3-container" style="width:20%">
        <c:url var="index" value="/index"></c:url>
        <button class="w3-btn w3-round-large" onclick="window.location.href='${index}'">На главную</button>
    </div>
    <div class="w3-col w3-container" style="width:60%">
        <h2>Добавление результатов сыгранной партии</h2>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>

</div>

<div>
    <div class="w3-col w3-container" style="width:20%"></div>
    <div class="w3-col w3-container" style="width:30%">
        <form:form action="save_new_party" modelAttribute="party">
            
            <form:hidden path="id"/>
            <br><br>
            Выберите игру: <form:select path="gameName">
            <c:forEach var="game" items="${games}">
                <form:option value="${game.name}"/>
            </c:forEach>
        </form:select>

            <br><br>
            Игрок №1:<form:select path="player1">
            <form:option value="Выберите имя"/>
            <c:forEach var="player" items="${players}">
                <form:option value="${player.username}"/>
            </c:forEach>
        </form:select>
            Введите кол-во очков:<form:select path="score1">
            <form:option value="0"/>
            <form:option value="1"/>
            <form:option value="2"/>
            <form:option value="3"/>
        </form:select>

            <br><br>
            Игрок №2:<form:select path="player2">
            <form:option value="Выберите имя"/>
            <c:forEach var="player" items="${players}">
                <form:option value="${player.username}"/>
            </c:forEach>
        </form:select>
            Введите кол-во очков:<form:select path="score2">
            <form:option value="0"/>
            <form:option value="1"/>
            <form:option value="2"/>
            <form:option value="3"/>
        </form:select>

            <br><br>
            Игрок №3:<form:select path="player3">
            <form:option value="Выберите имя"/>
            <c:forEach var="player" items="${players}">
                <form:option value="${player.username}"/>
            </c:forEach>
        </form:select>
            Введите кол-во очков:<form:select path="score3">
            <form:option value="0"/>
            <form:option value="1"/>
            <form:option value="2"/>
            <form:option value="3"/>
        </form:select>

            <br><br>
            Игрок №4:<form:select path="player4">
            <form:option value="Выберите имя"/>
            <c:forEach var="player" items="${players}">
                <form:option value="${player.username}"/>
            </c:forEach>
        </form:select>
            Введите кол-во очков:<form:select path="score4">
            <form:option value="0"/>
            <form:option value="1"/>
            <form:option value="2"/>
            <form:option value="3"/>
        </form:select>

            <br><br>
            Введите время (при пустом поле запишется текущее время):<form:input placeholder="yyyy.MM.dd HH:mm" path="date"/>
            <form:errors path="date"/>

            <br><br>
            <input class="w3-btn w3-border w3-round w3-blue" type="submit" value="Сохранить">

        </form:form>
    </div>
    <div class="w3-col w3-container" style="width:30%">
        <br>
        <h4>Правила игры:</h4>
        Первый выбывший получает 0 очков, второй выбывший - 1 очко, третий - 2 очка,
        четвертый - 3 очка.<br>
        После выбывания первого игрока, остальные могут согласиться на ничью и закончить игру каждый с 1 очком.<br>
        После выбывания двух игроков два оставшихся могут согласиться на ничью и закончить игру каждый с 2 очками.<br>
        Если один из игроков выбывает по причине поражения от компьютера до заранее оговоренного времени
        (например до эпохи Античности в Civilization VI), то партия не засчитывается и очки не зачисляются

    </div>
    <div class="w3-col w3-container" style="width:20%"></div>
</div>


</body>
</html>
