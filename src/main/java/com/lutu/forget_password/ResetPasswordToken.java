package com.lutu.forget_password;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiry() {
		return expiry;
	}
	public void setExpiry(LocalDateTime expiry) {
		this.expiry = expiry;
	}
	private LocalDateTime expiry;


}
