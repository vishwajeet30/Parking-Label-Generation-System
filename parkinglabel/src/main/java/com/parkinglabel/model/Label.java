package com.parkinglabel.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class Label {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer user_id;

	private String issueid;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String name;	
	
	@NotEmpty(message="Cannot be Left Blank")
	private String designation;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String division;
	
	private String telephone;
	
	@NotEmpty(message="Cannot be Left Blank")
	@Size(max=10,min=10,message="Please enter a valid 10 Digit Mobile Number")
	private String mobile;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String address;
	
	@NotBlank(message="Please Select A Vehicle Category")
	private String make;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String model;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String relationship;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String registeredownername;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String registeredowneraddress;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String registrationnum;
	
	@NotEmpty(message="Cannot be Left Blank")
	private String icardnum;
	
	private Date startdate;
	
	private Date enddate;
	
	@NotEmpty(message="Cannot be left blank")
	private String labeltype; 
	
	private String status;  // | Working | Expired | Renewal Request In Progress | Issue Request In Progress |
	
	private boolean hiredtaxi;
	
	private boolean signedformuploaded;
	
	private String remarks;
	
	private boolean edited;
	
	private List<LabelDocument> labeldocuments;
	public List<LabelDocument> getLabeldocuments() {
		return labeldocuments;
	}
	public void setLabeldocuments(List<LabelDocument> labeldocuments) {
		this.labeldocuments = labeldocuments;
	}


	public Integer getId() {
		return id;
	}

	private String username;
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getIssueid() {
		return issueid;
	}


	public void setIssueid(String issueid) {
		this.issueid = issueid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
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

	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getRelationship() {
		return relationship;
	}


	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}


	public String getRegisteredownername() {
		return registeredownername;
	}


	public void setRegisteredownername(String registeredownername) {
		this.registeredownername = registeredownername;
	}


	public String getRegisteredowneraddress() {
		return registeredowneraddress;
	}


	public void setRegisteredowneraddress(String registeredowneraddress) {
		this.registeredowneraddress = registeredowneraddress;
	}


	public String getRegistrationnum() {
		return registrationnum;
	}


	public void setRegistrationnum(String registrationnum) {
		this.registrationnum = registrationnum;
	}


	public String getIcardnum() {
		return icardnum;
	}


	public void setIcardnum(String icardnum) {
		this.icardnum = icardnum;
	}

	public Date getStartdate() {
		return startdate;
	}


	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}


	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getLabeltype() {
		return labeltype;
	}
	public void setLabeltype(String labeltype) {
		this.labeltype = labeltype;
	}
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public boolean isHiredtaxi() {
		return hiredtaxi;
	}


	public void setHiredtaxi(boolean hiredtaxi) {
		this.hiredtaxi = hiredtaxi;
	}

	

	public boolean isSignedformuploaded() {
		return signedformuploaded;
	}
	public void setSignedformuploaded(boolean signedformuploaded) {
		this.signedformuploaded = signedformuploaded;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return String.format(
				"Label [id=%s, issueid=%s, name=%s, designation=%s, division=%s, telephone=%s, mobile=%s, address=%s, model=%s, relationship=%s, registeredownername=%s, registeredowneraddress=%s, registrationnum=%s, icardnum=%s, status=%s, hiredtaxi=%s]",
				id, issueid, name, designation, division, telephone, mobile, address, model, relationship,
				registeredownername, registeredowneraddress, registrationnum, icardnum, status,
				hiredtaxi);
	}

	
	
	
	

}
