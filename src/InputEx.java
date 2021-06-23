
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class InputEx extends JFrame {
    //변수 설정
    JButton btnInsert, btnDelete, btnUpdate, btnSelect, btnSearch, btnRe, btnOn;
    JTextField tfId, tfName, tfType, tfSearch, tfPrice, tfDayOff,tfGood, tfStars, tfDist, tfVisit, tfTel,price;
    JScrollPane scrollPane;
    JTextArea ta;
    JRadioButton rbId, rbName, rbType;
    Image Iconimg;
    JPanel p,search;
    JComboBox<String> shoplist;
    Connection conn;
    Statement stmt,stmt2,stmt3;
    ResultSet rs,rcount,rdate;
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
        this.setSize(300,550);
        this.setVisible(true);
    }
    private void createGUI() { //crud기능
        Container ca = getContentPane();
        ca.setLayout(new FlowLayout());
        JPanel c = new JPanel();
        c.setPreferredSize(new Dimension(200,600));
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
    private void plus() {   //front 보이는 부분
        try{
            LocalDate currentDate = LocalDate.now();
            String resultStr = null;
            resultStr = JOptionPane.showInputDialog("어제 방문하신 식당을 입력해주세요.", "X");
            System.out.println("resultStr = " + resultStr);
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

            String searchSql = "";


            searchSql = String.format("update bestFood set visit =  '%s'  where name =    '%s'   ", currentDate,resultStr);

            stmt.executeUpdate(searchSql);
            rcount = stmt2.executeQuery("select * from bestFood;");
            rs = stmt.executeQuery("select name from bestFood;"); //식당명 가져오기


            BGcolor=new Color(178,235,244,80);
            Iconimg = new ImageIcon("logo.png").getImage();
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
            search.setLayout(new GridLayout(2,0));

            while (rcount.next()){
                i++;
            }

            String nana[] = new String[i];
            i=0;
            while (rs.next()){
                nana[i] = rs.getString("name");
                System.out.println(nana[i]);
                i++;
            }


            btnRe = new JButton("추천");
            btnRe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        conn = DBConn.dbConnection();
                        stmt3 = conn.createStatement();
                        rdate = stmt3.executeQuery("select * from bestFood order by visit desc;");
                        String line ="";
                        ta.setText("    번호    식당명    방문일\n");
                        ta.append("---------------------\n");
                        while (rdate.next()){
                            String name = rdate.getString("name");
                            String visit = rdate.getString("visit");
                            String id = rdate.getString("id");
                            line = " | " +id + " | " +name + " |    "+visit+"\n";
                            System.out.println("rdate => " +line);
                            ta.append(line);
                        }
                        stmt3.close();
                    }catch (Exception c) {
                        c.printStackTrace();
                    }

                }
            });
            c.add(btnRe);



            search.add(new JLabel("점포검색",JLabel.CENTER)); //식당 표시
            shoplist = new JComboBox<String>(nana);
            search.add(shoplist);
            search.add(new JLabel("가격검색(이하)",JLabel.CENTER));
            search.add(price=new JTextField());

            c.add(search);

            //db CRUD 창
            //검색부분
            tfSearch = new JTextField(18);
            rbId = new JRadioButton("번호", true);
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
            Image img = new ImageIcon("menu40.jpg").getImage(); //이미지 선언

            ta = new JTextArea(15,15){
                { setOpaque( false ) ; }
                public void paintComponent(Graphics g){
                    g.drawImage(img,0,0,null);       //이미지 그리기
                    super.paintComponent(g);
                }
            };

            ta.setFont(new Font("휴먼엑스포",Font.PLAIN,17));
            scrollPane = new JScrollPane(ta);
            c.add(scrollPane);
            stmt.close();
            stmt2.close();

            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    //db 선택
    private void dbSelect() {       //조회
        try {
            conn = DBConn.dbConnection(); //db 설정 클래스 처리
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from bestFood;");
            String line ="";
            ta.setText("번호    식당명    종류  \n");
            ta.append("---------------------\n");
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
        }
        catch (Exception e) {
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
            String  price= tfPrice.getText().toString();
            String  dayOff= tfDayOff.getText().toString();
            String  good= tfGood.getText().toString();
            String  stars= tfStars.getText().toString();
            String  dist= tfDist.getText().toString();
            String  visit= tfVisit.getText().toString();
            String  tel= tfTel.getText().toString();

            if (id.equals("") || id.equals("채워주세요!")){
                tfId.setText("채워주세요!");     return; }
            if (name.equals("") || name.equals("채워주세요!!")){
                tfName.setText("채워주세요!!");  return; }
            if (foodType.equals("") || foodType.equals("채워주세요!")){
                tfType.setText("채워주세요!");     return; }
            if (price.equals("") || price.equals("채워주세요!")){
                tfPrice.setText("채워주세요!");     return; }
            if (dayOff.equals("") || dayOff.equals("채워주세요!")){
                tfDayOff.setText("채워주세요!");     return; }
            if (good.equals("") || good.equals("채워주세요!")){
                tfGood.setText("채워주세요!");     return; }
            if (stars.equals("") || stars.equals("채워주세요!")){
                tfStars.setText("채워주세요!");     return; }
            if (dist.equals("") || dist.equals("채워주세요!")){
                tfDist.setText("채워주세요!");     return; }
            if (visit.equals("") || visit.equals("채워주세요!")){
                tfVisit.setText("채워주세요!");     return; }
            if (tel.equals("") || tel.equals("채워주세요!")){
                tfTel.setText("채워주세요!");     return; }

            //data 입력 구문
            stmt.executeUpdate("insert into bestFood values('" + id + "', '" + name + "', '" +foodType+"','" + price +
                    "','" + dayOff + "','" +good+"','" +stars+ "','" + dist + "','" + visit + "','" + tel+ "');");
            System.out.println(name + "입력 완료");
            tfId.setText("");
            tfName.setText("");
            tfType.setText("");
            tfPrice.setText("");
            tfDayOff.setText("");
            tfGood.setText("");
            tfStars.setText("");
            tfDist.setText("");
            tfVisit.setText("");
            tfTel.setText("");

            stmt.close();
            conn.close();
        }catch (SQLIntegrityConstraintViolationException a){
            tfId.setText("구분번호 중복!!!");
        }
        catch (Exception e) {
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
            String  price= tfPrice.getText().toString();
            String  dayOff= tfDayOff.getText().toString();
            String  good= tfGood.getText().toString();
            String  stars= tfStars.getText().toString();
            String  dist= tfDist.getText().toString();
            String  visit= tfVisit.getText().toString();
            String  tel= tfTel.getText().toString();

            if (in_id.equals("") || in_id.equals("채워주세요!")){
                tfId.setText("채워주세요!");     return; }
            if (in_name.equals("") || in_name.equals("채워주세요!!")){
                tfName.setText("채워주세요!!");  return; }
            if (in_Type.equals("") || in_Type.equals("채워주세요!")){
                tfType.setText("채워주세요!");     return; }
            if (price.equals("") || price.equals("채워주세요!")){
                tfPrice.setText("채워주세요!");     return; }
            if (dayOff.equals("") || dayOff.equals("채워주세요!")){
                tfDayOff.setText("채워주세요!");     return; }
            if (good.equals("") || good.equals("채워주세요!")){
                tfGood.setText("채워주세요!");     return; }
            if (stars.equals("") || stars.equals("채워주세요!")){
                tfStars.setText("채워주세요!");     return; }
            if (dist.equals("") || dist.equals("채워주세요!")){
                tfDist.setText("채워주세요!");     return; }
            if (visit.equals("") || visit.equals("채워주세요!")){
                tfVisit.setText("채워주세요!");     return; }
            if (tel.equals("") || tel.equals("채워주세요!")){
                tfTel.setText("채워주세요!");     return; }
            //db 수정 문법
            stmt.executeUpdate("update bestFood set id = '" + in_id + "', name = '" + in_name + "', foodType = '" +in_Type+
                    "', price = '" + price + "', dayOff = '" + dayOff + "', good ='" +good+"', stars = '" +stars+ "', dist = '" + dist
                    + "', visit = '" + visit + "', tel = '" + tel+ "';");
            System.out.println(in_name + "수정 완료");
            tfId.setText("");
            tfName.setText("");
            tfType.setText("");
            tfPrice.setText("");
            tfDayOff.setText("");
            tfGood.setText("");
            tfStars.setText("");
            tfDist.setText("");
            tfVisit.setText("");
            tfTel.setText("");
            stmt.close();
            conn.close();
        }catch (SQLIntegrityConstraintViolationException a){
            tfId.setText("없는 번호 입니다!!!");
        }
        catch (Exception e) {
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
            addTa(rs);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void event(){
        try{
            shoplist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        conn = DBConn.dbConnection(); //db 설정 클래스 처리
                        stmt = conn.createStatement();
                        String searchN = "";
                        String shopS=(String) shoplist.getSelectedItem();
                        searchN = "select * from bestFood where name = '" + shopS + "';";
                        rs = stmt.executeQuery(searchN);
                        ta.setText("");
                        addTa(rs);
                        stmt.close();
                        conn.close();
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
            });
            price.addActionListener(new ActionListener() //음식가격 검색 이벤트
            {
                public void actionPerformed(ActionEvent event)
                {
                    try {
                        conn = DBConn.dbConnection(); //db 설정 클래스 처리
                        stmt = conn.createStatement();
                        String priceS=price.getText();
                        String searchSQL = "SELECT * FROM bestFood WHERE price<= '" +priceS+ "' ;";
                        rs = stmt.executeQuery(searchSQL);
                        String line ="";
                        ta.setText("번호    이름    평균가  \n");
                        ta.append("---------------------\n");
                        while (rs.next()){
                            String name = rs.getString("name");
                            String price = rs.getString("price");
                            String id = rs.getString("id");
                            line = " | " +id + " | " +name + " | "+price+"\n";
                            System.out.println("rs => " +line);
                            ta.append(line);
                        }
                        stmt.close();
                        conn.close();
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addTa(ResultSet rs){
        try {
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
                line = "\n"+"식당명 : "+ name +"\n"+
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

    public static void main(String[] args) {
        new InputEx();
    }

}

