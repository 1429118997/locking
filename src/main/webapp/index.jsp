<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script
  src="http://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<script type="text/javascript">
   
   $(function(){
	   for(var i=0;i<20000;i++){
		   $.post({
			   url:"${pageContext.request.contextPath}/redis/saylocking?redpackedId=3&userId="+i,
			   success:function(result){
				   
			   }
		   });
	   }
   })
</script>
</body>
</html>