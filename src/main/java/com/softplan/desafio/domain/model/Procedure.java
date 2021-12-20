package com.softplan.desafio.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(	name = "procedures")
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Procedure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "A descrição do processo é obrigatória")
	@Size(max = 255)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_register_id")
	private User user;
	
	@Column( name = "date_register")
	private OffsetDateTime dateRegiter;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "procedure")
	private List<Opinion> opinions;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "procedure_opinion", 
				joinColumns = @JoinColumn(name = "procedure_id"), 
				inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();
}
