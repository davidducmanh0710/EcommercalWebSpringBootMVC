package com.example.Register.Login.in.Spring.Security.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	/*
	 * @ManyToMany( fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	 * 
	 * @JoinTable( name="users_roles", joinColumns={@JoinColumn(name="ROLE_ID",
	 * referencedColumnName="ID")}, inverseJoinColumns={@JoinColumn(name="USER_ID",
	 * referencedColumnName="ID")}) private List<User> users;
	 */

	/*
	 * @ManyToMany( fetch = FetchType.EAGER , cascade = {CascadeType.DETACH ,
	 * CascadeType.MERGE , CascadeType.PERSIST, CascadeType.REFRESH })
	 * 
	 * @JoinTable ( name = "user_role", joinColumns = @JoinColumn(name = "role_id"),
	 * inverseJoinColumns = @JoinColumn(name = "user_id") ) private List<User> users
	 * = new ArrayList<>();
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Role(String name) {
		this.name = name;
	}

}