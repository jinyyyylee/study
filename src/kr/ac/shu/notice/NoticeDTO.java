package kr.ac.shu.notice;

import java.sql.Timestamp;

public class NoticeDTO {
	private int idx;
	private int userIdx;
	private String userName;
	private String subject;
	private String content;
	private int viewCount;
	private Timestamp created;
	private Timestamp updated;
	public NoticeDTO() {
		super();
	}
	public NoticeDTO(int idx, int userIdx, String userName, String subject, String content, int viewCount,
			Timestamp created, Timestamp updated) {
		super();
		this.idx = idx;
		this.userIdx = userIdx;
		this.userName = userName;
		this.subject = subject;
		this.content = content;
		this.viewCount = viewCount;
		this.created = created;
		this.updated = updated;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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
		return "NoticeDTO [idx=" + idx + ", userIdx=" + userIdx + ", userName=" + userName + ", subject=" + subject
				+ ", content=" + content + ", viewCount=" + viewCount + ", created=" + created + ", updated=" + updated
				+ "]";
	}
}
