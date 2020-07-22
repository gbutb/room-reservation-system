<%--
  Created by IntelliJ IDEA.
  User: dadak
  Date: 17.07.2020
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Floor ${param.floor}</title>
</head>

<body>
<div class="d-flex flex-column bg-success" style="width: 100%; height: 100vh; min-width: 1280px; min-height: 720px;">
    <div class="d-flex flex-row justify-content-between align-items-center sticky-top bg-dark"
         style="width: 100%; height: 10%;">
        <div class="d-flex justify-content-start align-items-center p-4" style="width: 390px; height: 100%;">
            <a class="text-light" style="font-size: 1.25rem" href="#">Room Reservation System</a>
        </div>

        <div class="d-flex justify-content-center align-items-center" style="width: 500px; height: 100%;">
            <form class="form-inline flex-nowrap m-0">
                <label for="fromFilter" class="text-light bg-dark mr-2">From:</label>
                <input id="fromFilter" class="form-control mr-4" type="time">
                <label for="toFilter" class="text-light bg-dark mr-2">To:</label>
                <input id="toFilter" class="form-control mr-2" type="time">
                <button class="btn btn-primary" type="submit">Filter</button>
            </form>
        </div>

        <div class="d-flex justify-content-end align-items-center p-4" style="width: 390px; height: 100%;">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-muted" href="#" style="font-size: 1.2rem"
                       id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        User
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Settings</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Log Out</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="d-flex flex-row justify-content-between align-items-start bg-light" style="width: 100%; height: 90%;">
        <div class="d-flex bg-secondary shadow" style="width: 10%; height: 100%;">
            <ul class="nav d-flex flex-column justify-content-center align-items-center w-100 h-100">
                <c:forEach var="btnId" begin="1" end="4">
                    <li class="nav-item pb-3">
                        <a href="?floor=${btnId}">
                            <button type="button" class="btn ${param.floor == btnId ? 'btn-primary' : 'btn-dark'}" style="width: 80px; height: 80px; font-size: 2rem;">
                                ${btnId}
                            </button>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="d-flex flex-column bg-light justify-content-center align-items-center"
             style="width: 90%; height: 100%;">

            <div class="d-flex justify-content-start align-items-center" style="width: 1024px;">
                <h2>Placeholder</h2>
            </div>

            <span class="d-flex justify-content-center align-items-center border rounded"
                  style="border-width: 1.5px !important; width: 1050px; height: 506px;">

                <img src="${pageContext.request.contextPath}/resources/images/floor-${param.floor}.png" class="img"
                     style="width: 1030px; height: 486px;" alt="..."/>

                <svg class="position-absolute" width="1024" height="480">
                    <c:forEach var="roomID" items="${rooms.keySet()}">
                        <svg x="${rooms.get(roomID).get(0)}"
                             y="${rooms.get(roomID).get(1)}"
                             width="${rooms.get(roomID).get(2)}"
                             height="${rooms.get(roomID).get(3)}">
                            <a href="#">
                                <rect x="0" y="0" rx="5" ry="5"
                                      width="${rooms.get(roomID).get(2)}"
                                      height="${rooms.get(roomID).get(3)}"
                                      style="fill: mediumseagreen;">
                                </rect>
                                <text class="text text-primary" x="50%" y="50%" dominant-baseline="middle"
                                      text-anchor="middle">${roomID}</text>
                            </a>
                        </svg>
                    </c:forEach>
                </svg>
            </span>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
