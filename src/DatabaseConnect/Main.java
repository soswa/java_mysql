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
         //���� Ŭ���� �ε�
         Class.forName("com.mysql.cj.jdbc.Driver"); //DB���� ��ü ����
         conn = DriverManager.getConnection("jdbc:mysql://13.125.232.43:3306/study","lion","1234");
         stat = conn.createStatement();
         System.out.println("DB����");
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
      //select �� ���� �ʿ�, ����� �����Ѵ�.
      ResultSet rs;
      String sql = null;          
      java.sql.PreparedStatement stat;
      int count = 0;
      try {
         sql = "select * from table01 where id=" + id;    
         System.out.println("id ���� Ȯ��");
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
         System.out.println("�б� ����");
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
         System.out.println("����� ����� ��ȣ�� �Է��ϼ���");
         System.out.println("1. Create 2. Read 3. Update 4. Delete 7. ����");
         while (!sc.hasNextInt()) {
            System.out.print("������ �Է����ּ��� : ");
            sc.next(); 
         }
         int num = sc.nextInt();

         String sql = null;          
         java.sql.PreparedStatement stat;

         switch (num) {
         case 1:      
            //Create
            try {
               System.out.println("���� ����");
               sql = "insert into table01(name, age, city) values(?,?,?)";     
               stat = conn.prepareStatement(sql);
               System.out.println("�̸�: ");
               String name = sc.next();
               System.out.println("����: ");
               int age = sc.nextInt();
               System.out.println("��� ����: ");
               String city = sc.next();

               stat.setString(1, name);
               stat.setInt(2, age);
               stat.setString(3, city);
               stat.executeUpdate();
               System.out.println("���� ����");
            } catch (SQLException e) {
               System.out.println("���� ����");
               e.printStackTrace();
            }

            break;
         case 2:
            //Read
            // select ����
            // ** ������ �ʿ�

            try {
               sql = "select * from table01";    
               System.out.println("select ���� ����");
               stat = conn.prepareStatement(sql);
               rs = stat.executeQuery(sql);
               System.out.println("���̵�" + " \t" + "�̸�" + " \t" + "����" + " \t" + "����");
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
               System.out.println("�б� ����");
               e.printStackTrace();
            }

            break;
         case 3:
            //Update
            // update ����
            //���� : update table01 set age = 99 where name = '�̶���';

         
            try {
               System.out.println("���� ����");

               System.out.println("1.�̸� �ٲٱ� 2. ���� �ٲٱ�");
               int temp = sc.nextInt();
               switch(temp) {
               case 1: 

                  System.out.println("�̸��� �ٲٰ���� ID�� �Է��ϼ���.");
                  sc.nextLine();
                  while (!sc.hasNextInt()) {
                     System.out.print("������ �Է����ּ��� : ");
                     sc.next(); 
                  }
                  int id = sc.nextInt();
               
                  System.out.println("���ο� �̸��� �Է��ϼ���.");
                  String name = sc.next();

                  if(checkId(id)) {
                     sql = "update table01 set name = '"+ name +"' where id ="+id;  
                     stat = conn.prepareStatement(sql);
                     stat.executeUpdate();
                     
                  }else {
                     System.out.println("���� id �Դϴ�.");
                  }
                  
                  break;

               case 2:
                  System.out.println("���ø� �ٲٰ� ���� ID �Է��ϼ���.");
                  sc.nextLine();
                  while (!sc.hasNextInt()) {
                     System.out.print("������ �Է����ּ��� : ");
                     sc.next(); 
                  }
                  int id2 = sc.nextInt();
               
                  System.out.println("���ο� ���ø� �Է��ϼ���.");
                  String city = sc.next();
                  
                  if(checkId(id2)) {
                     sql = "update table01 set city = '"+ city +"' where id ="+id2;  
                     stat = conn.prepareStatement(sql);
                     stat.executeUpdate();
                     
                  }else {
                     System.out.println("���� id �Դϴ�.");
                  }
                  

                  
                  break;
               }




            } catch (SQLException e) {
               e.printStackTrace();
            }
            break;
         case 4:
            //Delete

            // delete ����
            try {

               System.out.println("������ ���̵� �Է��ϼ���.");
               sc.nextLine();
               while (!sc.hasNextInt()) {
                  System.out.print("������ �Է����ּ��� : ");
                  sc.next(); 
               }
               int delete_id = sc.nextInt();
               if(checkId(delete_id)) {
                  sql = "delete from table01 where id='" + delete_id + "'";
                  stat = conn.prepareStatement(sql);
                  int count = stat.executeUpdate();
                  System.out.println("���� �Ϸ� ���� : " + count);
               }else {
                  System.out.println("���� id �Դϴ�.");
               }
               
               

            } catch (SQLException e) {
               System.out.println("���� ����");
               e.printStackTrace();
            }

            break;
         case 7:
            flag = true;
         default:
            System.out.println("���� �������Դϴ�.");
            break;
         }


      }

      System.out.println("DB�� ����Ǿ����ϴ�.");
      disconnect();




   }


}