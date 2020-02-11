<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${param.lang}">
<head>
    <title>PhraseApp - i18n</title>
</head>
<body>

<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=gr"><fmt:message key="label.lang.de" /></a></li>
</ul>

<h1>
    <fmt:message key="welcome.message" />
</h1>

<p>
    This is a simple educational project, a crude application using a text file as a database.
    The application implements the main essence of the developer and its subtleties: skill and account.
    The developer may contain a list of skill numbers and one account number.
    Each type of entity is stored in different files.
</p>
<img src="petprojectUML.png" />
</body>
</html>