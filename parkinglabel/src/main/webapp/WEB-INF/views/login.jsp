<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>NIC : Parking Label</title>
	<link rel="icon" href="<c:url value="/static/nic_icon.png"/>" type="image/gif" sizes="16x16">
	
	<link href="<c:url value="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet">
	
	<link rel="stylesheet" href="<c:url value="/static/font-awesome-4.7.0/css/font-awesome.min.css"/>">

	<style>
		@media screen and (max-width: 480px){
		#btn{
			white-space: normal;			
			}

		#brand{
			display:none;
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
				<li><a id="ch" style="color:white" href="<c:url value="/signup"/>" >Register New Account</a></li>
				<li><a id="ch" style="color:white" href="#" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-phone-alt"></span>&nbsp;Contact</a></li>
			</ul>
		</div>
	</div>
</nav>




<div class="container-fluid">
	<div class="row">
  		<div class="col-md-9">
  			
  			<div class="jumbotron">
			 <div class="container">
				<h1>Generate Parking Label</h1>
				<p>Log in to generate your parking label form.</p>
				<br>
				<p><a id="btn" class="btn btn-primary btn-lg" href="<c:url value="/signup"/>" role="button">New Users Register Here »</a></p>
			 </div>
			</div>	

  		</div>
  		<div class="col-md-3">


  		<div class="jumbotron">
  		<c:url value="/performlogin" var="post_url"/>
             <form method="post" action="${post_url}">
             	<div style="margin:0 auto">
             	            
             	            <c:if test="${param.error != null}">
                                	<font color="red">Invalid Username or Password</font>
                            </c:if>
                            <c:if test="${param.logout != null}">
                                    <font color="red">Logged Out Successfully</font> <br>
                            </c:if>
                            
                            <c:if test="${param.success != null}">
                                <font color="green">Account Registered Successfully</font>
                            </c:if>
                            <c:if test="${param.successchange != null}">
                                <font color="green">Email for Changing Password Sent</font>
                            </c:if>
                            <c:if test="${param.updatesuccess != null}">
                                <font color="green">Password Successfully Changed</font>
                            </c:if>
                             <br>
                			<div class="input-group input-md">
                                <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="Enter User ID" required>
                            </div>
                            <br>
                            <div class="input-group input-md">
                                <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
                                <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                            </div>
                           
                            
                            <br>   
                            <div class="form-actions">
                                <input type="submit"
                                    class="btn btn-block btn-primary btn-default" value="Log in">
                            </div>
                            
                            <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
                            <br>
                			<a class="acc" href="<c:url value="/forgot"/>">Forgot Password?</a>
                </div>
                
                
             </form>
        </div>

  		</div>
	</div>
</div>
<br>
<br>

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


<div class="container" style="text-align: center;">
	<h3>Generate your label in the following steps: <a href = " <c:url value="/static/User_Manual.pdf"/> "  >Click Here To Download User Manual</a></h3>
</div>
<div class="container-fluid"><hr class="rule"></div>
<div class="container">	
	<div class="row">
		<div class="col-md-3">
			<h3>Step 1: Generate Form</h3>
			<p>Login and generate pdf form for issue of Car Parking Label.</p>
		</div>
		<div class="col-md-3">
			<h3>Step 2: Print Out</h3>
			<p>Get the hard copy of the form signed by the concerned reporting officer.</p>
		</div>
		<div class="col-md-3">
			<h3>Step 3: Submission</h3>
			<p>Submit the signed form to the Concerned Authority for Parking Label Generation.</p>
		</div>
		<div class="col-md-3">
			<h3>Step 4: Issue of Label</h3>
			<p>The Concerned Authority will issue you the label.</p>
		</div>
	</div>
</div>




<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>