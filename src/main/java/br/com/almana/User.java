package br.com.almana;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	@XmlElement
	private String name;

	@XmlElement
	private int age;

	@XmlElement
	private Date birthDay;

	@XmlElement
	private Address address;

	@XmlElementWrapper(name="dogs")
	@XmlElement(name="dog")
	private List<Dog> dogs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(name).append(age).append(birthDay)
				.append(address).append(dogs).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(name, other.getName())
				.append(address, other.getAddress())
				.append(age, other.getAge())
				.append(birthDay, other.getBirthDay())
				.append(dogs.toArray(), other.getDogs().toArray()).isEquals();
	}

}
