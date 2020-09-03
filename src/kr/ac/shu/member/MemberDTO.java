package kr.ac.shu.member;

import java.sql.Timestamp;

/**
 * MemberDTO 클래스
 * @author 정원수(20190514)
 *
 */
public class MemberDTO {
	private int idx;
	private String name;
	private String email;
	private String passwd;
	private String phone;
	private Timestamp created;
	private Timestamp updated;
	
	public MemberDTO() {
		super();
	}
	public MemberDTO(int idx, String name, String email, String passwd, String phone, Timestamp created,
			Timestamp updated) {
		super();
		this.idx = idx;
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.phone = phone;
		this.created = created;
		this.updated = updated;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Timestamp getUpdated() {
		return updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
	@Override
	public String toString() {
		return "MemberDTO [idx=" + idx + ", name=" + name + ", email=" + email + ", passwd=" + passwd + ", phone="
				+ phone + ", created=" + created + ", updated=" + updated + "]";
	}
}
