package com.edi.securedbackend.users.model;

import com.edi.securedbackend.tokens.Token;
import com.edi.securedbackend.loans.model.LoanData;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* Data Access Object */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserData implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;
	private String surname;
	private String email;
	private String password;

	@OneToMany(mappedBy = "userData")
	private List<LoanData> loanDataList;
	
	private Role role;

	@OneToMany(mappedBy = "userData")
	private List<Token> tokenList;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User transferToUser() {

		final User user = new User();

		user.setUserId(this.userId);
		user.setName(this.name);
		user.setSurname(this.surname);
		user.setEmail(this.email);
		user.setPassword(this.password);

		return user;
	}

}

