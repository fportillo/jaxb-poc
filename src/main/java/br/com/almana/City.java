package br.com.almana;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

	@XmlElement
	private String name;

	@XmlAttribute
	private FederationUnit fedUnit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FederationUnit getFedUnit() {
		return fedUnit;
	}

	public void setFedUnit(FederationUnit fedUnit) {
		this.fedUnit = fedUnit;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(fedUnit).append(name).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(fedUnit, other.getFedUnit())
				.append(name, other.getName()).isEquals();
	}

}
