<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


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
		@media screen and (max-width: 552px){
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
			
			#tooltip{
				left:-15px;
				top:-45px;
			}
			
			#myP{
				width:250px;
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

		textarea {
    		resize: none;
    		width: 500px;
		}

		.desktopversion{
			background-color:#eee;
			border-radius:5px;
			width:50%;
			margin-top:20px;
			margin-bottom:20px;"
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
				<li><a id="chactive"  style="color:white" href="<c:url value="/requests"/>">Parking Label Requests <span class="badge" style="background-color:red">${count}</span></a></li>
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

<noscript>
	<div class="alert alert-danger">
    <p><b>
    Your Java Script is Disabled.<br>
    This WebPage Works better with JavaScript Enabled. Kindly Enable JavaScript to browse properly.<br>
    We work hard to protect this WebSite against CSRF and XSS attacks. So you can enable JavaScript without worries.  
    <br><a href="<c:url value="/logout"/>">Click Here To Logout</a>
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
    <p>Successfully Issued Parking Label</p>
    </div>
</c:if>


<div class="container" style="text-align: center;">
	<h3>View All Labels with Renewal or Issue Requests</h3>
</div>
<div class="container-fluid"><hr class="rule"></div>

<div class="container-fluid">
<div class="table-responsive">
<table class="table table-striped">
	<caption>Parking Labels</caption>

	<thead>
		<tr>
			<th>UserID</th>
			<th>Name</th>
			<th>Category</th>
			<th>Model</th>
			<th>Registration No.</th>
			<th>Type</th>
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
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.startdate}" /></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${label.enddate}" /></td>
			<td>${label.status}<br><c:if test="${not label.signedformuploaded}"><font color="red">(Signed Form Not<br>Yet Uploaded)</font></c:if><c:if test="${label.edited}"><br><font color="green"><b>Current Status: Edited</b></font></c:if></td>
			<td style="max-width: 200px;word-wrap: break-word;">
			<c:choose>
			<c:when test="${not label.signedformuploaded}">
			<a href="<c:url value="/issue?labelid=${label.id}"/>" class="btn btn-success disabled">Issue</a>
			</c:when>
			<c:otherwise>
			<a href="<c:url value="/issue?labelid=${label.id}"/>" class="btn btn-success">Issue</a>
			</c:otherwise>
			</c:choose>
			<a title="Delete" href="<c:url value="/deletelabel?labelid=${label.id}"/>" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>
			<br>
			<a href="#" data-toggle="modal" data-target="#postremarks" onclick="setlabelid(${label.id})" id="popupbutton">Post Remarks</a>
			<c:if test="${not empty label.remarks}">
				<br>
				<font color="red"><b><c:if test="${label.edited}">Previous </c:if>Remarks:</b><br>${label.remarks}</font>
			</c:if>
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
			</td>
			<td>
			<c:if test="${not label.signedformuploaded}">
			<a href="<c:url value="/pdfview?labelid=${label.id}"/>" class="btn btn-success col-xs-10" style="text-align:left; padding-left:2px;padding-bottom:0px;padding-top:0px;margin-bottom:1px;"><span class="glyphicon glyphicon-save"></span> Form(PDF)</a><br>
			</c:if>
			<c:forEach items="${label.labeldocuments}" var="labeldoc">
			<a href="<c:url value="/download-${label.id}-${labeldoc.id}"/>" class="btn btn-primary col-xs-10" style="text-align:left; padding-left:2px;padding-bottom:0px;padding-top:0px;margin-bottom:1px;"><span class="glyphicon glyphicon-save"></span> ${labeldoc.description}</a><br>
			</c:forEach>
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