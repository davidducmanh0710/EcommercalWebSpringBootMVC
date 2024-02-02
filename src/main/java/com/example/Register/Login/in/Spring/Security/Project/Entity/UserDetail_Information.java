package com.example.Register.Login.in.Spring.Security.Project.Entity;



import java.sql.Date;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users_detail")
public class UserDetail_Information {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_infor_id" , nullable = false)
	private Long id;
	
    @OneToOne(fetch = FetchType.LAZY, optional = false , mappedBy = "userDetail_Information")
    private User user;
	
	@Column(name = "first_name")
	private String firstNameDetail;
	
	@Column(name = "last_name")
	private String lastNameDetail;
	
	@Column(name = "email" , unique = true)
	private String emailDetail;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "country")
	private String country;
	
	
	
	public UserDetail_Information(UserDetail_Information u) {
		this.id = u.id;
		this.firstNameDetail = u.firstNameDetail;
		this.lastNameDetail = u.lastNameDetail;
		this.emailDetail = u.emailDetail;
		this.phoneNumber = u.phoneNumber;
		this.gender = u.gender;
		this.dateOfBirth = u.dateOfBirth;
		this.address = u.address;
		this.country = u.country;
	}

	
	
}
