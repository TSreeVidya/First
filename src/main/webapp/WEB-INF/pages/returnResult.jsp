
<%
String bookInfo = (String) request.getAttribute("book");
response.setContentType("text/html");
response.getWriter().write(bookInfo);
response.getWriter().flush();
%>