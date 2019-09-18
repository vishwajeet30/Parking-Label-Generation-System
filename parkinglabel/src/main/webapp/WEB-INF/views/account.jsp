<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<!DOCTYPE html>
<html>
<head>
	<title>NIC : Account</title>
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
			
			#input-mobile {
    			display:none;
			}
			
			.input-group{
				width:90%;
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

		.input-group{
			text-align:left;
			margin:0 auto;
		}

		.desktopversion{
			background-color:#eee;
			border-radius:5px;
			width:60%;
			margin-top:20px;
			margin-bottom:20px;"
		}
		
		.addonwidth {
    		min-width:230px;
    		text-align:left;
		}
		
		textarea {
    		resize: none;
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
				<li><a id="ch" style="color:white" href="<c:url value="/welcome"/>"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
				<sec:authorize access="hasRole('ADMIN')">
				<li><a id="ch"  style="color:white" href="<c:url value="/requests"/>">Parking Label Requests <span class="badge" style="background-color:red">${count}</span></a></li>
				</sec:authorize>
				
				<li><a id="ch"  style="color:white" href="<c:url value="/form"/>">Issue New Label</a></li>
				<li><a id="ch" style="color:white" href="<c:url value="/renew"/>">View Status</a></li>
				
				<li class="dropdown">
					<a href="#" style="color:white" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>&nbsp;${loggedinuser}&nbsp;<span class="glyphicon glyphicon-triangle-bottom"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#" data-toggle="modal" data-target="#myModal">Contact</a></li>
						<li><a href="<c:url value="/account"/>">Account</a></li>
						<sec:authorize access="hasRole('ADMIN')">
						<li><a href="<c:url value="/list"/>">View All Users</a></li>
						</sec:authorize>
						<li><a href="<c:url value="/logout"/>">Log Out</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>
<div class="container" style="text-align: center;">
	<h3>Edit Your Account Details:</h3>
</div>
<div class="container-fluid"><hr class="rule"></div>

<c:url value="/account" var="post_url"/>
<div class="container-fluid desktopversion" id="mobileversion" style="margin:0 auto">
<form:form style="padding-top:10px;padding-bottom:10px;" modelAttribute="user" method="post" action="${post_url}" >	
	
	<c:if test="${param.bc != null}">
                                    <font color="red" style="padding-left:20px">Bad Credentials</font> <br>
                            </c:if>
	
	<form:input type="hidden" path="id" id="id"/>
	<div class="input-group">
    <label for="name" class="input-group-addon addonwidth" id="input-mobile"><b>Name</b><font color="red">*</font></label>
    <form:input type="text" class="form-control" id="name" path="name" placeholder="Name" required="required" />
  	<div class="has-error">
                            <font color="red"><form:errors path="name" class="help-inline"/></font>
                        </div>
  	</div>
	
	<div class="input-group">
    <label for="desg" class="input-group-addon addonwidth" id="input-mobile"><b>Designation</b><font color="red">*</font></label>
    <form:input type="text" class="form-control" id="desg" path="desg" placeholder="Designation" required="required" />
  	<div class="has-error">
                            <font color="red"><form:errors path="desg" class="help-inline"/></font>
                        </div>
  	</div>

  	<div class="input-group">
    <label for="division" class="input-group-addon addonwidth" id="input-mobile"><b>Division</b><font color="red">*</font></label>
    <form:input type="text" class="form-control" id="division" path="division" placeholder="Division" required="required" />
  	<div class="has-error">
                            <font color="red"><form:errors path="division" class="help-inline"/></font>
                        </div>
  	</div>
  	
  	
	<div class="input-group">
    <label for="email" class="input-group-addon addonwidth" id="input-mobile"><b>Email address</b><font color="red">*</font></label>
    <form:input type="email" class="form-control" id="email" path="email" placeholder="Email" required="required" />
  	<div class="has-error">
                            <font color="red"><form:errors path="email" class="help-inline"/></font>
                        </div>
  	</div>
	
  	<div class="input-group">
    <label class="input-group-addon addonwidth" for="moile" id="input-mobile"><b>Mobile Number: (+91)<font color="red">*</font></b></label>
    <form:input type="text" class="form-control" id="mobile" path="mobile" pattern="[0-9]{10}" title="Enter a valid 10 digit mobile number" maxlength="10" placeholder="Mobile" required="required" oninvalid="setCustomValidity('Enter a valid 10 digit mobile number')" oninput="setCustomValidity('')" />
  	<div class="has-error">
                            <font color="red"><form:errors path="mobile" class="help-inline" style="font-color:red" /></font>
                        </div>
  	</div>
	
  	<div class="input-group">
    <label for="telephone" class="input-group-addon addonwidth" id="input-mobile"><b>Intercom/Telphone No. Office</b></label>
    <form:input type="text" class="form-control" id="telephone" path="telephone" pattern="[0-9]{0,20}" title="Enter a valid Telephone number" placeholder="Telphone (Office) (If Any)" oninvalid="setCustomValidity('Enter a valid telephone number')" oninput="setCustomValidity('')"  />
  	<div class="has-error">
                            <form:errors path="telephone" class="help-inline"/>
                        </div>
  	</div>
	
  	
	
  	<div class="input-group">
    <label for="address" class="input-group-addon addonwidth" id="input-mobile"><b>Present Local Address</b><font color="red">*</font></label>
    <form:textarea id="adress" path="address" class="form-control" rows="2" maxlength="200" required="required" placeholder="Address"></form:textarea>
  	<div class="has-error">
                            <form:errors path="address" class="help-inline"/>
                        </div>
  	</div>

	
	<br>
  	<input type="submit" class="btn btn-primary" style="margin:auto;display:block" value="Update" />

	</form:form>
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


<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>