package kr.ac.shu.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * MemberDAO 클래스
 * @author 정원수(20190520)
 *
 */
public class MemberDAO {
	private Connection con;

	/**
	 * MemberDAO(Connection con) 생성자
	 * @param con 데이터베이스 연결 객체
	 */
	public MemberDAO(Connection con) {
		super();
		this.con = con;
	}
	/**
	 * idx 정보를 이용한 회원정보 검색
	 * @param idx : 회원정보 검색 시 사용할 idx 값
	 * @return 회원정보 검색 결과 : MemberDTO 객체
	 */
	public MemberDTO selectMemberIdx(int idx) {
		MemberDTO member = new MemberDTO();
		String sql = "select idx, name, email, passwd, phone, created, updated from member where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member.setIdx(rs.getInt("idx"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPasswd(rs.getString("passwd"));
				member.setPhone(rs.getString("phone"));
				member.setCreated(rs.getTimestamp("created"));
				member.setUpdated(rs.getTimestamp("updated"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}

	/**
	 * 회원 정보 수정
	 * @param member : 수정할 회원 정보 객체
	 * @return true: 쿼리수행 성공, false : 쿼리수행 실패
	 */
	public boolean updateData(MemberDTO member) {
		boolean result = true;
		String sql="update member set updated=?, name=?, email=?, passwd=?, phone=? where idx=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setTimestamp(1, member.getUpdated());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPasswd());
			pstmt.setString(5, member.getPhone());
			pstmt.setInt(6, member.getIdx());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 로그인 시 아이디 체크
	 * @param email : 로그인 아이디 체크시 사용할 아이디
	 * @return  검색 결과 : MemberDTO 객체
	 */
	public MemberDTO selectMemberEmail(String email) {
		MemberDTO member = new MemberDTO();
		String sql="select idx, name, email, passwd, phone, created, updated from member where email=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member.setIdx(rs.getInt("idx"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPasswd(rs.getString("passwd"));
				member.setPhone(rs.getString("phone"));
				member.setCreated(rs.getTimestamp("created"));
				member.setUpdated(rs.getTimestamp("updated"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	
	
	/**
	 * 회원정보 등록
	 * @param member : 회원 정보 객체
	 * @return true : 쿼리실행 성공, false : 쿼리실행 실패
	 */
	public boolean insertData(MemberDTO member) {
		boolean result = true;
		String sql="insert into member values(idx_seq.NEXTVAL,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getPhone());
			pstmt.setTimestamp(5, member.getCreated());
			pstmt.setTimestamp(6, member.getUpdated());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 회원정보 등록 전 회원 등록 유무 확인
	 * @param email : 회원등록 확인시 사용할 이메일 주소
	 * @return true : 회원정보 있음, false : 회원정보 없음
	 */
	public boolean isConfirmEmailMember(String email) {
		boolean result = true;
		String sql="select idx, name, email, passwd, phone, created, updated from member where email=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}













