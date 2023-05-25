package com.edi.simplebackend.users.model;

import com.edi.simplebackend.loans.model.LoanData;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String email;
	private String password;

	@OneToMany(mappedBy = "userData")
	private List<LoanData> loanDataList;

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

