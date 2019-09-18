package com.parkinglabel.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.parkinglabel.model.Forgot;
import com.parkinglabel.model.Label;
import com.parkinglabel.model.LabelDocument;
import com.parkinglabel.model.User;
import com.parkinglabel.service.ForgotService;
import com.parkinglabel.service.LabelDocumentService;
import com.parkinglabel.service.LabelService;
import com.parkinglabel.service.UserService;






@Controller
public class MyController {

    @Autowired
    UserService userService;
	
    @Autowired
    LabelService labelService;
    
    @Autowired
    LabelDocumentService labelDocumentService;
    
    @Autowired
    ForgotService forgotService;
    
    @Autowired
	private JavaMailSender mailSender;
    
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;
    
    private SecureRandom random = new SecureRandom();
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showloginpage(ModelMap model){
		if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/";  
        }
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			request.getSession().invalidate();
		}
		
		return "redirect:/login?logout"; //Redirect to home page
	}
	
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String showsignuppage(ModelMap model){
		if (isCurrentAuthenticationAnonymous()) {
			model.addAttribute("user",new User());    
			return "signup";
        } else {
            return "redirect:/";  
        }
		
		          
	}
	
	@RequestMapping(value= {"/signup"}, method=RequestMethod.POST)
	public String registrationp(@Valid User user, BindingResult result, ModelMap model, HttpSession session) {
		
        if (result.hasErrors()) {
            return "signup";
        }
        
        if(user.getPassword().length()<6 || user.getPassword().length()>20){
        	FieldError passwordError =new FieldError("user","password","Please should be 6 to 20 digits long");
            result.addError(passwordError);
        	return "signup";
        }
        
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
        	FieldError passwordError =new FieldError("user","passwordConfirm","Passwords Don't Match");
            result.addError(passwordError);
        	return "signup";
        }
        

        if(user.getUsername().length()<5 || user.getUsername().length()>20){
        	FieldError passwordError =new FieldError("user","username","Username Should Be 5 to 20 characters long.");
            result.addError(passwordError);
        	return "signup";
        }
        
        
        


        Pattern p2 =Pattern.compile("[^a-z\\.\\s]", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(user.getName());
        boolean b2 = m2.find();
        if (b2){
        	FieldError passwordError =new FieldError("user","name","Name Cannot Contain Special Charecters.");
            result.addError(passwordError);
        	return "signup";
        }
        
        Pattern p1 = Pattern.compile("[^a-z0-9\\.]", Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(user.getUsername());
        boolean b1 = m1.find();
        if (b1){
        	FieldError passwordError =new FieldError("user","username","Username Cannot Contain Special Charecters. Only Characters Numbers and dot(.) is allowed.");
            result.addError(passwordError);
        	return "signup";
        }
        
        Pattern pnum =Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m3 = pnum.matcher(user.getMobile());
        boolean b3 = m3.find();
        if (b3){
        	FieldError passwordError =new FieldError("user","mobile","Mobile Cannot Contain Special Charecters.");
            result.addError(passwordError);
        	return "signup";
        }
        Matcher m4=pnum.matcher(user.getTelephone());
        boolean b4=m4.find();
        if (b4){
        	FieldError passwordError =new FieldError("user","telephone","Telephone Cannot Contain Special Charecters. Only Characters Numbers and dot(.) is allowed.");
            result.addError(passwordError);
        	return "signup";
        }

        if(user.getUsername().contains(" ")){
        	FieldError passwordError =new FieldError("user","username","Username Cannot Contain Spaces");
            result.addError(passwordError);
        	return "signup";
        }
        
        if(!userService.isUserUsernameUnique(user.getUsername())){
            FieldError usernameError =new FieldError("user","username","Username Already Registered");
            result.addError(usernameError);
            return "signup";
        }
        
        if(!userService.isMobileUnique(user.getMobile())){
            FieldError mobileError =new FieldError("user","mobile","Mobile Number Already Registered");
            result.addError(mobileError);
            return "signup";
        }
        
        if(!userService.isEmailUnique(user.getEmail())){
            FieldError emailError =new FieldError("user","email","Email ID Already Registered");
            result.addError(emailError);
            return "signup";
        }
        //create a validate function instead if you want to include all custom error messages as done above

        
        String captcha=(String)session.getAttribute("CAPTCHA");
        if(captcha==null || (captcha!=null && !captcha.equals(user.getCaptcha()))){
            user.setCaptcha("");
            model.addAttribute("message", "Captcha does not match");
            return "signup";
        }
        
        try{
        userService.saveUser(user);
        }
        catch(Exception e){
        	model.addAttribute("message", "Invalid Credentials");
        	return "signup";
        }
        return "redirect:/login?success";
	}
	
	@RequestMapping(value="/forgot", method=RequestMethod.GET)
	public String showforgotpage(ModelMap model){
		if (isCurrentAuthenticationAnonymous()) {
            return "forgot";
        } else {
            return "redirect:/";  
        }    
		          
	}
	
	@RequestMapping(value="/forgot",method=RequestMethod.POST)
	public String forgotstepone(@RequestParam String email,@RequestParam String username,ModelMap model){
		
		if(email==null||username==null){
			model.addAttribute("message","Fields Cannot Be Empty");
			return "forgot";
		}
		
		if(!userService.isUsernameValid(username)){
			model.addAttribute("message","Invalid Username");
			return "forgot";
		}
		
		
		
		if(!userService.findByUsername(username).getEmail().equals(email)){
			model.addAttribute("message","The email you've entered doesn't match with the email associated to the username you've entered.");
			return "forgot";
		}
		
		if(forgotService.ismailalreadysent(userService.findByUsername(username).getId())){
			model.addAttribute("message","Email has already been sent previously. Check your email. The previously sent link will expire after 24 Hours. If you did not receive an email wait for a few minutes and if you still don't recieve the email try again after 24 hours.  ");
			return "forgot";
		}
		
		String otp=generatekey();
		
		try{
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("nic.parkinglabelsystem@gmail.com");
		message.setTo(email);
		message.setSubject("Parking Label System: Forgot Password Verification");
		message.setText("Hello "+userService.findByUsername(username).getName()+". Please open the following link to reset your Parking Label System password:  http://10.22.112.146:8080/parkinglabel/forgotsteptwo?id="+otp+"                     NOTE:This link will expire after 24 Hours.");
		mailSender.send(message);		
		
		}
		catch(Exception ex){
			model.addAttribute("message",ex.getMessage());
			return "forgot";
		}
        
		
		Forgot forgot = new Forgot();
        forgot.setUserid(userService.findByUsername(username).getId());
        forgot.setUniquekey(otp);
        forgotService.insertforgot(forgot);
        
        return "redirect:/login?successchange";
	}
	
	@RequestMapping(value="/forgotsteptwo",method=RequestMethod.GET)
	public String showchangepasswordpage(ModelMap model,@RequestParam String id){
		
		
		if(forgotService.findForgotByUniquekey(id)==null){
			return "accessDenied";
		}
		
		Forgot forgot=forgotService.findForgotByUniquekey(id);
		
		model.addAttribute("key",id);
		model.addAttribute("userid",userService.findById(forgot.getUserid()).getId());
		model.addAttribute("name",userService.findById(forgot.getUserid()).getName());
		return "forgotsteptwo";
	}
	
	@RequestMapping(value="/forgotsteptwo",method=RequestMethod.POST)
	public String submitforgotform(ModelMap model,@RequestParam String password,@RequestParam String passwordConfirm,@RequestParam int userid,@RequestParam String key){
		
		if(password==null||passwordConfirm==null||userid==0){
			model.addAttribute("message","Fields Cannot Be Empty");
			model.addAttribute("userid",userid);
			model.addAttribute("name",userService.findById(userid).getName());
			return "forgotsteptwo";
		}
		if(password.length()<6){
        	model.addAttribute("message","Please should be atleast 6 digits long");
        	model.addAttribute("userid",userid);
        	model.addAttribute("name",userService.findById(userid).getName());
        	return "formsteptwo";
        }
        
        if (!passwordConfirm.equals(password)) {
        	model.addAttribute("message","Passwords Don't Match");
        	model.addAttribute("userid",userid);
        	model.addAttribute("name",userService.findById(userid).getName());
        	return "formsteptwo";
        }
        
        
        try{
        	userService.updatePassword(userid,password);
        	forgotService.deleteforgot(forgotService.findForgotByUniquekey(key));
        }
        catch(Exception e){
        	model.addAttribute("message","Invalid Credentials");
        	model.addAttribute("userid",userid);
        	model.addAttribute("name",userService.findById(userid).getName());
        	return "formsteptwo";
        }
		return "redirect:/login?updatesuccess";
	}
	
	@RequestMapping(value={"/","/welcome"}, method=RequestMethod.GET)
	public String showwelcomepage(ModelMap model){
		model.addAttribute("count",labelService.countrequests());
	    model.addAttribute("loggedinuser",getPrincipal());
	    return "welcome";          
	}

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String showformpage(ModelMap model){
	    
		
		Label label = new Label();
		
		User user = userService.findByUsername(getPrincipalUsername());
		model.addAttribute("username",getPrincipalUsername());
		label.setName(user.getName());
		label.setDesignation(user.getDesg());
		label.setDivision(user.getDivision());
		label.setTelephone(user.getTelephone());
		label.setMobile(user.getMobile());
		label.setAddress(user.getAddress());
		label.setRegisteredownername(user.getName());
		label.setRegisteredowneraddress(user.getAddress());
		
		
		Map< String, String > relationship = new LinkedHashMap<String, String>();
        relationship.put("self", "Self");
        relationship.put("father", "Father");
        relationship.put("mother", "Mother");
        relationship.put("husband", "Husband");
        relationship.put("wife", "Wife");
        relationship.put("son", "Son");
        relationship.put("daughter", "Daughter");
        relationship.put("other", "Other");
        model.addAttribute("relationshipitems", relationship);
		
        Map< String, String > labeltype = new LinkedHashMap<String, String>();
        labeltype.put("Official", "Official (Permanent Employee)");
        labeltype.put("Non Official", "Non Official (Temporary Employee)");
        labeltype.put("Date Pass", "Date Pass");
        model.addAttribute("labeltypeitems", labeltype);
		
        model.addAttribute("count",labelService.countrequests());
		model.addAttribute("loggedinuser",getPrincipal());
	    model.addAttribute("label",label); 
	    model.addAttribute("edit",false); 
	    return "form";          
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String submitform(@Valid Label label,BindingResult result, ModelMap model){
			
		User usercheck=userService.findByUsername(getPrincipalUsername());
		if (label.getName() == null || label.getDesignation()==null
				||label.getDivision()==null||label.getMobile()==null||label.getAddress()==null
				||label.getIcardnum()==null||label.getMake()==null||label.getModel()==null
				||label.getRegisteredowneraddress()==null||label.getRegisteredownername()==null
				||label.getRegistrationnum()==null||label.getLabeltype()==null) {
			
			return "redirect:/form?bc";
		}
		
		if(label.getMobile().length()!=10){
			FieldError mobileError =new FieldError("user","mobile","Enter a Valid 10 digit Mobile number");
            result.addError(mobileError);
		}

		if(!userService.isMobileUnique(label.getMobile()) && !usercheck.getMobile().equals(label.getMobile())){
            FieldError mobileError =new FieldError("user","mobile","Mobile Number Already Registered");
            result.addError(mobileError);
            
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",false);
    		return "form";
        }
		
		if(label.getRelationship().equals("self") && (!label.getRegisteredowneraddress().equals(label.getAddress()) || !label.getRegisteredownername().equals(label.getName()))){
			return "redirect:/form?re1"; //re1=Relationship Error 1: Self But Name and Address Not Same
		}
		else if(label.getRelationship().equals("self") && label.isHiredtaxi()==true){
			return "redirect:/form?re2"; //re1=Relationship Error 2: Self cannot have hired taxi
		}
		else if(!label.getRelationship().equals("other") && !label.getRelationship().equals("self") && label.isHiredtaxi()==true){
			return "redirect:/form?re3"; //re1=Relationship Error 1: An owner family Member Cannot Be Hired Taxi
		}
		
		Pattern p2 =Pattern.compile("[^a-z\\.\\s]", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(label.getRegisteredownername());
        boolean b2 = m2.find();
        if (b2){
        	FieldError passwordError =new FieldError("label","registeredownername","Name Cannot Contain Special Charecters.");
            result.addError(passwordError);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",false);
            return "form";
        }
		
		Pattern p1 =Pattern.compile("[^A-Z0-9]");
        Matcher m1 = p1.matcher(label.getRegistrationnum());
        boolean b1 = m1.find();
        if (b1){
        	FieldError error =new FieldError("label","registrationnum","Registration Number should Be in Capitals and Cannot Contain Blank Spaces and Special Charecters. Only Numbers and letters allowed");
            result.addError(error);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",false);
            return "form";
        }
		
		if(!labelService.isRegistrationNumUnique(label.getRegistrationnum())){
			FieldError error =new FieldError("label","registrationnum","Registration Number Already Exists.");
            result.addError(error);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",false);
            return "form";
		}
        
        
			try{
				User user = userService.findByUsername(getPrincipalUsername());
				label.setStatus("Issue In Progress");
				label.setSignedformuploaded(false);
				label.setUser_id(user.getId());
				int labelid=labelService.saveLabel(label);
				return "redirect:/formsteptwo?labelid="+labelid;
			}
			catch(Exception e){
				
	            Map< String, String > relationship = new LinkedHashMap<String, String>();
	            relationship.put("self", "Self");
	            relationship.put("father", "Father");
	            relationship.put("mother", "Mother");
	            relationship.put("husband", "Husband");
	            relationship.put("wife", "Wife");
	            relationship.put("son", "Son");
	            relationship.put("daughter", "Daughter");
	            relationship.put("other", "Other");
	            model.addAttribute("relationshipitems", relationship);
	    		
	            Map< String, String > labeltype = new LinkedHashMap<String, String>();
	            labeltype.put("Official", "Official (Permanent Employee)");
	            labeltype.put("Non Official", "Non Official (Temporary Employee)");
	            labeltype.put("Date Pass", "Date Pass");
	            model.addAttribute("labeltypeitems", labeltype);
	            
	            model.addAttribute("username",usercheck.getUsername());
	    		model.addAttribute("loggedinuser",getPrincipal());
	    		model.addAttribute("count",labelService.countrequests());
	    		model.addAttribute("label",label);
	    		model.addAttribute("edit",false);
				return "redirect:/form?bc";
			}
		
	}
	
	
	@RequestMapping(value = { "/formsteptwo" }, method = RequestMethod.GET)
	public String showsteptwopage(@RequestParam int labelid, ModelMap model) {
		
		//if someone tries to access the page and the documents have already been uploaded
		if(labelDocumentService.findAllByLabelId(labelService.findById(labelid).getId()).size()>=3){
			return "accessDenied";
		}
		
		//Do not allow anyone other the intended user to upload documents 
		User labeluser=userService.findById(labelService.findById(labelid).getUser_id());
		User loggedinuser=userService.findByUsername(getPrincipalUsername());
		if(loggedinuser.getRole().equals("USER") && !labeluser.getUsername().equals(loggedinuser.getUsername())){
			return "accessDenied";
		}
		
		Label label = labelService.findById(labelid);
		
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		
		String ishired;
		boolean hired=label.isHiredtaxi();
		if(hired){
			ishired="yes";
		}
		else{
			ishired="no";
		}
		
		model.addAttribute("labelid",labelid);
		
		model.addAttribute("ishired",ishired);
		
		model.addAttribute("relationship",label.getRelationship());
		
		model.addAttribute("edit",false);
		
		return "formsteptwo";
	}
	
	
	@RequestMapping(value="/formsteptwo", method=RequestMethod.POST)
	public String submitformstep2(HttpServletRequest request,
            @RequestParam CommonsMultipartFile[] fileUpload,@RequestParam int labelid, ModelMap model) throws Exception{
		
		//Validate the files first
		int j=0;
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile1 : fileUpload){
				if(aFile1.getSize()>(2*1024*1024)){
					switch(j){
            		case 0:model.addAttribute("rc","Size Greater than 2 MB");
            		break;
            		case 1:model.addAttribute("dl","Size Greater than 2 MB");
            		break;
            		case 2:model.addAttribute("icard","Size Greater than 2 MB");
            		break;
            		case 3:model.addAttribute("affadavit","Size Greater than 2 MB");
            		break;
            		case 4:model.addAttribute("contract","Size Greater than 2 MB");
            		break;
            		}
					
					Label label = labelService.findById(labelid);
					
					model.addAttribute("loggedinuser",getPrincipal());
					model.addAttribute("count",labelService.countrequests());
					
					String ishired;
					boolean hired=label.isHiredtaxi();
					if(hired){
						ishired="yes";
					}
					else{
						ishired="no";
					}
					
					model.addAttribute("labelid",labelid);
					
					model.addAttribute("ishired",ishired);
					
					model.addAttribute("relationship",label.getRelationship());
					
					model.addAttribute("edit",false);
					return "formsteptwo";
				}
				
				if(!aFile1.getContentType().contains("image/")){
					Label label = labelService.findById(labelid);
					
					model.addAttribute("loggedinuser",getPrincipal());
					model.addAttribute("count",labelService.countrequests());
					
					String ishired;
					boolean hired=label.isHiredtaxi();
					if(hired){
						ishired="yes";
					}
					else{
						ishired="no";
					}
					
					model.addAttribute("labelid",labelid);
					
					model.addAttribute("ishired",ishired);
					
					model.addAttribute("relationship",label.getRelationship());
					model.addAttribute("message","Only Image Files Allowed");
					model.addAttribute("edit",false);
					return "formsteptwo";
				}
				j=j+1;
            }
			//Check if all required files are uploaded 
			Label label=labelService.findById(labelid);
			if(		   (label.isHiredtaxi() && label.getRelationship().equals("other") && j!=5) 
					|| (label.getRelationship().equals("self") && j!=3) 
					|| (!label.isHiredtaxi() && label.getRelationship().equals("other") && j!=4) 
					|| (!label.getRelationship().equals("self") && !label.getRelationship().equals("other") && j!=4)  ){
				model.addAttribute("loggedinuser",getPrincipal());
				model.addAttribute("count",labelService.countrequests());
				
				String ishired;
				boolean hired=label.isHiredtaxi();
				if(hired){
					ishired="yes";
				}
				else{
					ishired="no";
				}
				
				model.addAttribute("labelid",labelid);
				
				model.addAttribute("ishired",ishired);
				
				model.addAttribute("relationship",label.getRelationship());
				
				model.addAttribute("message","Select All The Files First");
				
				model.addAttribute("edit",false);
				return "formsteptwo";
			}
		}
		else{
			return "redirect:/formsteptwo?labelid="+labelid;
		}
		
		
		//After Validation
		Label label=labelService.findById(labelid);
		try{
		int i=1;
        for (CommonsMultipartFile aFile : fileUpload){
                LabelDocument uploadFile = new LabelDocument();
                uploadFile.setName(aFile.getOriginalFilename());
                uploadFile.setContent(aFile.getBytes());
                
                switch(i){
                case 1:uploadFile.setDescription("RC");
                break;
                case 2:uploadFile.setDescription("DL");
                break;
                case 3:uploadFile.setDescription("I-CARD");
                break;
                case 4: if(label.getRelationship().equals("other")) uploadFile.setDescription("AFFIDAVIT"); else uploadFile.setDescription("Proof of Relationship");
                break;
                case 5:uploadFile.setDescription("CONTRACT");
                break;
                }
                uploadFile.setType(aFile.getContentType());
                uploadFile.setLabelid(labelid);
                labelDocumentService.saveDocument(uploadFile);
                i=i+1;
            }
            return "redirect:/welcome?success";
		}
		catch(Exception e){
			String ishired;
			boolean hired=label.isHiredtaxi();
			if(hired){
				ishired="yes";
			}
			else{
				ishired="no";
			}
			
			model.addAttribute("labelid",labelid);
			
			model.addAttribute("ishired",ishired);
			
			model.addAttribute("relationship",label.getRelationship());
			
			model.addAttribute("message",e.getMessage());
			
			model.addAttribute("edit",false);
			return "formsteptwo";
		}
	}

	
	
	
	@RequestMapping(value="/pdfview", method=RequestMethod.GET)
	public String viewpdf(ModelMap model, @RequestParam int labelid){
		
		Label label=labelService.findById(labelid); 
		User labeluser=userService.findById(label.getUser_id());
		
		User loggedinuser=userService.findByUsername(getPrincipalUsername());
		if(loggedinuser.getRole().equals("USER") && !labeluser.getUsername().equals(loggedinuser.getUsername())){
			return "accessDenied";
		}
		
		model.addAttribute("label",label);
		return "pdfView";
	}
	
	
	@RequestMapping(value="/renew", method=RequestMethod.GET)
	public String showrenewpage(ModelMap model){
		model.addAttribute("loggedinuser",getPrincipal());
		
		User user=userService.findByUsername(getPrincipalUsername());
		List<Label> labels=labelService.findAllByUserId(user.getId());
		
		for(Label label : labels){
			label.setLabeldocuments(labelDocumentService.findAllByLabelId(label.getId()));
		}
		
		
		model.addAttribute("count",labelService.countrequests());
		model.addAttribute("labels",labels);
		return "renew";
	}
	
	//Upload Signed Forms Button
	@RequestMapping(value="/uploadsignedform",method=RequestMethod.POST)
	public String uploadsignedform(HttpServletRequest request,
            @RequestParam CommonsMultipartFile[] fileUpload,@RequestParam int labelid,ModelMap model) throws Exception{
		
		//Validations
		int j=0;
		if (fileUpload != null && fileUpload.length > 0 ) {
			
			for (CommonsMultipartFile aFile1 : fileUpload){
				if(aFile1.getSize()>(2*1024*1024)){
					return "redirect:/renew?size";
				}
				if(!aFile1.getContentType().contains("image/")){
					return "redirect:/renew?content";
				}
				j=j+1;
            }
			if(j!=2){
				return "redirect:/renew?invalid";
			}
		}
		else{
			return "redirect:/renew?invalid";
		}
		
		
		//After Validation
		int i=0;
        for (CommonsMultipartFile aFile : fileUpload){
            		
            LabelDocument uploadFile = new LabelDocument();
            uploadFile.setName(aFile.getOriginalFilename());
            uploadFile.setContent(aFile.getBytes());
                
            if(i==0){
            	uploadFile.setDescription("Form Pg1/2");
            }
            else if(i==1){
            	uploadFile.setDescription("Form Pg2/2");
            }
            uploadFile.setType(aFile.getContentType());
            uploadFile.setLabelid(labelid);
            labelDocumentService.saveDocument(uploadFile);
            i++;
        }
		
		labelService.signedformuploaded(labelid);//Set the value of whether signed form is uploaded to true
		return "redirect:/renew?uploadsuccess";
	}
	
	
	@RequestMapping(value="/editlabel", method=RequestMethod.GET)
	public String showeditpage(@RequestParam int labelid,ModelMap model){
		
		Label label = labelService.findById(labelid);
		
		//If anyone tries to access this page to edit their label details when the operator  
		if(label.getRemarks()==null){
			return "accessDenied";
		}
		
		//Do not allow anyone other the intended user to edit details 
		User labeluser=userService.findById(label.getUser_id());
		User loggedinuser=userService.findByUsername(getPrincipalUsername());
		if(loggedinuser.getRole().equals("USER") && !labeluser.getUsername().equals(loggedinuser.getUsername())){
			return "accessDenied";
		}
		
		
		model.addAttribute("username",getPrincipalUsername());		
		
		Map< String, String > relationship = new LinkedHashMap<String, String>();
        relationship.put("self", "Self");
        relationship.put("father", "Father");
        relationship.put("mother", "Mother");
        relationship.put("husband", "Husband");
        relationship.put("wife", "Wife");
        relationship.put("son", "Son");
        relationship.put("daughter", "Daughter");
        relationship.put("other", "Other");
        model.addAttribute("relationshipitems", relationship);
		
        Map< String, String > labeltype = new LinkedHashMap<String, String>();
        labeltype.put("Official", "Official (Permanent Employee)");
        labeltype.put("Non Official", "Non Official (Temporary Employee)");
        labeltype.put("Date Pass", "Date Pass");
        model.addAttribute("labeltypeitems", labeltype);
		
        model.addAttribute("count",labelService.countrequests());
		model.addAttribute("loggedinuser",getPrincipal());
	    model.addAttribute("label",label); 
	    model.addAttribute("edit",true); 
	    return "form";          
	}
	
	@RequestMapping(value="/editlabel", method=RequestMethod.POST)
	public String editlabel(@Valid Label label,BindingResult result, ModelMap model){
			
		User usercheck=userService.findByUsername(getPrincipalUsername());
		if (label.getName() == null || label.getDesignation()==null
				||label.getDivision()==null||label.getMobile()==null||label.getAddress()==null
				||label.getIcardnum()==null||label.getMake()==null||label.getModel()==null
				||label.getRegisteredowneraddress()==null||label.getRegisteredownername()==null
				||label.getRegistrationnum()==null||label.getLabeltype()==null) {
			
			return "redirect:/editlabel?bc&labelid="+label.getId();
		}
		
		

		if(!userService.isMobileUnique(label.getMobile()) && !usercheck.getMobile().equals(label.getMobile())){
            FieldError mobileError =new FieldError("user","mobile","Mobile Number Already Registered");
            result.addError(mobileError);
            
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",true);
    		return "form";
        }
		
		Pattern p2 =Pattern.compile("[^a-z\\.\\s]", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(label.getRegisteredownername());
        boolean b2 = m2.find();
        if (b2){
        	FieldError passwordError =new FieldError("label","registeredownername","Name Cannot Contain Special Charecters.");
            result.addError(passwordError);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",true);
            return "form";
        }
		
		Pattern p1 =Pattern.compile("[^A-Z0-9]");
        Matcher m1 = p1.matcher(label.getRegistrationnum());
        boolean b1 = m1.find();
        if (b1){
        	FieldError error =new FieldError("label","registrationnum","Registration Number should Be in Capitals and Cannot Contain Blank Spaces and Special Charecters.");
            result.addError(error);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",true);
            return "form";
        }
		
        Label labelcheck=labelService.findById(label.getId());
		if(!labelService.isRegistrationNumUnique(label.getRegistrationnum()) && !labelcheck.getRegistrationnum().equals(label.getRegistrationnum())){
			FieldError error =new FieldError("label","registrationnum","Registration Number Already Exists.");
            result.addError(error);
        	
            Map< String, String > relationship = new LinkedHashMap<String, String>();
            relationship.put("self", "Self");
            relationship.put("father", "Father");
            relationship.put("mother", "Mother");
            relationship.put("husband", "Husband");
            relationship.put("wife", "Wife");
            relationship.put("son", "Son");
            relationship.put("daughter", "Daughter");
            relationship.put("other", "Other");
            model.addAttribute("relationshipitems", relationship);
    		
            Map< String, String > labeltype = new LinkedHashMap<String, String>();
            labeltype.put("Official", "Official (Permanent Employee)");
            labeltype.put("Non Official", "Non Official (Temporary Employee)");
            labeltype.put("Date Pass", "Date Pass");
            model.addAttribute("labeltypeitems", labeltype);
            
            model.addAttribute("username",usercheck.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
    		model.addAttribute("label",label);
    		model.addAttribute("edit",true);
            return "form";
		}
        
        	try{
        		label.setSignedformuploaded(false);
        		label.setEdited(true);
				labelService.editlabel(label);
				return "redirect:/editlabeldocuments?labelid="+label.getId();
			}
			catch(Exception e){
				
	            Map< String, String > relationship = new LinkedHashMap<String, String>();
	            relationship.put("self", "Self");
	            relationship.put("father", "Father");
	            relationship.put("mother", "Mother");
	            relationship.put("husband", "Husband");
	            relationship.put("wife", "Wife");
	            relationship.put("son", "Son");
	            relationship.put("daughter", "Daughter");
	            relationship.put("other", "Other");
	            model.addAttribute("relationshipitems", relationship);
	    		
	            Map< String, String > labeltype = new LinkedHashMap<String, String>();
	            labeltype.put("Official", "Official (Permanent Employee)");
	            labeltype.put("Non Official", "Non Official (Temporary Employee)");
	            labeltype.put("Date Pass", "Date Pass");
	            model.addAttribute("labeltypeitems", labeltype);
	            
	            model.addAttribute("username",usercheck.getUsername());
	    		model.addAttribute("loggedinuser",getPrincipal());
	    		model.addAttribute("count",labelService.countrequests());
	    		model.addAttribute("label",label);
	    		model.addAttribute("edit",true);
				return "redirect:/editlabel?bc&labelid="+label.getId();
			}
		
	}
	
	@RequestMapping(value = { "/editlabeldocuments" }, method = RequestMethod.GET)
	public String showeditlabeldocumentspage(@RequestParam int labelid, ModelMap model) {
		
		Label label=labelService.findById(labelid);
		
		//Do not allow the user to edit if remarks is null i.e. the admin has not given permission to allow user to edit
		if(label.getRemarks()==null){
			return "accessDenied";
		}
		
		//Do not allow anyone other the intended user to edit details 
		User labeluser=userService.findById(label.getUser_id());
		User loggedinuser=userService.findByUsername(getPrincipalUsername());
		if(loggedinuser.getRole().equals("USER") && !labeluser.getUsername().equals(loggedinuser.getUsername())){
			return "accessDenied";
		}
		
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		
		String ishired;
		boolean hired=label.isHiredtaxi();
		if(hired){
			ishired="yes";
		}
		else{
			ishired="no";
		}
		
		model.addAttribute("labelid",labelid);
		
		model.addAttribute("ishired",ishired);
		
		model.addAttribute("relationship",label.getRelationship());
		
		model.addAttribute("edit",true);
		
		return "formsteptwo";
	}
	
	
	@RequestMapping(value="/editlabeldocuments", method=RequestMethod.POST)
	public String submiteditlabeldocuments(HttpServletRequest request,
            @RequestParam CommonsMultipartFile[] fileUpload,@RequestParam int labelid, ModelMap model) throws Exception{
		
		//Validate the files first
		int j=0;
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile1 : fileUpload){
				if(aFile1.getSize()>(2*1024*1024)){
					switch(j){
            		case 0:model.addAttribute("rc","Size Greater than 2 MB");
            		break;
            		case 1:model.addAttribute("dl","Size Greater than 2 MB");
            		break;
            		case 2:model.addAttribute("icard","Size Greater than 2 MB");
            		break;
            		case 3:model.addAttribute("affadavit","Size Greater than 2 MB");
            		break;
            		case 4:model.addAttribute("contract","Size Greater than 2 MB");
            		break;
            		}
					
					Label label = labelService.findById(labelid);
					
					model.addAttribute("loggedinuser",getPrincipal());
					model.addAttribute("count",labelService.countrequests());
					
					String ishired;
					boolean hired=label.isHiredtaxi();
					if(hired){
						ishired="yes";
					}
					else{
						ishired="no";
					}
					
					model.addAttribute("labelid",labelid);
					
					model.addAttribute("ishired",ishired);
					
					model.addAttribute("relationship",label.getRelationship());
					model.addAttribute("edit",true);
					return "formsteptwo";
				}
				
				if(!aFile1.getContentType().contains("image/")){
					Label label = labelService.findById(labelid);
					
					model.addAttribute("loggedinuser",getPrincipal());
					model.addAttribute("count",labelService.countrequests());
					
					String ishired;
					boolean hired=label.isHiredtaxi();
					if(hired){
						ishired="yes";
					}
					else{
						ishired="no";
					}
					
					model.addAttribute("labelid",labelid);
					
					model.addAttribute("ishired",ishired);
					
					model.addAttribute("relationship",label.getRelationship());
					model.addAttribute("message","Only Image Files Allowed");
					model.addAttribute("edit",true);
					return "formsteptwo";
				}
				j=j+1;
            }
			//Check if all required files are uploaded 
			Label label=labelService.findById(labelid);
			if(		   (label.isHiredtaxi() && label.getRelationship().equals("other") && j!=5) 
					|| (label.getRelationship().equals("self") && j!=3) 
					|| (!label.isHiredtaxi() && label.getRelationship().equals("other") && j!=4) 
					|| (!label.getRelationship().equals("self") && !label.getRelationship().equals("other") && j!=4)  ){
				model.addAttribute("loggedinuser",getPrincipal());
				model.addAttribute("count",labelService.countrequests());
				
				String ishired;
				boolean hired=label.isHiredtaxi();
				if(hired){
					ishired="yes";
				}
				else{
					ishired="no";
				}
				
				model.addAttribute("labelid",labelid);
				
				model.addAttribute("ishired",ishired);
				
				model.addAttribute("relationship",label.getRelationship());
				
				model.addAttribute("message","Select All The Files First");
				model.addAttribute("edit",true);
				return "formsteptwo";
			}
		}
		else{
			return "redirect:/editlabeldocuments?labelid="+labelid;
		}
		
		
		//After Validation
		Label label=labelService.findById(labelid);
		
		//Delete All Old Documents First
		labelDocumentService.deleteAllByLabelId(labelid);
		
		int i=1;
        for (CommonsMultipartFile aFile : fileUpload){
                LabelDocument uploadFile = new LabelDocument();
                uploadFile.setName(aFile.getOriginalFilename());
                uploadFile.setContent(aFile.getBytes());
                
                switch(i){
                case 1:uploadFile.setDescription("RC");
                break;
                case 2:uploadFile.setDescription("DL");
                break;
                case 3:uploadFile.setDescription("I-CARD");
                break;
                case 4: if(label.getRelationship().equals("other")) uploadFile.setDescription("AFFIDAVIT"); else uploadFile.setDescription("Proof of Relationship");
                break;
                case 5:uploadFile.setDescription("CONTRACT");
                break;
                }
                uploadFile.setType(aFile.getContentType());
                uploadFile.setLabelid(labelid);
                labelDocumentService.saveDocument(uploadFile);
                i=i+1;
            }
            return "redirect:/renew?editsuccess";
		
	}
	
	
	//Renew Button For Expired Labels
	@RequestMapping(value="/renewlabel", method=RequestMethod.GET)
	public String renewlabel(ModelMap model,@RequestParam int labelid){
		Label label=labelService.findById(labelid);
		label.setStatus("Renew In Progress");
		label.setStartdate(null);
		label.setEnddate(null); //To prevent the database from changing its status back to 'Expired' as curdate>enddate
		labelService.updateLabel(label);
		return "redirect:/renew?success";
	}
	
	@RequestMapping(value = { "/download-{labelid}-{docId}" }, method = RequestMethod.GET)
	public String downloadDocument(@PathVariable int labelid, @PathVariable int docId, HttpServletResponse response) throws IOException {
		
		//Do not allow anyone other the intended user to download documents
		Label label=labelService.findById(labelid); 
		User labeluser=userService.findById(label.getUser_id());
		User loggedinuser=userService.findByUsername(getPrincipalUsername());
		if(loggedinuser.getRole().equals("USER") && !labeluser.getUsername().equals(loggedinuser.getUsername())){
			return "accessDenied";
		}
		
		LabelDocument document = labelDocumentService.findById(docId);
		response.setContentType(document.getType());
        response.setContentLength(document.getContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + document.getName() +"\"");
 
        FileCopyUtils.copy(document.getContent(), response.getOutputStream());
 
 		return "redirect:/renew";
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
    public String accountdetails(ModelMap model) {
        User user=userService.findByUsername(getPrincipalUsername());
		model.addAttribute("user", user);
		model.addAttribute("username",user.getUsername());
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
        return "account";
    }
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
    public String accountdetailspost(@ModelAttribute User user, ModelMap model,BindingResult result) {
		User usercheck=userService.findByUsername(getPrincipalUsername());
		if (user.getMobile().length()!=10 || user.getName() == null || user.getDesg()==null
				||user.getDivision()==null||user.getMobile()==null||user.getAddress()==null||user.getEmail()==null) {
			model.addAttribute("username",user.getUsername());
			model.addAttribute("loggedinuser",getPrincipal());
			model.addAttribute("count",labelService.countrequests());
			return "account?bc";
		}
		if(!userService.isEmailUnique(user.getEmail()) && !usercheck.getEmail().equals(user.getEmail())){
            FieldError emailError =new FieldError("user","email","Email ID Already Registered");
            result.addError(emailError);
            model.addAttribute("username",user.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
            return "account";
        }
		if(!userService.isMobileUnique(user.getMobile()) && !usercheck.getMobile().equals(user.getMobile())){
            FieldError mobileError =new FieldError("user","mobile","Mobile Number Already Registered");
            result.addError(mobileError);
            model.addAttribute("username",user.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
            return "account";
        }
        
		Pattern p2 =Pattern.compile("[^a-z\\.\\s]", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(user.getName());
        boolean b2 = m2.find();
        if (b2){
        	FieldError passwordError =new FieldError("user","name","Name Cannot Contain Special Charecters.");
            result.addError(passwordError);
            model.addAttribute("username",user.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
            return "account";
        }
        
        Pattern pnum =Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m3 = pnum.matcher(user.getMobile());
        boolean b3 = m3.find();
        if (b3){
        	FieldError passwordError =new FieldError("user","mobile","Mobile Cannot Contain Special Charecters.");
            result.addError(passwordError);
            model.addAttribute("username",user.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
            return "account";
        }
        Matcher m4=pnum.matcher(user.getTelephone());
        boolean b4=m4.find();
        if (b4){
        	FieldError passwordError =new FieldError("user","telephone","Telephone Cannot Contain Special Charecters. Only Characters Numbers and dot(.) is allowed.");
            result.addError(passwordError);
            model.addAttribute("username",user.getUsername());
    		model.addAttribute("loggedinuser",getPrincipal());
    		model.addAttribute("count",labelService.countrequests());
            return "account";
        }
		
		user.setRole(usercheck.getRole());
		try{
		userService.updateUser(user);
		}
		catch(Exception e){
			model.addAttribute("username",user.getUsername());
			model.addAttribute("loggedinuser",getPrincipal());
			model.addAttribute("count",labelService.countrequests());
			return "account?bc";
		}
		
		return "redirect:/welcome?successaccount";
    }
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listallusers(ModelMap model) {
        List<User> users=userService.findAllUsers();
		model.addAttribute("users",users);
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		model.addAttribute("loggedinusername",getPrincipalUsername());
        return "listallusers";
    }
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteuser(@RequestParam String username){
		userService.deleteUserByUsername(username);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/makeadmin", method=RequestMethod.GET)
	public String makeadmin(@RequestParam String username){
		User user=userService.findByUsername(username);
		user.setRole("ADMIN");
		userService.updateUser(user);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/removeadmin", method=RequestMethod.GET)
	public String removeadminpriveleges(@RequestParam String username){
		User user=userService.findByUsername(username);
		user.setRole("USER");
		userService.updateUser(user);
		return "redirect:/list";
	}
	
	@RequestMapping(value= {"/requests"}, method=RequestMethod.GET)
	public String requests(ModelMap model){
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		
		List<Label> labels=labelService.findAllIssueRequestLabels();
		labels.addAll(labelService.findAllRenewRequestLabels());
		
		for(Label label : labels){
			label.setLabeldocuments(labelDocumentService.findAllByLabelId(label.getId()));
			User user=userService.findById(label.getUser_id());
			label.setUsername(user.getUsername());
		}
		
		model.addAttribute("labels",labels);
		return "requests";
	}
	
	@RequestMapping(value="/postremarks", method=RequestMethod.POST)
	public String postremarks(@RequestParam int remarkslabelid,@RequestParam String remarks){
		labelService.setremarks(remarkslabelid, remarks);
		Label label=labelService.findById(remarkslabelid);
		label.setEdited(false);//So that the user can edit the details
		labelService.editlabel(label);
		return "redirect:/requests";
	}
	
	@RequestMapping(value="/deletelabel", method=RequestMethod.GET)
	public String deletelabel(@RequestParam int labelid){
		labelService.deleteById(labelid);
		return "redirect:/requests";
	}
	@RequestMapping(value="/deletelabelwelcome", method=RequestMethod.GET)
	public String deletelabelfromwelcomepage(@RequestParam int labelid){
		labelService.deleteById(labelid);
		return "redirect:/welcome";
	}
	
	@RequestMapping(value= {"/issue"}, method=RequestMethod.GET)
	public String issue(ModelMap model, @RequestParam int labelid){
		
		//To disallow admin from issuing label or adding documents to already working label 
		if(labelService.findById(labelid).getStatus().equals("Working")){
			return "accessDenied";
		}
		
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		
		Label label=labelService.findById(labelid);
		label.setLabeldocuments(labelDocumentService.findAllByLabelId(label.getId()));
		model.addAttribute("label",label);
		model.addAttribute("edit",false);
		return "issue";
	}
	
	//Admin Issues Parking Label Button
	@RequestMapping(value="/saveform", method=RequestMethod.POST)
	public String saveform(@RequestParam int labelid,ModelMap model,@RequestParam String issueid,@RequestParam Date startdate,@RequestParam Date enddate){
		
		
            
        Label label=labelService.findById(labelid);
        label.setStartdate(startdate);
        label.setEnddate(enddate);
    	label.setStatus("Working");
    	labelService.deleteremarks(labelid); //Remove remarks so that he can no longer edit his label
    	label.setIssueid(issueid);
    	labelService.updateLabel(label);
    	
    	
    	label.setEdited(false);
    	labelService.editlabel(label);
    	
    	return "redirect:/requests?success";
		

	}
	
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(ModelMap model,@RequestParam String searchoption,@RequestParam String search){
			
			String[] words = search.split("\\W+");	
			List<Label> results=new ArrayList<Label>();
			
			if(searchoption.equals("vid")){
				for(String word : words){
					results.addAll(labelService.search(word, "registrationnum"));
				}
			}
			else if(searchoption.equals("pid")){
				for(String word : words){
					results.addAll(labelService.search(word, "issue_id"));
				}
			}else if(searchoption.equals("empid")){
				List<User> users=userService.searchusingusername(search);
				for(User user : users){
					results.addAll(labelService.findAllByUserId(user.getId()));
				}
			}else if(searchoption.equals("mobile")){
				for(String word : words){
					results.addAll(labelService.search(word, "mobile"));
				}
			}else if(searchoption.equals("name")){
				for(String word : words){
					results.addAll(labelService.search(word, "name"));
				}
			}
			
			for(Label label : results){
				label.setLabeldocuments(labelDocumentService.findAllByLabelId(label.getId()));
				User user=userService.findById(label.getUser_id());
				label.setUsername(user.getUsername());
			}
			
			model.addAttribute("resultcount", results.size());
			model.addAttribute("labels",results);
	       	model.addAttribute("loggedinuser",getPrincipal());
	       	model.addAttribute("count",labelService.countrequests());
	        return "welcome";          
	}
	
	
	//When Admin wants to edit the Label Serial No. or Valid From or Valid Till Date
	@RequestMapping(value= {"/admineditlabel"}, method=RequestMethod.GET)
	public String admineditlabel(ModelMap model, @RequestParam int labelid){
		
		model.addAttribute("loggedinuser",getPrincipal());
		model.addAttribute("count",labelService.countrequests());
		
		Label label=labelService.findById(labelid);
		label.setLabeldocuments(labelDocumentService.findAllByLabelId(label.getId()));
		model.addAttribute("label",label);
		model.addAttribute("edit",true);
		return "issue";
	}
	
	//Admin Edits Parking Label
	@RequestMapping(value="/admineditlabel", method=RequestMethod.POST)
	public String admineditlabelsubmit(@RequestParam int labelid,ModelMap model,@RequestParam String issueid,@RequestParam Date startdate,@RequestParam Date enddate){
		
		
            
        Label label=labelService.findById(labelid);
        label.setStartdate(startdate);
        label.setEnddate(enddate);
    	label.setIssueid(issueid);
    	labelService.updateLabel(label);
    	
    	return "redirect:/welcome?editsuccess";
		

	}
	
	@RequestMapping(value="/accessDenied", method=RequestMethod.GET)
	public String showaccessdeniedpage(ModelMap model){
	        return "accessDenied";          
	}
	
	
	
	
	/**
     * This method returns the principal[user-name] of logged-in user.
     */
	private String getPrincipalUsername(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
	
	
	/**
     * This method returns the principal[first name] of logged-in user.
     *
	*/
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        User userforname =userService.findByUsername(userName);
        String[] words = userforname.getName().split("\\W+");
        return words[0];
    }
    
	
    //This method generates 26 character long key of base 32 i.e. (0 to V)
	private String generatekey(){
		return new BigInteger(130, random).toString(32);
	}

    @InitBinder
    protected void initBinder(WebDataBinder binder){
    	SimpleDateFormat dateformat =new SimpleDateFormat("MM/dd/yyyy");
    	binder.registerCustomEditor(Date.class,new CustomDateEditor(dateformat,false));
    }
    
    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
    
    
    
    //Scheduled Execution every 6 hours for checking if forgot password is expired and deleting if the time exceeds 24 hours
    @Scheduled(cron="0 0 */6 * * *")
    public void forgotpasswordfeildsdeleted(){
    	forgotService.deleteafterexpire();
    }
    
    //Scheduled Execution every day at 12:00 PM Noon
    @Scheduled(cron="0 0 12 * * ?")
    public void updatelabelstatusifexpired(){
    	labelService.updateonexpire();
    }
    
  
    
    
}


