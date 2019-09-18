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
		@media screen and (max-width: 580px){
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
		
			#tooltip{
				left:-15px;
				top:-45px;
			}
			
			#myP{
				width:250px;
			}
			
			#tooltip2{
				left:-15px;
				top:-45px;
			}
			
			#myP2{
				width:250px;
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
			width:70%;
			margin-top:20px;
			margin-bottom:20px;"
		}
		
		.addonwidth {
    		min-width:316px;
    		text-align:left;
		}
		
		textarea {
    		resize: none;
		}
		
		#white-bg{
			background-color:white;
		}
		
		
		.container {
    width: 400px;
    height: 0px;
    position:absolute;
}
.floating {
border: 1px solid black;
border-radius:3px;
    float: left;
    position: relative;
    left: -10px;
    top: -25px;
    background-color: white;
    z-index:1000;
}

#registrationnum{
	text-transform:uppercase;
}
	</style>

<script>
function checkrelationship(){
	if(document.getElementById("relation").value=="self"){
		document.getElementById("registeredownername").value="${label.name}";
		document.getElementById("registeredownername").setAttribute("readonly", true);
		document.getElementById("registeredowneraddress").value="${label.address}";
		document.getElementById("registeredowneraddress").setAttribute("readonly", true);
		document.getElementById("hiredtaxi").checked = false;
		document.getElementById("hiredtaxi").disabled = true;
	}
	else{
		document.getElementById("registeredownername").removeAttribute("readonly");
		document.getElementById("registeredownername").value="";
		document.getElementById("registeredowneraddress").removeAttribute("readonly");
		document.getElementById("registeredowneraddress").value="";
		if(document.getElementById("relation").value=="other"){
			document.getElementById("hiredtaxi").disabled = false;
		}
		else{
			document.getElementById("hiredtaxi").checked = false;
			document.getElementById("hiredtaxi").disabled = true;
		}
	}
	
}
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
				
				<li><a id="chactive"  style="color:white" href="<c:url value="/form"/>">Issue New Label</a></li>
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
    #registrationnum{
    	text-transform:none;
    }
    </style>
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



<div style="margin:0 auto;">
<div class="container-fluid desktopversion" id="mobileversion">

<c:if test="${param.re1!=null}">
	<div class="alert alert-danger">
    <p><b>Invalid: Relationship with Registered Owner of Vehicle is "Self" but Name and Address of User and Registered Owner Don't Match</b></p>
    </div>
</c:if>
<c:if test="${param.re2!=null}">
	<div class="alert alert-danger">
    <p><b>Invalid: Relationship with Registered Owner is "Self" but You have also Ticked "Hired Taxi"</b></p>
    </div>
</c:if>
<c:if test="${param.re3!=null}">
	<div class="alert alert-danger">
    <p><b>Invalid: Relationship with Registered Owner is Immediate Family(Father,Mother... etc) but You have Also Ticked "Hired Taxi"</b></p>
    </div>
</c:if>


	<form:form style="padding-top:10px;padding-bottom:10px;" modelAttribute="label" method="post" id="myform">
	
	<form:input type="hidden" path="id" id="id" />	
	<form:input type="hidden" path="name" id="name" />
	<form:input type="hidden" path="designation" id="designation" />
	<form:input type="hidden" path="division" id="division" />
	<form:input type="hidden" path="mobile" id="mobile" />
	<form:input type="hidden" path="telephone" id="telephone" />
	<form:input type="hidden" path="address" id="address" />
	
	<c:if test="${param.bc != null}">
                                    <font color="red" style="padding-left:20px">Bad Credentials</font> <br>
                            </c:if>
	
	<div class="input-group">
    <label for="username" class="input-group-addon addonwidth" id="input-mobile"><b>Username</b></label>
    <input type="text" class="form-control" id="white-bg" value="${username}" disabled />
  	</div>
	
  	<div class="input-group">
    <label for="name" class="input-group-addon addonwidth" id="input-mobile"><b>Name</b></label>
    <input type="text" class="form-control" id="white-bg" value="${label.name}" disabled />
  	</div>
	
  	<div class="input-group">
    <label for="desg" class="input-group-addon addonwidth" id="input-mobile"><b>Designation</b></label>
    <input type="text" class="form-control" id="white-bg" value="${label.designation}" disabled />
  	</div>

  	<div class="input-group">
    <label for="division" class="input-group-addon addonwidth" id="input-mobile"><b>Division</b></label>
    <input type="text" class="form-control" id="white-bg" value="${label.division}" disabled/>
  	</div>
	
  	<div class="input-group">
    <label for="telephone" class="input-group-addon addonwidth" id="input-mobile"><b>Intercom/Telphone No. Office</b></label>
    <input type="text" class="form-control" id="white-bg" pattern="[0-9]{0,20}" title="Enter a valid Telephone number" value="${label.telephone}" disabled />
  	</div>
	
  	<div class="input-group">
    <label for="mobile" class="input-group-addon addonwidth" id="input-mobile"><b>Mobile No.</b></label>
    <input type="text" class="form-control" id="white-bg" pattern="[0-9]{10}" title="Enter a valid 10 digit mobile number" value="${label.mobile}" disabled />
  	</div>
	
  	<div class="input-group">
    <label for="address" class="input-group-addon addonwidth" id="input-mobile"><b>Present Local Address</b></label>
    <textarea id="adress" class="form-control"  style="background-color:white" rows="3" disabled >${label.address}</textarea>
  	</div>

  	
  	<div class="input-group">
  		<label class="input-group-addon addonwidth" id="input-mobile"><b>Parking Label Type</b><font color="red">*</font></label>
  		<form:select path="labeltype"  class="dropdown form-control" required="required">
  		<form:option value="" label="--- Select ---"/>
  		<form:options path="labeltype" items="${labeltypeitems}"/>
  		</form:select>
  	</div>
  	
  	<div class="input-group">
		<label for="InputModel" class="input-group-addon addonwidth" id="input-mobile"><b>Type / Make of Vehicle</b><font color="red">*</font></label>
    		<div class="radio" style="margin-left:25px">
    		<form:radiobutton path="make" value="Four Wheeler" required="required"/>
    		Four(4) Wheeler
    		</div>
    		<div class="radio" style="margin-left:25px">
    		<form:radiobutton path="make" value="Two Wheeler"/>
    		Two(2) Wheeler
    		</div>
		<div class="has-error">
                  
             <font color="red"><b><form:errors path="make" class="help-inline"/></b></font>
         </div>
  	</div>
  	
  	<div class="input-group">
    <label for="model" class="input-group-addon addonwidth" id="input-mobile"><b>Model of Vehicle</b><font color="red">*</font></label>
    <form:input type="text" class="form-control" id="model" path="model" placeholder="Car Manufacturere & Model" required="required"/>
  	<div class="has-error">
                            <form:errors path="model" class="model"/>
                        </div>
  	</div>

  	<div class="input-group">
  		<label class="input-group-addon addonwidth" id="input-mobile"><b>Relationship with the Owner of Vehicle</b><font color="red">*</font></label>
  		<form:select path="relationship" items="${relationshipitems}" class="dropdown form-control" id="relation" onchange="checkrelationship()"/>
  	</div>


	<div class="input-group">
    <label for="registeredownername" class="input-group-addon addonwidth" id="input-mobile"><b>Name of Registered Owner of Vehicle</b><font color="red">*</font></label>
    <div class="container" style="visibility:hidden" id="myP2">
    		<div id="tooltip2" class="floating"></div>
		</div>
    <form:input onfocus="hint2();" onblur="hint2off();" type="text" class="form-control" id="registeredownername" path="registeredownername" placeholder="Registered Owner" required="required"/>
  	<div class="has-error">
                            <font color="red"><form:errors path="registeredownername" class="help-inline"/></font>
                        </div>
  	</div>

  	<div class="input-group">
    <label for="registeredowneraddress" class="input-group-addon addonwidth" id="input-mobile"><b>Address of Registered Owner of Vehicle</b><font color="red">*</font></label>
    <form:textarea id="registeredowneraddress" path="registeredowneraddress" class="form-control" rows="3" maxlength="200" required="required" placeholder="Address of Registered Owner of Vehicle"/>
  	<div class="has-error">
                            <form:errors path="registeredowneraddress" class="help-inline"/>
                        </div>
  	</div>

  	<div class="input-group">
    <label for="registrationnum" class="input-group-addon addonwidth" id="input-mobile"><b>Vehicle Registration No.</b><font color="red">*</font></label>
    <div class="container" style="visibility:hidden" id="myP">
    		<div id="tooltip" class="floating"></div>
		</div>
    <form:input maxlength="30" type="text" onkeyup="return cUpper(this)" onfocus="hint();" onblur="hintoff();" class="form-control" id="registrationnum" path="registrationnum" placeholder="Vehicle Registeration No. (IN CAPITAL LETTERS WITHOUT SPACES) eg-DL12CF3510" pattern="[A-Z0-9]{2,20}" title="In Capital Letters Without Spaces and Without Special Characters" required="required"  oninvalid="setCustomValidity('Registration Number should be in Capital Letters with No Spaces eg- DL12CF3510 and Cannot Contain Special Characters Only Numbers and Letters Are Allowed')" oninput="setCustomValidity('')" />
  	
  		
  	
  	<div class="has-error">
                            <font color="red"><form:errors path="registrationnum" class="help-inline"/></font>
                        </div>
  	</div>

  	<div class="input-group">
    <label for="icardnum" class="input-group-addon addonwidth" id="input-mobile"><b>Your Identity Card No.</b><font color="red">*</font></label>
    <form:input type="text" class="form-control" id="icardnum" path="icardnum" placeholder="Your Identity Card No." required="required" />
  	<div class="has-error">
                            <form:errors path="icardnum" class="help-inline"/>
    </div>
  	</div> 
  	
  	<div class="input-group">
  		<div class="checkbox">
  		<label>
    	<form:checkbox path="hiredtaxi" id="hiredtaxi"/>
    		<b>Tick this box in Case of a Hired Taxi (NOTE:If you tick this box, you'll also have to Attach a Copy of the Contract)</b>
  		</label>
		</div>
  	</div>  
  	<br>


	<c:choose>
	<c:when test="${edit}">
    	<div style="width:200px;margin:0 auto;">
  		<input type="submit" value="Update Details and Proceed >>" class="btn btn-primary" />
  		</div>
  	</c:when>
  	<c:otherwise>
  		<div style="width:200px;margin:0 auto;">
  		<input type="submit" value="Proceed to Step 2 >>" class="btn btn-primary" />
  		</div>
  	</c:otherwise>
	</c:choose>
	</form:form>
	
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
function hint(){
	document.getElementById('tooltip').innerHTML="&nbsp;In Capital Letters Without Spaces &nbsp;Eg - DL12CF3510&nbsp;";
	document.getElementById("myP").style.visibility = "visible";
}
function hintoff(){
	document.getElementById('tooltip').innerHTML="";
	document.getElementById("myP").style.visibility = "hidden";
}

function hint2(){
	document.getElementById('tooltip2').innerHTML="&nbsp;Name of Registered Owner of &nbsp;Vehicle&nbsp;";
	document.getElementById("myP2").style.visibility = "visible";
}
function hint2off(){
	document.getElementById('tooltip2').innerHTML="";
	document.getElementById("myP2").style.visibility = "hidden";
}

function cUpper(cObj)
{
//store current positions in variables
var start = cObj.selectionStart, end = cObj.selectionEnd;

cObj.value=cObj.value.toUpperCase();
// restore from variables...
cObj.setSelectionRange(start, end);
}


function doIt(e)
{
    var e = e || event;
    
    if (e.keyCode == 32) return false; //Spaces Not Allowed
    
    if (e.keyCode == 173) return false; //Disable -
    
    if (e.keyCode == 190) return false; //Disable .
    
    if (e.keyCode == 191) return false; //Disable /
    
    if (e.keyCode == 188) return false; //Disable ,
}

window.onload = function(){
    var inp = document.getElementById("registrationnum");
    
    inp.onkeydown = doIt;
    
    if(document.getElementById("relation").value=="self"){
    	document.getElementById("registeredownername").setAttribute("readonly", true);
    	document.getElementById("registeredowneraddress").setAttribute("readonly", true);
    	document.getElementById("hiredtaxi").disabled=true;
    }
    else if(document.getElementById("relation").value!="other"){
    	document.getElementById("hiredtaxi").disabled=true;
    }
};


</script>

<script src="<c:url value="/static/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>"></script>
</body>
</html>