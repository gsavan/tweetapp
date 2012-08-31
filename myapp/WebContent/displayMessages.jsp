c<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html>
<script type="text/javascript">
function linebreak(text, maxLen) {
   
   var out = "";
   var part1 = text, part2 = "";
   if (part1.length() > maxLen) {
      while (getTextWidth(part1) > maxLen) {
         var breakPos = binSearch(part1, maxLen);
         part2 = part1.substring(breakPos, part1.length);
         part1 = part1.substring(0, breakPos);
         out += part1 + "<br>";
         part1 = part2;
      }
      return out + part2;
   }
   else
      return text;
}

function binSearch(text, searchLen) {
   var left = 0, right = text.length;
   var breakPos = left, lastBreakPos = right;
   while (Math.abs(lastBreakPos - breakPos) > 1) {
      lastBreakPos = breakPos;
      breakPos = Math.floor((left+right)/2);
      if (searchLen < getTextWidth(text.substring(0, breakPos)))
         right = breakPos - 1;
      else
         left = breakPos + 1;
   }
   return Math.min(breakPos, lastBreakPos);
}

function getTextWidth(text) {
   var ea = document.createElement("span");
   ea.innerHTML = text;
   document.body.appendChild(ea);
   var len = ea.offsetWidth;
   document.body.removeChild(ea);
   return len;
}

window.onload = function () {
   var e = document.getElementById("wrap");
   e.innerHTML = linebreak(e.innerHTML, 200);
};
</script>
<script language="javascript" type="text/javascript">
var limitNum = 150;
function limitText(limitField, limitCount) {
	
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>
<script type="text/javascript">

  var productId = "00001";

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messages</title>
</head>
<body >
<form name="myform"  action="insert.do" >
<table align="center" width="60%" border="1" cellspacing="5">
<tr>
<td>Name</td><td><input type="text" name="uname"></td>
</tr>
<tr>
<td>Message*</td><td><textarea name="message" cols=50 rows=4 onKeyDown="limitText(this.form.message,this.form.countdown);" 
onKeyUp="limitText(this.form.message,this.form.countdown);">
</textarea></td>
</tr>
<tr>
<td><input type="submit" value="insert"></td><td><font size="1">(Maximum characters: 
	<script type="text/javascript">
  		document.write(limitNum+'). You have <input readonly type="text" name="countdown" size="3" value="'+limitNum+'"> characters left.</font>');
 	</script>
 	</font>
  <br></td>
</tr>
</table>
</form>
<form action="delete.do">
		<table align="center"  border="1" cellpadding="5" cellspacing="5">
        <tr>
        	<td>
        	<input type="hidden" name="page" value="${currentPage}"> 
			<input type="submit" value="delete">
        	</td>
	        <td><c:if test="${currentPage != 1}">
	        <td><a href="message.do?page=${currentPage - 1}">Previous</a></td>
		    </c:if>
		    </td>
		    
            <c:forEach  begin="1" end="${noOfPages}" var="i">
            
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="message.do?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
                
            </c:forEach>
            <td>
             <%--For displaying Next link --%>
    		<c:if test="${currentPage lt noOfPages}">
        	<td><a href="message.do?page=${currentPage + 1}">Next</a></td>
    		</c:if>
            </td>
            
        </tr>
    </table>
 		<font size="3" >
   		<c:forEach var="message" items="${messageList}">
   		<table  border="1" cellspacing="0" style="background:#D1D1F2" align="center" width="60%">
   		<col width="2%">
   		<col width="58%">
            <tr>
                <td><input type="checkbox" name="deletes" value = ${message.id }>  </td>
                <td colspan="2"><font size="1" color="0905FB"> ${message.uname}</font> ${message.message}<br><small>${message.timestamp}</small> </td>
            </tr>
        </table>   
        </c:forEach>
        </font>
   
 
    <%--For displaying Previous link except for the 1st page --%>
    
 
    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    
    </form>
   <table>
   		<tr>	
            <td>
            <form action="message.do">
            Page Size <input type="text" name="pageSize" value="${pageSize}">
            <input type="submit" value="change">
            </form> 
            </td>
        </tr>
   
   </table> 

</body>
</html>

