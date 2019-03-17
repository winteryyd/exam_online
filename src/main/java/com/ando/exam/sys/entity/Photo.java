package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_photo")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Photo implements Serializable {
	private static final long serialVersionUID = -8897270887557213026L;

	@Id
	@Column(length = 32)
	private String empid;

	@Lob
	@Column(name = "photo", columnDefinition = "longblob", nullable = true)
	private byte[] photo;

	public Photo() {
	}
	
	public Photo(String empid, byte[] photo) {
		this.empid = empid;
		this.photo = photo;
	}
}
