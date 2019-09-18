package com.parkinglabel.model;



import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class User{
    
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@NotEmpty (message="CAnnot Be Empty")
	private String username;
    
    @NotEmpty(message="Cannot be Left Blank")
    private String password;
    
    @NotEmpty(message="Cannot be Left Blank")
    private String passwordConfirm;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String email;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String name;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String desg;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String division;
	
	private String telephone;
	
	@NotEmpty(message="Cannot be Left Blank")
	@Size(max=10,min=10,message="Please Enter a Valid Mobile Phone Number")
	private String mobile;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String address;
	
	private boolean isVerified;
	
	private String role; 
    
	private String captcha;
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
	
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = true;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public User(String username, String password, String passwordConfirm, String email, String name,
			String desg, String division, String telephone, String mobile, String address,
			boolean isVerified) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
		this.name = name;
		this.desg = desg;
		this.division = division;
		this.telephone = telephone;
		this.mobile = mobile;
		this.address = address;
		this.isVerified = isVerified;
		this.role="USER";
	}
	
	public User(){
		
	}
	
	
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

	@Override
	public String toString() {
		return String.format(
				"User [id=%s, username=%s, email=%s, name=%s, desg=%s, division=%s, telephone=%s, mobile=%s, address=%s, isVerified=%s, role=%s]",
				id, username, email, name, desg, division, telephone, mobile, address, isVerified, role);
	}
 

	
}
