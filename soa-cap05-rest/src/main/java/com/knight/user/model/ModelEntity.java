package com.knight.user.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

//This annotations means that data contained in this class must be persisted
@MappedSuperclass
public abstract class ModelEntity {

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@XmlTransient
	public Date getUpdateDate() {
		return updateDate;
	}
	
	@PreUpdate
	@PrePersist
	protected void adjustUpdateDate() {
		this.updateDate = new Date();
	}
	
}
