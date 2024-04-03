package com.project1.group5.frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class MovieChoiceFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener{

    //graphics 라이브러리를 사용하기 위한 객체
	Image buffImage; 
	Graphics buffg;
    Image character;
    JPanel panelForGraphics;

    //창 크기
    int f_width;
    int f_height;
    
    //캐릭터 이미지 경로
    String[] imageList = {"pondering_dorothy", "pondering_scarecrow","pondering_tin_man","pondering_cowardly_lion","good_witch_glinda","wicked_witch"};
    String[] choices = {"장르1","장르2","연도1","연도2","심의1","심의2","국가1","국가2","러닝타임1","러닝타임2","감독1","감독2"};
    //캐릭터 이미지 받아올 이미지 배열
    Image[] characters;
    
    //기타 임시 이미지들... 차후 삭제 예정
    Image ozo;
    Image ozland;
    Image rc;
    Image bc;

    //임시로 선택지 띄우기 위한 J라벨
    JLabel jl_left;
    JLabel jl_right;

    //임시로 선택지 클릭 영역 만들기 위한 도형 객체. 차후에 변수명 수정 예정
    Shape rc_s;
    Shape bc_s;

    //이미지를 넘기기 위한 변수
    int nthChacracter = 0;

    //키보드, 마우스 액션을 위한 변수
    boolean isKeyPressed = false;
    int mousex;
    int mousey;

    public MovieChoiceFrame(){
        f_width=1300;
        f_height=750;

        setSize(f_width,f_height);
        setResizable(false);//창 크기 조절 불가능
        setLocationRelativeTo(null);//위치 = 모니터 정중앙
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //이미지 받아오기. 디렉토리는 프로젝트 폴더에 맞게 수정
        String imgDir = "group5/src/main/java/com/project1/group5/frame/images/";
        characters = new Image[6];
        for(int i = 0;i<6;i++){
            characters[i] = new ImageIcon(imgDir+imageList[i]+".png").getImage();
        }
        ozo = new ImageIcon(imgDir+"ozo.png").getImage();
        ozland = new ImageIcon(imgDir+"ozland.png").getImage();
        rc = new ImageIcon(imgDir+"rc.png").getImage();
        bc = new ImageIcon(imgDir+"bc.png").getImage();

        //J라벨 임의로 위치 및 텍스트 조정
        jl_left = new JLabel(choices[nthChacracter*2]);
        jl_right = new JLabel(choices[nthChacracter*2+1]);
        jl_left.setBounds(f_width/2+200,f_height/2-75,200,30);
        jl_right.setBounds(f_width/2,f_height/2-75,200,30);
        jl_left.setFont(new Font(null, Font.BOLD, 30));
        jl_right.setFont(new Font(null, Font.BOLD, 30));

        //그래픽을 먼저 로딩시키기 위해 사진들을 전부 Jpanel에 붙여서 프레임 위에 붙일것임
        panelForGraphics = new JPanel() {
            //이하 전부 그래픽 올리는 메소드들. repaint()를 통해 재호출이 가능함.
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                buffImage = createImage(f_width, f_height);
                buffg = buffImage.getGraphics();
                update(g);
            }

            public void drawCharacter(){//0,250 위치에 500x500 캐릭터 이미지를 그려줘라는 뜻
                if (buffg != null) {
                    buffg.drawImage(characters[nthChacracter], 0, 250,500,500, this); 
                }
            }
            public void drawOzo(){//마우스 좌표 -50 위치에 주어진 크기의 오조 로고를 그려줘라는 뜻
                if (buffg != null) {
                    buffg.drawImage(ozo, mousex-50, mousey-50,170,170, this); 
                }
            }
            public void drawOzland(){//0,0 위치에 창 크기만큼의 배경을 그려줘라는 뜻
                if (buffg != null) {
                    buffg.drawImage(ozland, 0, 0,f_width,f_height, this); 
                }
            }
            public void drawCircle(){//뭐 임의로 선택지 이미지를 띄운다는 뜻. 차후 수정 혹은 제거 예정
                if (buffg != null) {
                    buffg.drawImage(rc, f_width/2,f_height/2-30,150,150, this);
                    rc_s = new Rectangle2D.Double(f_width/2,f_height/2-30,150,150);
                    buffg.drawImage(bc, f_width/2+200,f_height/2-30,150,150, this); 
                    bc_s = new Rectangle2D.Double(f_width/2+200,f_height/2-30,150,150);
                }
            }
        
            public void update(Graphics g){//repaint()혹은 최초 로딩시 해당 순서로 draw를 호출하고 업데이트함.
                drawOzland();
                drawCharacter();
                drawOzo();
                drawCircle();
                if (buffImage != null) {
                    g.drawImage(buffImage, 0, 0, this); 
                }
            }
        };
        
        panelForGraphics.setLayout(null);
        panelForGraphics.add(jl_left);
        panelForGraphics.add(jl_right);
        add(panelForGraphics);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        // getContentPane().setBackground(new Color(62,197,211));
        setBackground(new Color(62,197,241));//배경을 하늘색으로 지정...인데 덮어버려서 현재 시점에선 무쓸모

        
        setVisible(true);
    }
    
    //프레임의 그래픽 처리 메소드...였던것. 현재는 전부 j패널에 넘겨줌
    // public void paint(Graphics g){
    //     // Initialize the buffer image
    //     buffImage = createImage(f_width, f_height);
    //     buffg = buffImage.getGraphics();
    //     update(g);
    // }

    public static void main(String[] args) {
        MovieChoiceFrame mc = new MovieChoiceFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //왼쪽 오른쪽 키를 누르면 캐릭터 이미지가 넘어가도록 설계한건데 차후 키보드를 통해 왼쪽 오른쪽 선택지를 고르게 할까말까... 암튼 나중엔 쓸모없음
        if(!isKeyPressed){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                repaint();
                nthChacracter = (nthChacracter+1)%6;
                isKeyPressed = true;
            }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                repaint();
                nthChacracter = (nthChacracter+5)%6;
                isKeyPressed = true;

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()>0){//키보드 누를때 꾹 눌리면서 눌리고 있는동안 반복해서 실행되는 현상을 방지
            isKeyPressed = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(rc_s.contains(e.getPoint())){//만약 클릭된 포인트를 이전에 생성한 shape객체가 포함하고 있을 경우~ 뭐뭐 한다. 안의 코드는 차후 수정
            nthChacracter = (nthChacracter+5)%6;
            jl_left.setText(choices[nthChacracter*2]);
            jl_right.setText(choices[nthChacracter*2+1]);
            repaint();
        }
        if(bc_s.contains(e.getPoint())){//마찬가지. 애는 두번째 shape객체
            nthChacracter = (nthChacracter+1)%6;
            jl_left.setText(choices[nthChacracter*2]);
            jl_right.setText(choices[nthChacracter*2+1]);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        // System.out.println(mousex+" "+mousey);

    }

    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        // System.out.println("Mouse moved at coordinates: " + e.getX() + ", " + e.getY());
        mousex = e.getX();
        mousey = e.getY();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
}