<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>
<head>
	<title>NIC : Requests</title>
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
	
	
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
 
	<script>
		//Use Date Picker	
    		jQuery(function($){ //on document.ready
        	$('#startdate').datepicker();
    		$('#enddate').datepicker();
    		})
	</script>
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
						
						<sec:authorize access="hasRole('Admin')">
						<li><a href="<c:url value="/list"/>">View All Users</a></li>
						</sec:authorize>
						
						<li><a href="<c:url value="/logout" />">Log Out</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>

<noscript>
	<div class="alert alert-danger">
    <p><b>
    Your Java Script is Disabled.<br>
    This WebPage Works better with JavaScript Enabled. Kindly Enable JavaScript to browse properly.<br>
    We work hard to protect this WebSite against CSRF and XSS attacks. So you can enable JavaScript without worries.  
    <br><a href="<c:url value="/logout"/>">Click Here To Logout</a>
    </b></p>
    </div>
</noscript>

<c:choose>
	<c:when test="${edit}">
    	<h3 style="text-align:center">Edit Parking Label Details</h3>
  	</c:when>
  	<c:otherwise>
  		<h3 style="text-align:center">Issue Parking Label</h3>
  	</c:otherwise>
</c:choose>

<div class="container-fluid"><hr class="rule"></div>

<div class="container">
<div class="table-responsive">
<table class="table table-striped">
	<caption>Parking Label Details</caption>

	<thead>
		<tr>
			<th>Name</th>
			<th>Category</th>
			<th>Model</th>
			<th>Registration No.</th>
			<th>Type</th>
			<th>Valid From</th>
			<th>Valid Till</th>
			<th>Label Serial No.</th>
			<th>Status</th>
			<th>Documents</th>
		</tr>
	</thead>
	
	<tbody>
			<tr>
			<td>${label.name}</td>
			<td>${label.make}</td>
			<td>${label.model}</td>
			<td>${label.registrationnum}</td>
			<td>${label.labeltype}</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.startdate}" /></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.enddate}" /></td>
			<td>${label.issueid}</td>
			<td>${label.status}</td>
			<td>
			<c:forEach items="${label.labeldocuments}" var="labeldoc">
			<a href="<c:url value="/download-${label.id}-${labeldoc.id}"/>" class="btn btn-primary col-xs-8" style="text-align:left; padding-left:2px;padding-bottom:0px;padding-top:0px;margin-bottom:1px;"><span class="glyphicon glyphicon-save"></span> ${labeldoc.description}</a><br>
			</c:forEach>
			</td>   
			</tr>	
	</tbody>
</table>
</div>
</div>

<c:if test="${edit}">
	<h4 style="text-align:center">Enter New Details to Be Updated</h4>
</c:if>


<c:choose>
	<c:when test="${edit}">
    	<c:url value="/admineditlabel?labelid=${label.id}&${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
  	</c:when>
  	<c:otherwise>
  		<c:url value="/saveform?labelid=${label.id}&${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
  	</c:otherwise>
</c:choose>

<form class="container-fluid desktopversion upload-form" id="mobileversion" style="margin:0 auto padding-top:10px;padding-bottom:10px;" method="post" action="${post_url}" >
    	<div class="form-group">
   	 		<label for="issueid">Enter The Parking Label Serial Number To Be Issued</label>
    		<input type="text" class="form-control" id="issueid" name="issueid" placeholder="Parking Label Serial Number" required="required" />
  		</div>
    	<div class="form-group">
   	 		<label for="startdate">Valid From</label><noscript>&nbsp;<b>(mm/dd/yyyy)</b></noscript>
    		<input type="text" class="form-control" id="startdate" name="startdate" maxlength="10" placeholder="mm/dd/yyyy" pattern="(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d" oninvalid="setCustomValidity('Invalid Date. Please Enter a valid date in the format: mm/dd/yyyy')" oninput="setCustomValidity('')" onchange="setCustomValidity('')" required="required" />
  		</div>
  		<div class="form-group">
   	 		<label for="enddate">Valid Till</label><noscript>&nbsp;<b>(mm/dd/yyyy)</b></noscript>
    		<input type="text" class="form-control" id="enddate" name="enddate" maxlength="10" placeholder="mm/dd/yyyy" pattern="(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d" oninvalid="setCustomValidity('Invalid Date. Please Enter a valid date in the format: mm/dd/yyyy')" oninput="setCustomValidity('')" onchange="setCustomValidity('')" required="required" />
  		</div>
    	<br>
    	<button type="submit" class="btn btn-success">Submit</button>
    	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
</form>


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