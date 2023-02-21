package com.bank.rest.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */

@Entity
@Table(name = "permission")
public class Permission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
