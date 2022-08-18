<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Ошибка</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-center">
    <div class="w3-col w3-container" style="width:20%">
        <c:url var="index" value="/index"></c:url>
        <button class="w3-btn w3-round-large" onclick="window.location.href='${index}'">На главную</button>
    </div>
    <div class="w3-col w3-container" style="width:60%">
        <h2>Возникла ошибка</h2>
    </div>
    <div class="w3-col w3-container" style="width:20%"></div>
</div>
<div class="w3-container">

    <div class="w3-col w3-container" style="width:10%"></div>
    <div class="w3-col w3-container" style="width:80%">
        <h3>${error}</h3>
    </div>
    <div class="w3-col w3-container" style="width:10%"></div>

</div>


</body>
</html>
