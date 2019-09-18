<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<title>NIC : Forgot Password</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<c:url value="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet">
	<link rel="icon" href="<c:url value="/static/nic_icon.png"/>" type="image/gif" sizes="16x16">
	<style>
		@media screen and (max-width: 480px){
			#btn{
				white-space: normal;			
				}

			#brand{
				display:none;
			}

			#mobileversion{
				width:90%;
			}

			#button_mobile{
				float:left;
			}

		}

		body {
  			padding-top: 70px;
			}

		#ch:hover{
			background-color:#337ab7;
		}
		.rule{
			height: 10px;
			border: 0;
			box-shadow: 0 10px 10px -10px #8c8b8b inset;
		}
		#chactive{
			background-color:#337ab7; 
		}

		.form-group{
			text-align:left;
		}

		.desktopversion{
			background-color:#eee;
			border-radius:5px;
			width:50%;
			margin-top:20px;
			margin-bottom:20px;"
		}
}
	</style>
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<div><img src="<c:url value="/static/nic_logo.png"/>" style="height:30px; float:left;margin-top:10px;"><a id="brand" style="color:white" class="navbar-brand" href="javaScript:void(0);">&nbsp;&nbsp;Electronics Niketan</a></div>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a id="ch" style="color:white" href="<c:url value="/login"/>"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
				<li><a id="ch"  style="color:white" href="<c:url value="/signup"/>">Register New Account</a></li>
				<li><a id="ch" style="color:white" href="#" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-phone-alt"></span>&nbsp;Contact</a></li>
			</ul>
		</div>
	</div>
</nav>

<div class="container" style="text-align:center;">
	<h3>Forgot Password</h3>
</div>
<div class="container-fluid"><hr class="rule"></div>


<c:url value="/forgotsteptwo?userid=${userid}&${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
<div class="container-fluid desktopversion" id="mobileversion" style="margin:0 auto">
	<form style="padding-top:10px;padding-bottom:10px;" method="post" action="${post_url}">
	<h3 style="margin:0 auto">Welcome ${name}.</h3>
	

  	<div class="form-group">
    <label for="password">Enter New Password</label>
    <input onkeyup='check();' type="password" class="form-control" id="password" name="password" pattern=".{6,}" title="Password should be atleast 6 digits" placeholder="Password" required="required" >
    
    </div>
  	
  	<div class="form-group">
    <label for="passwordConfirm">Re-Enter New Password</label>
    <input onkeyup='check();' type="password" class="form-control" pattern=".{6,}" title="Password should be atleast 6 digits" id="passwordConfirm" name="passwordConfirm" placeholder="Re-Enter Password" required="required" />
  			<span class="input-group-addon"><span id='message'></span></span>
    </div>

  	<button type="submit" class="btn btn-primary center-block" style="margin-bottom:10px;">Verify and Update</button>
	
	<input type="hidden" name="key" value="${key}"/>
	<font color="red"><b>${message}</b></font>
  	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
  	</form>

  	

</div>


<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#A2C1E3">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Contact Information for Help</h4>
      </div>
      <div class="modal-body">
        <h3>Operator Details:</h3>
        <p>Phone Number: +91 XX XX XXXXXX</p>
        <p>
        Address:<br>
        Ministry of Electronics and Information Technology<br>
		(Government of India)<br>
		Electronics Niketan, 6, CGO Complex,<br>
		Lodhi Road, New Delhi - 110003 <br>
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


<script>
var check = function() {
	  if (document.getElementById('password').value ==
	    document.getElementById('passwordConfirm').value) {
	    document.getElementById('message').style.color = 'green';
	    document.getElementById('message').setAttribute("class","glyphicon glyphicon-ok");
	  } else {
	    document.getElementById('message').style.color = 'red';
	    document.getElementById('message').setAttribute("class","glyphicon glyphicon-remove");
	  }
	}
	
	
var input3 = document.getElementById('password');
input3.oninvalid = function(event) {
    event.target.setCustomValidity('Password should be 6 to 20 digits long');
}
var input4 = document.getElementById('passwordConfirm');
input4.oninvalid = function(event) {
    event.target.setCustomValidity('Password should be 6 to 20 digits long');
}
</script>

<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>