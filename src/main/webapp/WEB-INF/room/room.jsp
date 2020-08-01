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
                        id="timeInputsModalLabel"><%-- TODO: Error message title --%></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <%-- TODO: Error message content --%>
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
            <a class="text-light" style="font-size: 1.2rem;" href="#">RRS</a>
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
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Log Out</a>
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
            <div class="d-flex flex-column justify-content-center align-items-start bg-light"
                 style="width: 70%; height: 100%;">

                <%-- Room diagram and properties --%>
                <div class="d-flex flex-row justify-content-start align-items-center bg-light"
                     style="width: 100%; height: 50%;">

                    <%-- Room diagram --%>
                    <div class="d-flex flex-row justify-content-center align-items-center bg-light border mr-4"
                         style="width: 40%; height: 100%; border-radius: 15px">
                        <img src="/resources/images/room-${room.roomSize}.jpg" alt="Room Diagram">
                    </div>

                    <%-- Room properties --%>
                    <div class="d-flex flex-column justify-content-center align-items-center bg-light" style="width: 50%; height: 100%;">
                        <div class="d-flex flex-row justify-content-start align-items-center" style="width: 100%; font-size: x-large">
                            <svg height="20" width="20">
                                <circle cx="10" cy="10" r="8" fill= "${room.occupied ? "#d9534f" : "#28a745"}"></circle>
                            </svg> <b>${room.occupied ? "Reserved" : "Available"}</b>
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
                                    <button type="button" class="btn disabled btn-${i == room.floor ? "primary" : "outline-secondary"} btn-sm mr-1">${i}</button>
                                </c:forEach>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-center"
                             style="width: 100%; height: 100%;">
                            Size: &nbsp
                            <c:forEach var="i" begin="1" end="4">
                                <button type="button" class="btn disabled btn-${i == room.roomSize ? "primary" : "outline-secondary"} btn-sm mr-1">
                                        ${i == 1 ? "Mini" : i == 2 ? "Small" : i == 3 ? "Medium" : "Large"}</button>
                            </c:forEach>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-end"
                             style="width: 100%; height: 100%;">
                            <div class="checkbox disabled">
                              <label>Air Conditioning:  &nbsp<input type="checkbox" value="" disabled ${room.conditioner ? "checked" : ""}></label>
                            </div>
                        </div>
                        <div class="d-flex flex-row justify-content-start align-items-start"
                             style="width: 100%; height: 100%;">
                            <div class="checkbox disabled">
                              <label>Projector: &nbsp<input type="checkbox" value="" disabled ${room.projector ? "checked" : ""}></label>
                            </div>
                        </div>
                    </div>

                </div>

                <%-- Room Comment --%>
                <div class="d-flex flex-column justify-content-start align-items-start bg-light border rounded mt-5 p-3"
                     style="width: 95%; height: 25%;">
                    Latest Comment --
                </div>

                <%-- Room Reservation --%>
                <div class="d-flex flex-row justify-content-start align-items-center bg-light border rounded"
                     style="width: 95%; height: 15%;">
                </div>

            </div>

            <%-- Container for room reservation table --%>
            <div class="d-flex flex-column align-items-center justify-content-center bg-light"
                 style="width: 30%; height: 100%;">

                Room Reservations<br>
                <%-- Helper container for room reservation table --%>
                <div class="d-flex flex-column overflow-auto align-items-center justify-content-center bg-light"
                     style="width: 100%; height: 100%;">


                    <%-- Container for room reservation table --%>
                    <div class="d-flex flex-row align-items-start justify-content-center bg-light"
                         style="width: 100%; height: 100%;">

                        <%-- Timeline list for room reservation table --%>
                        <div class="d-flex flex-column align-items-center justify-items-start"
                             style="height: 120%; width: 20%;">
                            <c:forEach var="i" begin="9" end="32">
                                <svg width="100%" height="100%">

                                    <rect x="0" y="0" fill="transparent" width="100%" height="90%"></rect>
                                    <line x1="8%" y1="0%" x2="98%" y2="0%" stroke="#C8C8C8"></line>
                                    <line x1="8%" y1="100%" x2="98%" y2="100%" stroke="#C8C8C8"></line>
                                    <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle"
                                          style="fill: #484848;"
                                          font-size="0.9rem">${((i >= 24) ? (i - 24) : i)}:00-</text>
                                </svg>
                            </c:forEach>
                        </div>

                        <%-- Room reservation table --%>
                        <div class="d-flex flex-column align-items-center justify-items-start ml-2"
                             style="height: 120%; width: 80%;">

                            <svg x="0" y="50%" width="100%" height="1px">
                                <line x1="0%" y1="50%" x2="100%" y2="50%" stroke="#484848"></line>
                            </svg>
                            <c:forEach var="timePortion" items="${timePortions}">
                                <svg width="100%" height="${timePortion.timePortion * 100}%">
                                    <rect x="0" y="0" rx="6" ry="6"
                                          width="100%"
                                          height="100%"
                                          style="fill: ${timePortion.reservation ? '#d9534f' : 'transparent'};">
                                    </rect>
                                </svg>
                            </c:forEach>
                        </div>
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
</body>
</html>
