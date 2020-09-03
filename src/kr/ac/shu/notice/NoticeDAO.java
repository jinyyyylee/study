package kr.ac.shu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NoticeDAO {
	private Connection con;

	public NoticeDAO(Connection con) {
		super();
		this.con = con;
	}
	/**
	 * 공지사항 게시판 게시글 수정
	 * @param idx : 수정하고자하는 게시글 idx값
	 * @return true : 쿼리실행 성공, false : 쿼리실행 실패
	 */
	public boolean updateDataNotice(NoticeDTO notice) {
		boolean result = true;
		String sql ="update notice set updated=?, subject=?, content=? where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setTimestamp(1, notice.getUpdated());
			pstmt.setString(2, notice.getSubject());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, notice.getIdx());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 공지사항 게시판 글삭제
	 * @param idx : 삭제할 게시글 idx 값
	 * @return true : 쿼리실행 성공, false : 쿼리실행 실패
	 */
	public boolean deleteDataNotice(int idx) {
		boolean result = true;
		String sql = "delete from notice where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 공지사항 게시글 보기 -> viewCount 1 증가
	 * @param idx : Notice 테이블 idx값
	 * @return true : 쿼리실행 성공, false : 쿼리실행 실패
	 */
	public boolean updateViewCount(int idx) {
		boolean result = true;
		String sql="update notice set viewCount=viewCount+1 where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * idx값에 해당하는 게시글 검색
	 * @param idx : 게시글을 검색하기 위한 키값
	 * @return Notice 객체(게시글 정보)
	 */
	public NoticeDTO selectIdxData(int idx) {
		NoticeDTO notice = new NoticeDTO();
		String sql="select idx, userIdx, subject, content, viewCount, created, updated, "
				+ "(select name from member where idx=userIdx) as userName from notice where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				notice.setIdx(rs.getInt("idx"));
				notice.setUserIdx(rs.getInt("userIdx"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setCreated(rs.getTimestamp("created"));
				notice.setUpdated(rs.getTimestamp("updated"));
				notice.setUserName(rs.getString("userName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notice;
	}
	
	/**
	 * 공지사항 게시판 게시글 등록
	 * @param notice : 게시글 등록 정보 NoticeDTO 객체
	 * @return true 쿼리실행 성공, false 쿼리실행 실패
	 */
	public boolean insertData(NoticeDTO notice) {
		boolean result = true;
		String sql="insert into notice values(idx_seq.NEXTVAL,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, notice.getUserIdx());
			pstmt.setString(2, notice.getSubject());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, notice.getViewCount());
			pstmt.setTimestamp(5, notice.getCreated());
			pstmt.setTimestamp(6, notice.getUpdated());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 공지사항 게시판 전체 글 검색
	 * @return 공지사항 게시판 검색 결과 : ArrayList<NoticeDTO> 객체
	 */
	public ArrayList<NoticeDTO> selectAllData(){
		ArrayList<NoticeDTO> array1 = new ArrayList<NoticeDTO>();
		String sql="select idx, userIdx, subject, content, viewCount, created, updated, "
				+ "(select name from member where idx=userIdx) as userName from notice";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO notice = new NoticeDTO();
				notice.setIdx(rs.getInt("idx"));
				notice.setUserIdx(rs.getInt("userIdx"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setCreated(rs.getTimestamp("created"));
				notice.setUpdated(rs.getTimestamp("updated"));
				notice.setUserName(rs.getString("userName"));
				
				array1.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array1;
	}
}
















