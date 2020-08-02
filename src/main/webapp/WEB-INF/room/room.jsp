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

    <title>Room ${param.roomId}</title>
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
                    <h5 class="modal-title text-danger"
                        id="timeInputsModalLabel">Incorrect Time Range!</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    You can only filter rooms from 09:00 o'clock to 09:00 o'clock of the next day.
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
            <a class="text-light" style="font-size: 1.2rem;"
               href="${pageContext.request.contextPath}/homepage-gv?floor=1">RRS</a>
        </div>

        <%-- Container for user dropdown toggler --%>
        <div class="d-flex justify-content-end align-items-center p-4" style="width: 100px; height: 100%;">
            <div class="dropdown">
                <a class="dropdown-toggle text-muted" href="#" style="font-size: 1.2rem" id="userMenu"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${user.username}
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
    <div class="d-flex flex-column justify-content-center align-items-center bg-light"
         style="width: 100%; height: 90%;">

        <%-- Container for room title --%>
        <div class="d-flex justify-content-between align-items-center" style="width: 70%;">
            <h2>Room ${param.id}</h2>
        </div>

        <%-- Container for room view --%>
        <span class="d-flex justify-content-center align-items-center border rounded p-4"
              style="border-width: 1.5px !important; width: 70%; height: 550px;">

            <%-- Container for room information --%>
            <div class="d-flex flex-column justify-content-center align-items-start bg-light mr-2"
                 style="width: 70%; height: 100%;">

                <%-- Room diagram and properties --%>
                <div class="d-flex flex-row justify-content-start align-items-center bg-light"
                     style="width: 100%; height: 50%;">

                    <%-- Room diagram --%>
                    <div class="d-flex flex-row justify-content-center align-items-center bg-light border rounded mr-4"
                         style="width: 40%; height: 100%;">
                        <img src="/resources/images/room-${room.roomSize}.png" class="img-fluid" alt="Room Diagram"
                             width="auto" height="auto" style="max-height: 100%; max-width: 100%">
                    </div>

                    <%-- Room properties --%>
                    <div class="d-flex flex-column justify-content-center align-items-center bg-light"
                         style="width: 50%; height: 100%;">
                        <div class="d-flex flex-row justify-content-start align-items-center"
                             style="width: 100%; font-size: x-large">
                            <b class="mr-2">${room.occupied ? "Reserved" : "Available"}</b>
                            <svg height="20" width="20">
                                <circle cx="10" cy="10" r="8" fill="${room.occupied ? "#d9534f" : "#28a745"}"></circle>
                            </svg>
                        </div>
                        <br>
                        <div class="d-flex flex-row justify-content-start align-items-center"
                             style="width: 100%; height: 100%;">
                            Room ID: &nbsp <b>${room.roomId}</b>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-center"
                             style="width: 100%; height: 100%;">
                                Floor: &nbsp
                                <c:forEach var="i" begin="1" end="4">
                                    <a class="btn disabled btn-${i == room.floor ? "primary" : "outline-secondary"} btn-sm mr-1">${i}</a>
                                </c:forEach>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-center"
                             style="width: 100%; height: 100%;">
                            Size: &nbsp
                            <c:forEach var="i" begin="1" end="4">
                                <a class="btn disabled btn-${i == room.roomSize ? "primary" : "outline-secondary"} btn-sm mr-1">
                                        ${i == 1 ? "Mini" : i == 2 ? "Small" : i == 3 ? "Medium" : "Large"}</a>
                            </c:forEach>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-end"
                             style="width: 100%; height: 100%;">
                            <div class="checkbox disabled">
                              <label>Air Conditioning:  &nbsp<input type="checkbox" value=""
                                                                    disabled ${room.conditioner ? "checked" : ""}></label>
                            </div>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-start"
                             style="width: 100%; height: 100%;">
                            <div class="checkbox disabled">
                              <label>Projector: &nbsp<input type="checkbox" value=""
                                                            disabled ${room.projector ? "checked" : ""}></label>
                            </div>
                        </div>
                    </div>

                </div>

                <%-- Room Comment --%>
                <div class="d-flex flex-column border rounded mt-3"
                     style="width: 100%; height: 30%; background-color: #F0F0F0">
                    <div class=" d-flex flex-column justify-content-center rounded"
                         style="width: 100%; height: 30%; background-color: #F0F0F0">
                        <text class="ml-4"
                              style="color: darkslategrey !important; font-size: smaller">${roomCommentDate}</text>
                    </div>
                    <form:form cssClass="m-0"
                               action="${pageContext.request.contextPath}/room?id=${param.id}"
                               method="post"
                               id="commentForm">
                        <input type="hidden" name="comment">
                        <textarea class="form-control" id="exampleFormControlTextarea1" maxlength="255" rows="4"
                                  name="commentString"
                            ${(room.occupied && currentReservation.accountId == user.primaryKey) ? "" : "disabled"}
                                  style="border-width: 0; resize: unset; background-color: white; outline: 0 !important; -webkit-appearance: none; box-shadow: none !important">${roomComment}</textarea>
                    </form:form>
                </div>

                <%-- Room Reservation Panel --%>
                <div class="d-flex flex-row align-items-center bg-light"
                     style="width: 100%; height: 15%;">
                    <%-- Time Range Entry --%>
                    <form:form cssClass="form-inline flex-nowrap m-0"
                               action="${pageContext.request.contextPath}/room?id=${param.id}"
                               method="post"
                               id="timeRangeForm">

                        <label for="fromTime" class="mr-2">From:</label>
                        <input id="fromTime" class="form-control mr-2" name="fromTime" type="time">
                        <label for="toTime" class="mr-2">To:</label>
                        <input id="toTime" class="form-control mr-2" name="toTime" type="time">
                        <input type="hidden" name="reserve">

                        <button class="btn btn-primary" type="button" onclick="checkReservationTime()">Reserve</button>
                    </form:form>

                    <text class="ml-2 text-danger"
                          style="font-size: medium;">${invalidReservation ? "Error Already Reserved!" : ""}</text>

                    <button class="btn btn-primary ml-auto" type="submit" name="commentBtn"
                            form="commentForm" ${(room.occupied && currentReservation.accountId == user.primaryKey) ? "" : "disabled"}>Comment</button>
                </div>
            </div>

            <%-- Container for room reservation table --%>
            <div class="d-flex flex-column align-items-end justify-content-center bg-light"
                 style="width: 30%; height: 100%;">

                <%-- Helper container for room reservation table --%>
                <div class="d-flex overflow-auto bg-light"
                     style="width: 100%; height: 100%;">

                        <%-- Container for room reservation table --%>
                        <div class="d-flex overflow-auto bg-light"
                             style="width: 100%; height: 100%;">

                            <%-- Hourly division for reservation table --%>
                            <svg x="0%" y="0%" width="100%" height="120%">
                                <svg x="0%" y="0%" width="100%" height="100%">
                                    <c:forEach var="i" begin="1" end="23">
                                        <svg y="${(i/24)*100}%" width="100%" height="${100/24}%">
                                            <line x1="15%" y1="0%" x2="100%" y2="0%" stroke="#484848" opacity="10%"
                                                  stroke-width="2px"></line>
                                        </svg>
                                    </c:forEach>
                                </svg>

                            <%-- Timeline list for room reservation table --%>
                                <svg x="0%" y="0%" width="20%" height="100%">
                                    <c:forEach var="i" begin="10" end="32">
                                        <svg y="${((i-9.5)/24)*100}%" width="100%" height="${100/24}%">
                                            <text x="50%" y="60%" dominant-baseline="middle" text-anchor="middle"
                                                  opacity="${(((i-9)/24 - currentTimeRelativePosition < 1/48) && ((i-9)/24 - currentTimeRelativePosition > -1/48)) ? 20 : 100}%"
                                                  style="fill: #6c757d;"
                                                  font-size="0.9rem">
                                                    ${(i >= 24) ? (i - 24) : i}</text>
                                        </svg>
                                    </c:forEach>
                                </svg>

                                <%-- Room reservation table --%>
                                <svg x="18%" y="0%" width="79%" height="100%">
                                    <c:forEach var="timePortion" items="${timePortions}">
                                        <svg y="${timePortion.relativePosition * 100}%" width="100%"
                                             height="${timePortion.timePortion * 100}%">
                                            <rect x="0" y="0" rx="3" ry="3"
                                                  width="100%"
                                                  height="100%"
                                                  style="fill: #d9534f; opacity: 80%">
                                        <title>${timePortion.reservation.startDate.substring(10)} -${timePortion.reservation.endDate.substring(10)}</title>
                                            </rect>
                                        </svg>
                                    </c:forEach>
                                </svg>

                                <%-- Current Time Indicator --%>
                                <svg x="0%" y="0%" width="100%" height="100%">
                                    <text x="2%" y="${currentTimeRelativePosition*100 + 0.3}%"
                                          dominant-baseline="middle" text-anchor="start"
                                          style="fill: #000000; font-size: smaller"
                                          font-weight="bold">${currentTime}</text>
                                    <line x1="15%" y1="${currentTimeRelativePosition*100}%" x2="100%"
                                          y2="${currentTimeRelativePosition*100}%" stroke="#484848"></line>
                                    <svg x="15%" y="${(currentTimeRelativePosition-(1/48))*100}%" height="20"
                                         width="20">
                                        <circle cx="4" cy="12" r="4" fill="#484848"></circle>
                                    </svg>
                                </svg>
                            </svg>
                        </div>
                </div>




            </div>

        </span>
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
