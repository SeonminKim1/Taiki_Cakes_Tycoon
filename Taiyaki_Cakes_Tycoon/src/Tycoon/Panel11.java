package Tycoon;
import javax.swing.*;
import java.awt.*;

public class Panel11 extends JPanel{
	public static JLabel LeftLabel11 = new JLabel();
	public static JLabel RightLabel11 = new JLabel();
	public static JPanel Panel11_1 = new JPanel(new CardLayout());   //이미지 들어가는 패널을 CardLayout으로 바꿈
	
	//붕어빵객체생성
	public Panel11(){
		JPanel Panel11_2 = new JPanel();
		setLayout(new BorderLayout());
		Panel11_2.setBackground(Color.DARK_GRAY); //패널2 꾸미기 시작
		LeftLabel11.setOpaque(true);
		RightLabel11.setOpaque(true);
		LeftLabel11.setBackground(Color.DARK_GRAY);
		LeftLabel11.setForeground(Color.WHITE);
		LeftLabel11.setFont(new Font("돋움",Font.BOLD,15));
		RightLabel11.setBackground(Color.DARK_GRAY);
		RightLabel11.setForeground(Color.YELLOW);
		RightLabel11.setFont(new Font("돋움",Font.BOLD,13));

		Panel11_2.add(LeftLabel11); Panel11_2.add(RightLabel11); 
		
		add(Panel11_1, BorderLayout.CENTER);
		add(Panel11_2, BorderLayout.SOUTH);
		
		Th11 thth11 = new Th11();
		Thread th11 = new Thread(thth11);
		th11.start();
		
	}
}

class Th11 extends JPanel implements Runnable{
	private ImageIcon empty= new ImageIcon(Main.class.getResource("../images/FISHNULL.png"));	//빈 상태
	private ImageIcon step1= new ImageIcon(Main.class.getResource("../images/FISH00.png"));		//재료
	private ImageIcon step2= new ImageIcon(Main.class.getResource("../images/FISH11.png"));		//익기시작
	private ImageIcon step3= new ImageIcon(Main.class.getResource("../images/FISH22.png"));		//갈변
	private ImageIcon step4= new ImageIcon(Main.class.getResource("../images/FISH33.png"));		//연기 (점수추가)
	private ImageIcon burn= new ImageIcon(Main.class.getResource("../images/FISHBURN.png"));	//태움
	private JLabel label1=new JLabel(empty);		//빈 상태
	private JLabel label2=new JLabel(step1);		//재료
	private JLabel label3=new JLabel(step2);		//익기시작
	private JLabel label4=new JLabel(step3);		//갈변
	private JLabel label5=new JLabel(step4);		//연기 (점수 추가)
	private JLabel label6=new JLabel(burn);		//태움
	private int stage=0;  // 상태 변수
	
	public void run() {
		while(true) {
			int escape = 1; // 탈출 변수.
			while(escape !=0) {
				if(MainPanel.kcheck()==true)return; // 총 타이머가 다됬는지
				String word = MainPanel.wordlist1[(int)(Math.random()*MainPanel.wordlist1.length)];
				Panel11.LeftLabel11.setText(word);
				Panel11.LeftLabel11.setForeground(Color.WHITE);
				Panel11.Panel11_1.add(label1);
				try {
					Thread.sleep(Timer.timer); // 한 단어 총 출력 시간
				} catch (InterruptedException e) {
					e.printStackTrace(); 
				}
				try {
				if(SouthPanel.allfree11.equals(word)) { //맞는 단어를 치면
					escape=0; this.stage++;
					
				}
				else if(SouthPanel.allfree11.equals(null) || !SouthPanel.allfree11.equals(word)){ //아무것도 안 쳤거나, 틀린 단어를 치면
					if(this.stage==0) {
					escape=1; this.stage = 0; // 빈 상태에서 계속 비게 함 (while문을 빠져나가지 않고 무한루프)
					} 
					else {
						escape=0; this.stage = 7; // 재료가 들어있으면 타게 함
					}
				}
				}catch (NullPointerException e) {}
			}
			switch (this.stage) {
			case 0 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label1); break; // ? -> 빈 상태
			case 1 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label2); break; // 빈 상태 -> 재료
			case 2 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label3); break; // 재료 -> 익기시작
			case 3 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label4); break; // 익기시작 -> 갈변
			case 4 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label5); break; // 갈변 -> 연기 (완성)
			case 5 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label1); this.stage=0; EastPanel.SumScore(); break; //연기 (완성) -> 빈 상태 1점 추가
			case 7 : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label6); this.stage=-1; break; // 타버린 상태
			default : Panel11.Panel11_1.removeAll(); Panel11.Panel11_1.add(label1); this.stage=0; break;
			}
		}
	}
}