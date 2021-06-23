import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Menus extends JFrame{
    JTable table;
    JScrollPane scrollPane;
    Connection conn;
    Statement stmt;
    ResultSet rs;
    Container c;
    int i=0;

    Menus(String menu){
        super("식당 메뉴"); //타이틀
        c = getContentPane();
        c.setLayout(new FlowLayout());
        menuTable(menu);

        this.setSize(500,310);
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2 +450,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setVisible(true);


    }

    private void menuTable(String menu){
        if (menu.equals("닥터로빈"))
            i=1;
        else if(menu.equals("전주식당"))
            i=2;
        else if(menu.equals("부대통령뚝배기"))
            i=3;
        else if(menu.equals("우마이"))
            i=4;
        else if(menu.equals("고척돈까스"))
            i=5;
        else if(menu.equals("뜸들이다"))
            i=6;
        else if(menu.equals("동명"))
            i=7;
        else if(menu.equals("진스시"))
            i=8;
        else if(menu.equals("순대타운"))
            i=9;
        else if(menu.equals("하우마라"))
            i=10;

        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from menuPlus where id = '" + i + "'");
            int k =0;
            String header[] = {"번호", "메뉴", "가격"};
            String contents[][] = new String[15][3];
            while (rs.next()){
                String name = rs.getString("name");
                String price = rs.getString("price");
                String id = rs.getString("id");
                System.out.println("id = " + id);
                contents[k][0] = id;
                contents[k][1] = name;
                contents[k][2] = price;

                k++;
            }
            table = new JTable(contents, header);

            scrollPane = new JScrollPane(table);
            c.add(scrollPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
