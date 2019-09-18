<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>NIC : Welcome</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link href="<c:url value="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet">
	
	<link rel="icon" href="<c:url value="/static/nic_icon.png"/>" type="image/gif" sizes="16x16">

	
	<link rel="stylesheet" href="<c:url value="/static/font-awesome-4.7.0/css/font-awesome.min.css"/>">

		<style>
		@media screen and (max-width: 552px){
		#btn{
			white-space: normal;			
			}
			
		#btn1{
			white-space: normal;
			height:80px;
			padding-top:10px;			
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

		
		#mytextarea{
				width:auto;
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
			margin-top:5px;
			margin-bottom:20px;"
		}



		.btn-lg{
			margin-right: 10px;
			margin-bottom: 10px;
			
		}
		
		.btn1{
		 height:71px;
		 padding-top:20px;
		}
		
		textarea {
    		resize: none;
    		width: 500px;
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
				<li><a id="chactive" style="color:white" href="<c:url value="/welcome"/>"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
				
				<sec:authorize access="hasRole('ADMIN')">
				<li><a id="ch" style="color:white" href="<c:url value="/requests"/>">Parking Label Requests <span class="badge" style="background-color:red">${count}</span></a></li>
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
						
						<li><a href="<c:url value="/logout"/>">Logout</a></li>
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
    <br><a href="<c:url value="/logout"/>" class="btn btn-danger">Logout</a>
    <a href="<c:url value="/account"/>" class="btn btn-primary">Update Your Account Details</a>
    <sec:authorize access="hasRole('ADMIN')"><a href="<c:url value="/list"/>" class="btn btn-primary">View All Users</a></sec:authorize>
    </b></p>
    </div>
    <style>
    #popupbutton{
    	display:none;
    }
    </style>
</noscript>

<c:if test="${param.success != null}">
	<div class="alert alert-success">
    <p><b>Successfully Registered Parking Label. <span style="text-decoration:underline">Kindly click on "View Status" and Upload a Signed Hard Copy of the form.</span></b></p>
    </div>
</c:if>

<c:if test="${param.successaccount != null}">
	<div class="alert alert-success">
    <p><b>Details Successfully Updated.</b></p>
    </div>
</c:if>

<c:if test="${param.editsuccess != null}">
	<div class="alert alert-success">
    <p><b>Label Details Successfully Updated.</b></p>
    </div>
</c:if>

<sec:authorize access="hasRole('ADMIN')">
<div class="container-fluid">
<div class="jumbotron" style="background-color:white">
<h1 class="strong text-center">Search</h1>
</div>
</div>

<div class="container-fluid desktopversion" id="mobileversion" style="background-color:white">
	<c:url value="/search?${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
	<form method="post" action="${post_url}">
		<div class="form-group">
  		<label>Search Using:</label>
  		<select name="searchoption" class="dropdown form-control">
  			<option value="vid" selected="selected">Vehicle Registration No.</option>
  			<option value="pid">Parking Label Serial No.</option>
  			<option value="empid">UserID</option>
  			<option value="mobile">Mobile Number</option>
  			<option value="name">Name</option>
		</select>
  		</div>

  		<div class="form-group">
    	<input type="text" class="form-control" id="search" name="search" placeholder="Search here..." autofocus="autofocus">
  		</div>

  		<button type="submit" class="btn btn-primary center-block">Search</button>
  		
  		<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	</form>
</div>
<br><br>

<c:if test="${param.search != null}">
<div class="container-fluid">
<div class="table-responsive">
<table class="table table-striped">
	<caption>&nbsp;&nbsp;${resultcount} Parking Labels Found</caption>

	<thead>
		<tr>
			<th>UserID</th>
			<th>Name</th>
			<th>Category</th>
			<th>Model</th>
			<th>Registration No.</th>
			<th>Type</th>
			<th>Label No.</th>
			<th>Valid From</th>
			<th>Valid Till</th>
			<th>Status</th>
			<th></th>
			<th>Documents</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${labels}" var="label">
			<tr>
			<td>${label.username}</td>
			<td>${label.name}<br>M:${label.mobile}</td>
			<td>${label.make}</td>
			<td>${label.model}</td>
			<td>${label.registrationnum}</td>
			<td>${label.labeltype}</td>
			<td>
			<c:choose>
			<c:when test="${label.issueid != null}">${label.issueid}</c:when>
			<c:otherwise>---</c:otherwise>
			</c:choose>
			</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.startdate}" /></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.enddate}" /></td>
			<td>${label.status}<br><c:if test="${not label.signedformuploaded}"><font color="red">(Signed Form Not<br>Yet Uploaded)</font></c:if><c:if test="${label.edited}"><br><font color="green"><b>Current Status: Edited</b></font></c:if></td>
			<td style="max-width: 200px;word-wrap: break-word;">
			<c:choose>
			<c:when test="${not label.signedformuploaded}">
			<a href="<c:url value="/issue?labelid=${label.id}"/>" class="btn btn-success disabled">Issue</a>
			</c:when>
			<c:otherwise>
			<c:if test="${label.status=='Issue In Progress' or label.status=='Renew In Progress'}">
			<a href="<c:url value="/issue?labelid=${label.id}"/>" class="btn btn-success">Issue</a>
			</c:if>
			</c:otherwise>
			</c:choose>
			<c:if test="${label.status=='Working'}"><a href="<c:url value="/admineditlabel?labelid=${label.id}"/>" class="btn btn-primary" title="Edit"><span class="glyphicon glyphicon-pencil"></span></a></c:if>
			<a href="<c:url value="/deletelabelwelcome?labelid=${label.id}"/>" title="Delete" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>
			<br>
			<c:if test="${label.status=='Issue In Progress' or label.status=='Renew In Progress'}">
			<a href="#" data-toggle="modal" data-target="#postremarks" onclick="setlabelid(${label.id})" id="popupbutton">Post Remarks</a>
			<noscript>
				<c:url value="/postremarks?${_csrf.parameterName}=${_csrf.token}" var="post_urlns"/>
                <form method="post" action="${post_urlns}">
        		<input type="hidden" name="remarkslabelid" value="${label.id}" required="required">
        		<label>Enter Remarks</label><br>
        		<textarea name="remarks" style="width:120px" maxlength="290" rows="2" required="required"></textarea>
        		<br>
        		<input type="submit" class="btn" value="Post Remarks">
        		<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
        		</form>
			</noscript>
			</c:if>
			<c:if test="${not empty label.remarks}">
				<br>
				<font color="red"><b><c:if test="${label.edited}">Previous </c:if>Remarks:</b><br>${label.remarks}</font>
			</c:if>
			</td>
			<td>
			<c:if test="${not label.signedformuploaded}">
			<a href="<c:url value="/pdfview?labelid=${label.id}"/>" target="_blank" class="btn btn-success col-xs-11" style="text-align:left; padding-left:2px;padding-bottom:0px;padding-top:0px;margin-bottom:1px;"><span class="glyphicon glyphicon-save"></span> Form(PDF)</a><br>
			</c:if>
			<c:forEach items="${label.labeldocuments}" var="labeldoc">
			<a href="<c:url value="/download-${label.id}-${labeldoc.id}"/>" class="btn btn-primary col-xs-11" style="text-align:left; padding-left:2px;padding-bottom:0px;padding-top:0px;margin-bottom:1px;"><span class="glyphicon glyphicon-save"></span> ${labeldoc.description}</a><br>
			</c:forEach>
			</td>   
			</tr>
		</c:forEach>	
	</tbody>
</table>
</div>
</div>
</c:if>

</sec:authorize>


<br><br>



<sec:authorize access="hasRole('USER')">
<div class="container" style="text-align: center;">
	<h1>Generate Parking Label</h1>
</div>

<div class="container-fluid"><hr class="rule"></div>



<div class="container">
<div class="jumbotron">
<div class="container" style="padding-top:100px;padding-bottom:100px;margin:0 auto;width:700px;">
	
	<a class="btn btn-primary btn-lg mobileversion btn1" id="btn1" href="<c:url value="/form"/>">Generate New Parking Label</a>
  	<a class="btn btn-primary btn-lg mobileversion" id="btn" href="<c:url value="/renew"/>">View Status of Existing Parking Label<br>or Renew Old Label</a>
  	
</div>
</div>
</div>
</sec:authorize>



<div class="container" style="text-align: center;">
	<h3>Generate your label in the following steps:</h3>
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


<div id="postremarks" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background-color:#A2C1E3">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Post Remarks</h4>
      </div>
      <div class="modal-body">
        
        <c:url value="/postremarks?${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
        <form method="post" action="${post_url}">
        <input type="hidden" id="remarkslabelid" name="remarkslabelid" required="required">
        <label>Enter Remarks</label><br>
        <textarea id="mytextarea" name="remarks" maxlength="290" rows="5" required="required"></textarea>
        <br>
        <input type="submit" class="btn" value="Post Remarks">
        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
        </form>
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


<script>
function setlabelid(labelid){
		document.getElementById("remarkslabelid").value=labelid;
}
</script>
<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>