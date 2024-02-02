package com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.Register.Login.in.Spring.Security.Project.Entity.User;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="PasswordResetCodeAndToken")
public class PasswordResetCodeAndToken {
	
	private static final int EXPIRATION_TOKEN = 60 * 60 * 24;
	private static final int EXPIRATION_CODE = 35;


	@Id
    @Column(name = "PasswordResetCodeAndToken_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
    @OneToOne(fetch = FetchType.LAZY, optional = false , mappedBy = "passwordRCAT")
    private User user;
	
	
	 @Column(name = "reset_password_token")
	 private String resetPasswordToken;
	 
	 @Column(name="password_token_expired_time")
	 private Date resetPasswordToken_expried;
	
	 @Column(name= "code_validate_email")
	 private String code;
	 
	 @Column(name="code_expired_time")
	 private Date code_expried;

	public PasswordResetCodeAndToken( User user, String resetPasswordToken, String code) {
		super();
		this.user = user;
		this.resetPasswordToken = resetPasswordToken;
		this.code = code;
		this.resetPasswordToken_expried = calculateExpiryDate(EXPIRATION_TOKEN);
		this.code_expried = calculateExpiryDate(EXPIRATION_CODE);
	}
	 
	private Date calculateExpiryDate (final int expiryTimeInSeconds) {
		final Calendar cal = Calendar.getInstance(); // calendar is abstract type , can't create instance
		cal.setTimeInMillis(new Date().getTime()); // get current time
		cal.add(Calendar.SECOND, expiryTimeInSeconds); // add time expired to current time
		return new Date(cal.getTime().getTime()); // return date type
	}
	
	
	public void updateToken(final String token) {
		this.resetPasswordToken = token;
		this.resetPasswordToken_expried = calculateExpiryDate(EXPIRATION_TOKEN);
	}
	
	public void updateCode(final String code) {
		this.code = code;
		this.code_expried = calculateExpiryDate(EXPIRATION_CODE);
	}
	
}
