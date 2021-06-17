
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class InputEx extends JFrame {
    //변수 설정
    JButton btnInsert, btnDelete, btnUpdate, btnSelect, btnSearch, btnCreate, btnOn;
    JTextField tfId, tfName, tfType, tfSearch, tfPrice, tfDayOff,tfGood, tfStars, tfDist, tfVisit, tfTel;
    JTextArea ta;
    JRadioButton rbId, rbName, rbType;
    Image Iconimg;
    JPanel p,search;
    JComboBox<String> shoplist;
    Connection conn;
    Statement stmt,stmt2;
    ResultSet rs,rcount;
    Boolean onOff;
    Color BGcolor;
    int i=0;

    public InputEx(){
        this.setTitle("맛집 관리 프로젝트");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();
        plus();
        event();
        //버튼 이벤트 설정
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbInsert();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbDelete();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbUpdate();
            }
        });
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbSelect();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbSearch();
            }
        });
        this.setSize(300,500);
        this.setVisible(true);
    }
    private void createGUI() {
        Container ca = getContentPane();
        ca.setLayout(new FlowLayout());
        JPanel c = new JPanel();
        c.setPreferredSize(new Dimension(200,500));
        c.setLayout(new GridLayout(15,2,5,5));
        c.add(new JLabel("구분        "));
        tfId = new JTextField(20);
        c.add(tfId);
        c.add(new JLabel("이름        "));
        tfName = new JTextField(20);
        c.add(tfName);
        c.add(new JLabel("종류        "));
        tfType = new JTextField(20);
        c.add(tfType);
        c.add(new JLabel("평균가격"));
        tfPrice= new JTextField(20);
        c.add(tfPrice);
        c.add(new JLabel("휴무일   "));
        tfDayOff= new JTextField(20);
        c.add(tfDayOff);
        c.add(new JLabel("추천메뉴"));
        tfGood= new JTextField(20);
        c.add(tfGood);
        c.add(new JLabel("별점        "));
        tfStars = new JTextField(20);
        c.add(tfStars);
        c.add(new JLabel("거리        "));
        tfDist= new JTextField(20);
        c.add(tfDist);
        c.add(new JLabel("방문일   "));
        tfVisit= new JTextField(20);
        c.add(tfVisit);
        c.add(new JLabel("전화번호"));
        tfTel= new JTextField(20);
        c.add(tfTel);
        btnInsert = new JButton("입력");
        c.add(btnInsert);
        btnUpdate = new JButton("수정");
        c.add(btnUpdate);
        btnDelete = new JButton("삭제");
        c.add(btnDelete);
        btnSelect = new JButton("조회");
        c.add(btnSelect);
        ca.add(BorderLayout.CENTER,c);
        onOff = false;
        c.setVisible(false);
        btnOn = new JButton("상세");
        btnOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOff = !onOff;
                c.setVisible(onOff);
            }
        });
        ca.add(btnOn);
    }
    //화면 만들기
    private void plus() {
        try{
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

//
            rcount = stmt2.executeQuery("select * from bestFood;");
            rs = stmt.executeQuery("select name from bestFood;"); //식당명 가져오기

            BGcolor=new Color(178,235,244,80);
            Iconimg = new ImageIcon("logo1.png").getImage();
            setIconImage(Iconimg);
            Container c = getContentPane();
            c.setLayout(new FlowLayout());

            search=new JPanel() {
                public void paintComponent(Graphics g) //바탕색 설정
                {
                    g.setColor(BGcolor);
                    g.fillRect(0,0,2000,100);
                    setOpaque(false);
                    super.paintComponent(g);
                }
            };
            search.setLayout(new GridLayout(0,4));

            while (rcount.next()){
                String name = rcount.getString("name");
                i++;
            }
            stmt2.close();
            String nana[] = new String[i];
            i=0;
            while (rs.next()){
                nana[i] = rs.getString("name");
                System.out.println(nana[i]);
                i++;
            }
            stmt.close();
            conn.close();
            search.add(new JLabel("점포검색",JLabel.CENTER)); //식당 표시
            shoplist = new JComboBox<String>(nana);
            search.add(shoplist);

            c.add(search);
            // TODO: 2021-06-16 rs를 이용해서 점포검색을 합시다! 

            //db CRUD 창
            //검색부분
            tfSearch = new JTextField(18);
            rbId = new JRadioButton("학번", true);
            rbName = new JRadioButton("이름");
            rbType = new JRadioButton("종류");
            ButtonGroup group = new ButtonGroup();
            group.add(rbId);
            group.add(rbName);
            group.add(rbType);
            btnSearch = new JButton("검색");
            JPanel pn1 = new JPanel();
            pn1.add(new JLabel(" "));
            pn1.add(tfSearch);
            pn1.add(btnSearch);
            JPanel pn2 = new JPanel();
            pn2.add(rbId);
            pn2.add(rbName);
            pn2.add(rbType);
            JPanel pMiddle = new JPanel(new BorderLayout(0,0));
            pMiddle.add(BorderLayout.NORTH, pn1);
            pMiddle.add(BorderLayout.CENTER, pn2);
            TitledBorder tb = new TitledBorder("검색");
            pMiddle.setBorder(tb);

            //출력 장소 만들기
            c.add(pMiddle);
            ta = new JTextArea(30, 15);
            ta.setFont(new Font("휴먼엑스포",Font.PLAIN,17));
            c.add(ta);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    //db 선택
    private void dbSelect() {
        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from bestFood;");
            String line ="";
            ta.setText("    id              name            Type  \n");
            ta.append("-----------------------------------------------\n");
            while (rs.next()){
                String name = rs.getString("name");
                String foodType = rs.getString("foodType");
                String id = rs.getString("id");
                line = " | " +id + " | " +name + " |    "+foodType+"\n";
                System.out.println("rs => " +line);
                ta.append(line);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //data 삭제
    private void dbDelete() {

        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            //삭제할 아이디
            String inputid = tfId.getText().toString();
            //data 삭제 구문
            stmt.executeUpdate("delete from bestFood where id = '" + inputid + "'");
            System.out.println(inputid + "입력 완료");
            tfId.setText("");
            tfName.setText("");
            tfType.setText("");
            stmt.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    //data 삽입
    private void dbInsert() {
        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            String id = tfId.getText().toString();
            String name = tfName.getText().toString();
            String foodType = tfType.getText().toString();
            //data 입력 구문
            stmt.executeUpdate("insert into bestFood(id,name,foodType) values('" + id + "', '" + name + "', '" +foodType+"');");
            System.out.println(name + "입력 완료");
            tfId.setText("");
            tfName.setText("");
            tfType.setText("");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //db 업데이트
    private void dbUpdate() {
        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            String in_id = tfId.getText().toString();
            String in_name = tfName.getText().toString();
            String in_Type = tfType.getText().toString();
            //db 수정 문법
            stmt.executeUpdate("update bestFood set foodType = '" + in_Type +"', name = '" + in_name + "' where id = '" + in_id + "'");
            System.out.println(in_name + "수정 완료");
            tfId.setText("");
            tfName.setText("");
            tfType.setText("");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //db 검색
    private void dbSearch() {
        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            String searchText = tfSearch.getText().toString();
            String searchSql = "";
            if (rbId.isSelected()){
                searchSql = "select * from bestFood where id = '" + searchText + "';";
            } else if (rbName.isSelected()){
                searchSql = "select * from bestFood where name = '" + searchText + "';";
            }else {
                searchSql = "select * from bestFood where foodType = '" + searchText + "';";
            }
            rs = stmt.executeQuery(searchSql);
            ta.setText("");
            String line ="";
            while (rs.next()){
                String name = rs.getString("name");
                String foodType = rs.getString("foodType");
                String id = rs.getString("id");
                String price = rs.getString("price");
                String  dayOff= rs.getString("dayOff");
                String good = rs.getString("good");
                String stars = rs.getString("stars");
                String dist = rs.getString("dist");
                String visit = rs.getString("visit");
                String tel = rs.getString("tel");
                line = "식당명 : "+ name +"\n"+
                        "음식종류 : " + foodType+"\n"+
                        "평균가 : " +price +"\n"+
                        "쉬는날 : " +dayOff +"\n"+
                        "추천메뉴 : " + good +"\n"+
                        "별점 : " +stars +"\n"+
                        "거리 : " +dist +"\n"+
                        "방문일 : " + visit +"\n"+
                        "전화번호 : " + tel;
                System.out.println("rs => " +line);
                ta.append(line);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void event(){
        try{
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            shoplist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchN = "";
                    String shopS=(String) shoplist.getSelectedItem();
                    searchN = "select * from bestFood where name = '" + shopS + "';";
                    try {
                        rs = stmt.executeQuery(searchN);
                        ta.setText("");
                        String line ="";
                        while (rs.next()){
                            String name = rs.getString("name");
                            String foodType = rs.getString("foodType");
                            String id = rs.getString("id");
                            String price = rs.getString("price");
                            String  dayOff= rs.getString("dayOff");
                            String good = rs.getString("good");
                            String stars = rs.getString("stars");
                            String dist = rs.getString("dist");
                            String visit = rs.getString("visit");
                            String tel = rs.getString("tel");
                            line = "식당명 : "+ name +"\n"+
                                    "음식종류 : " + foodType+"\n"+
                                    "평균가 : " +price +"\n"+
                                    "쉬는날 : " +dayOff +"\n"+
                                    "추천메뉴 : " + good +"\n"+
                                    "별점 : " +stars +"\n"+
                                    "거리 : " +dist +"\n"+
                                    "방문일 : " + visit +"\n"+
                                    "전화번호 : " + tel;
                            System.out.println("rs => " +line);
                            ta.append(line);
                        }
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new InputEx();
    }


}
