package DatabaseConnect;

import java.sql.*;
import java.util.*;


public class Main {
   static Connection conn;
   static Statement stat;
   public Main(){
      database();
   }
   static void database() {
      try {
         //동적 클래스 로드
         Class.forName("com.mysql.cj.jdbc.Driver"); //DB연결 객체 생성
         conn = DriverManager.getConnection("jdbc:mysql://13.125.232.43:3306/study","lion","1234");
         stat = conn.createStatement();
         System.out.println("DB접속");
      } catch (Exception e) {
         e.printStackTrace(System.out);
      }
   }

   private static void disconnect() {
      try {
         if (stat != null) {
            stat.close();
         }
         if (conn != null) {
            conn.close();
         }
      } catch (Exception ex) {
      }
   }

   static boolean checkId(int id) {
      //select 할 때만 필요, 결과를 저장한다.
      ResultSet rs;
      String sql = null;          
      java.sql.PreparedStatement stat;
      int count = 0;
      try {
         sql = "select * from table01 where id=" + id;    
         System.out.println("id 유무 확인");
         stat = conn.prepareStatement(sql);
         rs = stat.executeQuery(sql);
         
         System.out.println("=================================");
         while (rs.next()) {
            String user_id = rs.getString(1);
            String user_name = rs.getString(2);
            String user_age = rs.getString(3);
            String user_city = rs.getString(4);
            System.out.println(user_id + " \t" + user_name + " \t" + user_age + " \t" + user_city);
            System.out.println("---------------------------------");
            count++;
         }
         System.out.println("=================================");
      
   
         
      } catch (SQLException e) {
         System.out.println("읽기 실패");
         e.printStackTrace();
      }
      
      if (count > 0) {
         return true;
      }else {
         return false;
      }
      

   }
   

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      Main m = new Main();
      ResultSet rs;
      boolean flag = false;
      Scanner sc = new Scanner(System.in);
      while(!flag) {
         System.out.println("사용할 기능의 번호를 입력하세요");
         System.out.println("1. Create 2. Read 3. Update 4. Delete 7. 종료");
         while (!sc.hasNextInt()) {
            System.out.print("정수를 입력해주세요 : ");
            sc.next(); 
         }
         int num = sc.nextInt();

         String sql = null;          
         java.sql.PreparedStatement stat;

         switch (num) {
         case 1:      
            //Create
            try {
               System.out.println("쿼리 시작");
               sql = "insert into table01(name, age, city) values(?,?,?)";     
               stat = conn.prepareStatement(sql);
               System.out.println("이름: ");
               String name = sc.next();
               System.out.println("나이: ");
               int age = sc.nextInt();
               System.out.println("사는 도시: ");
               String city = sc.next();

               stat.setString(1, name);
               stat.setInt(2, age);
               stat.setString(3, city);
               stat.executeUpdate();
               System.out.println("쿼리 성공");
            } catch (SQLException e) {
               System.out.println("삽입 실패");
               e.printStackTrace();
            }

            break;
         case 2:
            //Read
            // select 쿼리
            // ** 정리가 필요

            try {
               sql = "select * from table01";    
               System.out.println("select 쿼리 시작");
               stat = conn.prepareStatement(sql);
               rs = stat.executeQuery(sql);
               System.out.println("아이디" + " \t" + "이름" + " \t" + "나이" + " \t" + "도시");
               System.out.println("=================================");
               while (rs.next()) {
                  String user_id = rs.getString(1);
                  String user_name = rs.getString(2);
                  String user_age = rs.getString(3);
                  String user_city = rs.getString(4);
                  System.out.println(user_id + " \t" + user_name + " \t" + user_age + " \t" + user_city);
                  System.out.println("---------------------------------");
               }
               System.out.println("=================================");

            } catch (SQLException e) {
               System.out.println("읽기 실패");
               e.printStackTrace();
            }

            break;
         case 3:
            //Update
            // update 쿼리
            //형식 : update table01 set age = 99 where name = '이랑이';

         
            try {
               System.out.println("쿼리 시작");

               System.out.println("1.이름 바꾸기 2. 도시 바꾸기");
               int temp = sc.nextInt();
               switch(temp) {
               case 1: 

                  System.out.println("이름을 바꾸고싶은 ID를 입력하세요.");
                  sc.nextLine();
                  while (!sc.hasNextInt()) {
                     System.out.print("정수를 입력해주세요 : ");
                     sc.next(); 
                  }
                  int id = sc.nextInt();
               
                  System.out.println("새로운 이름을 입력하세요.");
                  String name = sc.next();

                  if(checkId(id)) {
                     sql = "update table01 set name = '"+ name +"' where id ="+id;  
                     stat = conn.prepareStatement(sql);
                     stat.executeUpdate();
                     
                  }else {
                     System.out.println("없는 id 입니다.");
                  }
                  
                  break;

               case 2:
                  System.out.println("도시를 바꾸고 싶은 ID 입력하세요.");
                  sc.nextLine();
                  while (!sc.hasNextInt()) {
                     System.out.print("정수를 입력해주세요 : ");
                     sc.next(); 
                  }
                  int id2 = sc.nextInt();
               
                  System.out.println("새로운 도시를 입력하세요.");
                  String city = sc.next();
                  
                  if(checkId(id2)) {
                     sql = "update table01 set city = '"+ city +"' where id ="+id2;  
                     stat = conn.prepareStatement(sql);
                     stat.executeUpdate();
                     
                  }else {
                     System.out.println("없는 id 입니다.");
                  }
                  

                  
                  break;
               }




            } catch (SQLException e) {
               e.printStackTrace();
            }
            break;
         case 4:
            //Delete

            // delete 쿼리
            try {

               System.out.println("삭제할 아이디를 입력하세요.");
               sc.nextLine();
               while (!sc.hasNextInt()) {
                  System.out.print("정수를 입력해주세요 : ");
                  sc.next(); 
               }
               int delete_id = sc.nextInt();
               if(checkId(delete_id)) {
                  sql = "delete from table01 where id='" + delete_id + "'";
                  stat = conn.prepareStatement(sql);
                  int count = stat.executeUpdate();
                  System.out.println("삭제 완료 개수 : " + count);
               }else {
                  System.out.println("없는 id 입니다.");
               }
               
               

            } catch (SQLException e) {
               System.out.println("삭제 실패");
               e.printStackTrace();
            }

            break;
         case 7:
            flag = true;
         default:
            System.out.println("없는 선택지입니다.");
            break;
         }


      }

      System.out.println("DB가 종료되었습니다.");
      disconnect();




   }


}