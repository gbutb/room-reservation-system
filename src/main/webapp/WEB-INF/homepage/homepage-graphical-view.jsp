<%--
  Created by IntelliJ IDEA.
  User: dadak
  Date: 17.07.2020
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Homepage</title>

    <meta http-equiv="refresh" content="300"> <%-- Refresh page every 5 minutes --%>
</head>

<body>
<%-- Container for entire screen --%>
<div class="d-flex flex-column" style="width: 100%; height: 100vh; min-width: 1280px; min-height: 720px;">

    <%-- Modal for wrong time inputs --%>
    <div class="modal fade" id="timeInputsModal" tabindex="-1" role="dialog"
         aria-labelledby="timeInputsModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-danger" id="timeInputsModalLabel">Incorrect Time Range!</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    You can only filter rooms from current time to 09:00 o'clock in the morning.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
                </div>
            </div>
        </div>
    </div>

    <%-- Container for top navigation bar --%>
    <div class="d-flex flex-row justify-content-between align-items-center sticky-top bg-dark"
         style="width: 100%; height: 10%;">

        <%-- Container for navigation bar branding --%>
        <div class="d-flex justify-content-start align-items-center p-4" style="width: 100px; height: 100%;">
            <a class="text-light" style="font-size: 1.2rem;" href="${pageContext.request.contextPath}/homepage-gv?floor=1">RRS</a>
        </div>

        <%-- Container for search and filter --%>
        <div class="d-flex justify-content-center align-items-center" style="width: 1000px; height: 100%;">

            <%-- Search by room number --%>
            <form class="form-inline flex-nowrap m-0" action="${pageContext.request.contextPath}/room" method="get">
                <label for="roomSearch" class="text-light bg-dark mr-2">Room:</label>
                <input id="roomSearch" class="form-control mr-2" name="id" type="number" required style="width: 100px;">
                <button class="btn btn-primary mr-5" type="submit">Search</button>
            </form>

            <%-- Time based filter and advanced filter --%>
            <form:form cssClass="form-inline flex-nowrap m-0"
                  action="${pageContext.request.contextPath}/homepage-gv?floor=${param.floor}"
                  method="post"
                  id="timeRangeForm">

                <label for="fromTime" class="text-light bg-dark mr-2">From:</label>
                <input id="fromTime" class="form-control mr-2" name="fromTime" type="time">
                <label for="toTime" class="text-light bg-dark mr-2">To:</label>
                <input id="toTime" class="form-control mr-2" name="toTime" type="time">

                <div class="btn-group">
                    <button class="btn btn-primary" type="button" onclick="checkTime()">Filter</button>
                    <button class="btn btn-primary dropdown-toggle" type="button" id="advancedSearch"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="advancedSearch">
                        <h6 class="dropdown-header p-0 ml-2 mb-1">Must include:</h6>
                        <div class="d-flex justify-content-start align-items-center" style="width: 315px;">
                            <input type="checkbox" name="hasAirConditioner" class="form-check-input ml-3" id="hasAirConditioner">
                            <label class="form-check-label" for="hasAirConditioner">
                                Air Conditioner
                            </label>
                        </div>
                        <div class="d-flex justify-content-start align-items-center">
                            <input type="checkbox" name="hasProjector" class="form-check-input ml-3"
                                   id="hasProjector">
                            <label class="form-check-label" for="hasProjector">
                                Projector
                            </label>
                        </div>
                        <h6 class="dropdown-header p-0 ml-2 mb-1 mt-2">Room size:</h6>
                        <div class="d-flex justify-content-start align-items-center">
                            <input type="checkbox" name="roomSize1" class="form-check-input ml-3" id="roomSize1">
                            <label class="form-check-label" for="roomSize1">
                                Mini
                            </label>

                            <input type="checkbox" name="roomSize2" class="form-check-input ml-3" id="roomSize2">
                            <label class="form-check-label" for="roomSize2">
                                Small
                            </label>

                            <input type="checkbox" name="roomSize3" class="form-check-input ml-3" id="roomSize3">
                            <label class="form-check-label" for="roomSize3">
                                Medium
                            </label>

                            <input type="checkbox" name="roomSize4" class="form-check-input ml-3" id="roomSize4">
                            <label class="form-check-label" for="roomSize4">
                                Large
                            </label>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>

        <%-- Container for user dropdown toggler --%>
        <div class="d-flex justify-content-end align-items-center p-4" style="width: 100px; height: 100%;">
            <div class="dropdown">
                <a class="dropdown-toggle text-muted" href="#" style="font-size: 1.2rem" id="userMenu"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${username}
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/settings">Settings</a>
                    <div class="dropdown-divider"></div>
                    <form:form cssClass="m-0" action="/logout" method="post">
                        <input class="dropdown-item" type="submit" value="Log Out">
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <%-- Container for bottom space after navigation bar --%>
    <div class="d-flex flex-row justify-content-between align-items-start bg-light" style="width: 100%; height: 90%;">

        <%-- Container for left sidebar --%>
        <div class="d-flex bg-secondary shadow" style="width: 8%; height: 100%;">
            <ul class="nav d-flex flex-column justify-content-center align-items-center w-100 h-100">
                <c:forEach var="btnId" begin="1" end="4">
                    <li class="nav-item pb-3">
                        <a href="?floor=${5 - btnId}">
                            <button type="button" class="btn ${param.floor == (5 - btnId) ? 'btn-primary' : 'btn-dark'}"
                                    style="width: 80px; height: 80px; font-size: 2rem;">
                                    ${5 - btnId}
                            </button>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <%-- Container for right space after sidebar --%>
        <div class="d-flex flex-column bg-light justify-content-center align-items-center"
             style="width: 92%; height: 100%;">

            <%-- Container for title --%>
            <div class="d-flex justify-content-between align-items-center" style="width: 1162px;">
                <h2>Graphical View</h2>

                <a href="${pageContext.request.contextPath}/homepage-lv" role="button"
                   class="btn btn-outline-secondary btn-sm">List View</a>
            </div>

            <%-- Container for graphical view --%>
            <span class="d-flex justify-content-center align-items-center border rounded"
                  style="border-width: 1.5px !important; width: 1162px; height: 550px;">

                <img src="${pageContext.request.contextPath}/resources/images/floor-${param.floor}.png" class="img"
                     style="width: 1152px; height: 540px;" alt="..."/>

                <svg class="position-absolute" width="1152" height="540">
                    <c:forEach var="room" items="${allRooms}">
                        <svg x="${renderData.get(room.roomId).get(0)}"
                             y="${renderData.get(room.roomId).get(1)}"
                             width="${renderData.get(room.roomId).get(2)}"
                             height="${renderData.get(room.roomId).get(3)}">
                            <rect x="0" y="0" rx="5" ry="5"
                                  width="${renderData.get(room.roomId).get(2)}"
                                  height="${renderData.get(room.roomId).get(3)}"
                                  style="fill: #c4c8ca;">
                            </rect>

                            <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle"
                                  style="fill: #f8f9fa; opacity: 75%;">${room.roomId}</text>
                        </svg>
                    </c:forEach>
                </svg>

                <svg class="position-absolute" width="1152" height="540">
                    <c:forEach var="room" items="${filteredRooms}">
                        <svg x="${renderData.get(room.roomId).get(0)}"
                             y="${renderData.get(room.roomId).get(1)}"
                             width="${renderData.get(room.roomId).get(2)}"
                             height="${renderData.get(room.roomId).get(3)}">
                            <a href="${pageContext.request.contextPath}/room?id=${room.roomId}">
                                <rect x="0" y="0" rx="5" ry="5"
                                      width="${renderData.get(room.roomId).get(2)}"
                                      height="${renderData.get(room.roomId).get(3)}"
                                      style="fill: ${room.occupied == true ? '#dc3545' : '#28a745'};">
                                </rect>

                                <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle"
                                      style="fill: #f8f9fa;">${room.roomId}</text>
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
<script src="../../resources/time-range-controller.js"></script>
</body>
</html>
