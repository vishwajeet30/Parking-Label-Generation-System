<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
	<title>NIC : List All</title>
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
						
						<li><a href="<c:url value="/logout" />">Log Out</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>


<div class="container" style="text-align: center;">
	<h3>View All Users</h3>
</div>
<div class="container-fluid"><hr class="rule"></div>

<div class="container">
<div class="table-responsive">
<table class="table table-striped">
	<caption>List of All Users</caption>

	<thead>
		<tr>
			<th>UserID</th>
			<th>Name</th>
			<th>Designation</th>
			<th>Devision</th>
			<th>Telephone/Intercom</th>
			<th>Mobile</th>
			<th>Address</th>
			<th></th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr <c:if test="${user.username == loggedinusername}">style="background-color:skyblue"</c:if>>
			<td>${user.username}</td>
			<td>${user.name}</td>
			<td>${user.desg}</td>
			<td>${user.division}</td>
			<td>${user.telephone}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>   
			<td>
			<c:if test="${user.username != loggedinusername}">
			<a href="<c:url value="/delete?username=${user.username}"/>" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>
			</c:if>
			<c:if test="${user.role == 'USER'}">
			<a href="<c:url value="/makeadmin?username=${user.username}"/>" class="btn btn-primary">Make Admin</a>
			</c:if>
			<c:if test="${user.role == 'ADMIN'}">
			<c:if test="${user.username != loggedinusername}">
			<a href="<c:url value="/removeadmin?username=${user.username}"/>" class="btn">Remove Admin Priveleges</a>
			</c:if>
			</c:if>
			<c:if test="${user.username == loggedinusername}">
			<font color="green"><b>CURRENT USER</b></font>
			</c:if>
			</td>
			</tr>
		</c:forEach>	
	</tbody>
</table>
</div>
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