package com.edi.simplebackend.users.model;

import com.edi.simplebackend.loans.model.LoanData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/* Data Access Object */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String telephoneNumber;
	@OneToMany(mappedBy = "userData")
	private List<LoanData> loanDataList;

	public User transferToUser() {

		final User user = new User();

		user.setUserId(this.userId);
		user.setName(this.name);
		user.setSurname(this.surname);
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setEmail(this.email);
		user.setTelephoneNumber(this.telephoneNumber);

		return user;
	}

}

