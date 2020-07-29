<!-- Load taglibs -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
        xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<body>
<h2>Hello World!</h2>
<form:form action="/logout" method="POST">
    <input type="submit" value="Sign Out"/>
</form:form>
</body>
</html>
