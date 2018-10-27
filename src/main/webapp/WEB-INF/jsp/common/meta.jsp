<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<title>performanceMods Admin Tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<%
response.setHeader("Expires", "Tue, 01 Jan 2000 12:12:12 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Pragma", "no-store");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
%>
<sec:authentication property="principal" var="curUser" scope="request" />