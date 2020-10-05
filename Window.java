import java.awt.EventQueue;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;

public class Window {
	
	int MaxBase, basePower, MapSize, miniPower;
	int flag = 0;
	private JFrame frame;
	private JPanel panel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPanel panel_1;
	private ArrayList<GMap> ans = new ArrayList<GMap>();

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 529, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 513, 401);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("BasePower:");
		lblNewLabel_2.setBounds(319, 16, 65, 15);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(407, 53, 96, 21);
		textField.setColumns(8);
		panel.add(textField);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(173, 16, 0, 0);
		panel.add(label_2);
		
		JLabel lblNewLabel = new JLabel("MaxBase:");
		lblNewLabel.setBounds(316, 56, 68, 15);
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(407, 13, 96, 21);
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		JLabel label = new JLabel("");
		label.setBounds(312, 16, 0, 0);
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(317, 16, 0, 0);
		panel.add(label_1);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(322, 16, 0, 0);
		panel.add(label_3);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(438, 200, 65, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MaxBase = Integer.valueOf(textField.getText());
				basePower = Integer.valueOf(textField_1.getText());
				MapSize = Integer.valueOf(textField_2.getText());
				miniPower = Integer.valueOf(textField_3.getText());
				flag = 1;
			}
		});
		panel.add(btnNewButton);
		
		textField_2 = new JTextField();
		textField_2.setBounds(407, 103, 96, 21);
		textField_2.setColumns(10);
		panel.add(textField_2);
		
		JLabel lblNewLabel_1 = new JLabel("MapSize:");
		lblNewLabel_1.setBounds(308, 106, 76, 15);
		panel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(407, 145, 96, 21);
		panel.add(textField_3);
		
		JLabel lblNewLabel_3 = new JLabel("MiniPower");
		lblNewLabel_3.setBounds(308, 148, 68, 15);
		panel.add(lblNewLabel_3);
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 36, 280, 280);
	}
	
	public int MaxBase() {
		return MaxBase;
	}
	
	public int basePower() {
		return basePower;
	}
	
	public int MapSize() {
		return MapSize;
	}
	
	public int miniPower() {
		return miniPower;
	}
	
	public int flag() {
		return flag;
	}

	public void setanswer(ArrayList<GMap> answer){
		this.ans = answer;
	}

	public void drawMap(){
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(MapSize, MapSize, 0, 0));

		int answerSize = ans.size();
		boolean exist = true;
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMM"+MapSize);
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMM"+basePower);
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMM"+MaxBase);
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMM"+miniPower);
		if(answerSize != 0){
		GMap temp1 = ans.get(0);
		ArrayList<baseStand> bas = temp1.getBase();
		int baseSize = temp1.getBase().size();
		for(int i=0, BaseCount = 0; i < MapSize; i++){
			for(int j=0; j < MapSize; j++){
				JButton temp = new JButton();
				System.out.println("add button x:" + i +" y:" + j);
				if(BaseCount < baseSize) {
					for(int c = 0; c< baseSize; c++){
						if(i== (int)(bas.get(c).getPlace().getX()) && j== (int)(bas.get(c).getPlace().getY())) {
							temp.setBackground(Color.red);
							System.out.println("*************color*************");
							BaseCount++;
						}
					}
				}
				panel_1.add(temp);
			}
		}
		}
		else if(answerSize == 0){
			for(int i = 0; i<MapSize; i++)
				for(int j = 0; j<MapSize; j++){
					JButton temp = new JButton();
					panel_1.add(temp);
				}
		}
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

}