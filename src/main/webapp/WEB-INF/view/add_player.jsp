<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Добавление игрока</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-center">
    <div class="w3-col w3-container" style="width:20%">
        <c:url var="index" value="/index"></c:url>
        <button class="w3-btn w3-round-large" onclick="window.location.href='${index}'">На главную</button>
    </div>
    <div class="w3-col w3-container" style="width:60%">
        <h2>Добавление нового игрока</h2>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>

</div>
<div>
    <div class="w3-col w3-container" style="width:20%"></div>
    <div class="w3-col w3-container" style="width:60%">
        <form:form action="save_new_player" modelAttribute="player">
            <br><br>
            Введите UserName игрока: <form:input path="username"/>
            <form:errors path="username"/>
            <br><br>
            Введите имя игрока: <form:input path="name"/>
            <form:errors path="name"/>
            <br><br>
            <input class="w3-btn w3-border w3-round w3-blue" type="submit" value="Добавить игрока">

        </form:form>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>
</div>


</body>
</html>
