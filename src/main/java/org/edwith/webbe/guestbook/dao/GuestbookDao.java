package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * jdbc 코드를 작성하는 부분
 * 1. db연결정보 작성
 * 2. db에 접근하는 쿼리 작성
 *  - java 코드를 통해, sql로 바꾸는 과정이라고 생각
 */
public class GuestbookDao {

    private static String dbUrl = "jdbc:mysql://localhost:3306/project_b?characterEncoding=UTF-8&serverTimezone=UTC";
    private static String dbUser = "projectuser-a";
    private static String dbPassword = "00000000";

    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();

        // 코드를 작성하세요.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 1. 드라이버 로딩하기
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "select id, name, content, regdate from guestbook"; // 쿼리 만들기

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword); // 2. connection 객체 얻기
             PreparedStatement ps = conn.prepareStatement(sql)) { // 3. statement 객체 얻기

            try (ResultSet rs = ps.executeQuery()) {// 4. statement 객체 실행

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String content = rs.getString("content");
                    Date regdate = rs.getTimestamp("regdate");

                    Guestbook guestbook = new Guestbook(Long.valueOf(id), name, content, regdate);
                    list.add(guestbook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void addGuestbook(Guestbook guestbook){
        // 코드를 작성하세요.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 1. 드라이버 로딩하기
        } catch (Exception e) {
            e.printStackTrace();
        }

        // auto increment이므로, id값은 insert하지 않는다
        String sql = "insert into guestbook (name, content, regdate) values (?,?,?,?)"; // 쿼리 만들기

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword); // 2. connection 객체 얻기
             PreparedStatement ps = conn.prepareStatement(sql)) { // 3. statement 객체 얻기
            ps.setString(2, guestbook.getName());
            ps.setString(3, guestbook.getContent());
            ps.setTimestamp(4, new java.sql.Timestamp(guestbook.getRegdate().getTime()));

            ps.executeUpdate();// 4. statement 객체 실행

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
