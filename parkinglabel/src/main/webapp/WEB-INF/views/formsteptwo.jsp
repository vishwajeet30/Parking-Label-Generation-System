<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<title>NIC : Form</title>
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
				<li><a id="ch" style="color:white" href="<c:url value="/welcome"/>" onclick="clicked(event)"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
				
				<sec:authorize access="hasRole('ADMIN')">
				<li><a id="ch"  style="color:white" href="<c:url value="/requests"/>" onclick="clicked(event)">Parking Label Requests <span class="badge" style="background-color:red">${count}</span></a></li>
				</sec:authorize>
				
				<li><a id="ch"  style="color:white" href="<c:url value="/form"/>" onclick="clicked(event)">Issue New Label</a></li>
				<li><a id="ch" style="color:white" href="<c:url value="/renew"/>" onclick="clicked(event)">View Status</a></li>
				
				<li class="dropdown">
					<a href="#" style="color:white" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>&nbsp;${loggedinuser}&nbsp;<span class="glyphicon glyphicon-triangle-bottom"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#" data-toggle="modal" data-target="#myModal">Contact</a></li>
						<li><a href="<c:url value="/account"/>" onclick="clicked(event)">Account</a></li>
						<sec:authorize access="hasRole('Admin')">
						<li><a href="<c:url value="/list"/>" onclick="clicked(event)">View All Users</a></li>
						</sec:authorize>
						<li><a href="<c:url value="/logout"/>" onclick="clicked(event)">Log Out</a></li>
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
    </b></p>
    </div>
</noscript>

<div class="container" style="text-align: center;">

<c:choose>
	<c:when test="${edit}">
    	<h3 style="text-align:center">Re Upload Parking Label Documents</h3>
    	<h5 style="color:red">NOTE: After This Step You Will Also Have to Re Upload the Signed Form in The "View Status" Section.</h5>
  	</c:when>
  	<c:otherwise>
  		<h3>STEP 02 of 02 for Issue of New Parking Label</h3>
  	</c:otherwise>
</c:choose>
	


</div>
<div class="container-fluid"><hr class="rule"></div>


<div style="margin:0 auto;">


<div class="container-fluid desktopversion" id="mobileversion">
<h5 class="form-group"><b>Upload scanned images of the following Documents:</b></h5>

<c:choose>
	<c:when test="${edit}">
    	<c:url value="/editlabeldocuments?labelid=${labelid}&${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
  	</c:when>
  	<c:otherwise>
  		<c:url value="/formsteptwo?labelid=${labelid}&${_csrf.parameterName}=${_csrf.token}" var="post_url"/>
  	</c:otherwise>
</c:choose>

	<form class="container-fluid desktopversion upload-form" id="mobileversion" style="margin:0 auto padding-top:10px;padding-bottom:10px;" method="post" action="${post_url}" enctype="multipart/form-data" >
	<div class="form-group">
		<font color="red">${message}</font>
		
		
    	<label for="file">Registration Certificate</label>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${rc}</font>
    	    	
    	<label for="file">Driving License</label>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload2" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${dl}</font>
    	
    	<label for="file">Your Identity Card</label>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload3" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${icard}</font>
    	
    	<c:if test="${relationship == 'other'}">
    	<label for="file">Copy of Affidavit For Relationship: "Other"</label>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload4" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${affadavit}</font>
    	</c:if>
    	
    	<c:if test="${relationship != 'self' && relationship !='other'}">
    	<label for="file">Document Containing Proof of the Relationship: </label><p style="text-transform: uppercase"><b>${relationship}</b></p>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload4" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${affadavit}</font>
    	</c:if>
    	
    	<c:if test="${ishired == 'yes'}">
    	<label for="file">Contract for Hired Taxi</label>
    	<input type="file" accept="image/*" class="upload-file" id="fileupload5" name="fileUpload" size="50" required="required" data-max-size="2048"/>
    	<p class="help-block">Size should be less than 2Mb.</p>
    	<font color="red">${contract}</font>
    	</c:if>
    	
    	
    	
    	<button type="submit" class="btn btn-success" onclick="window.open('<c:url value="/pdfview?labelid=${labelid}"/>');return true;">Upload</button>
    	
    	
    	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
  	</div>
	</form>
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

 <script>
function clicked(e){
	var nme = document.getElementById("fileupload");
	var nme2= document.getElementById("fileupload2");
	var nme3= document.getElementById("fileupload3");
	var nme4= document.getElementById("fileupload4");
	var nme5= document.getElementById("fileupload5");
	if(nme.value == "") {
    	alert('Please Select A File First!');
    	nme.focus();
    	e.preventDefault();
    	return false;
	}
	else if(nme2.value == "") {
	    alert('Please Select The Files First!');
	    nme2.focus();
	    e.preventDefault();
	    return false;
		}
	else if(nme3.value == "") {
	    alert('Please Select The Files First!');
	    nme3.focus();
	    e.preventDefault();
	    return false;
		}
	else if(nme4.value == "" && ${relationship!='self'}) {
	    alert('Please Select The Files First!');
	    nme4.focus();
	    e.preventDefault();
	    return false;
		}
	else if(nme5.value == ""  && ${ishired=='yes'}) {
	    alert('Please Select The Files First!');
	    nme5.focus();
	    e.preventDefault();
	    return false;
		}
}
    </script>


<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>