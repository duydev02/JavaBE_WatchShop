package com.assignment.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

	private static final long serialVersionUID = -7899874613954877814L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	@Size(min = 3, max = 20, message = "Username must be greater than 3 and less than 20 characters")
	private String username;

	@Column(name = "fullname")
	@Size(min = 5, max = 50, message = "Fullname must be greater than 5 and less than 50 characters")
	private String fullname;

	@Column(name = "hashPassword")
	private String hashPassword;

	@Column(name = "email")
	@Email(message = "Invalid email")
	private String email;

	@Column(name = "createdDate")
	@CreationTimestamp
	private Timestamp createdDate;

	@Column(name = "imgUrl")
	private String imgUrl;

	@ManyToOne
	@JoinColumn(name = "roleId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Roles role;

	@Column(name = "resetPassword")
	private String resetPassword;
	
	@Column(name = "active")
	private String active;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "authProvider")
	private AuthenticationProvider authProvider;
	
	@Transient
	public String getAvatarImagePath() {
		if(imgUrl == null) return "/user-avatar/default.png";
		
		return "/user-avatar/" + imgUrl;
	}
	
}
