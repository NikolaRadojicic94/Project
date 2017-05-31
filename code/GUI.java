package Prijemni;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import java.awt.Canvas;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.TextField;
import javax.swing.JRadioButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.text.DefaultCaret;

public class GUI extends JFrame{
	
	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextArea textField_13;
	
	private JButton btnUnesi;
	private JLabel lblRbr ;
	private JButton btnProcitajPodatke ;
	private JTextArea textField_14;
	
	private JRadioButton rdbtnSortirajPoRednom;
	private JRadioButton rdbtnSortirajPoBrojuBodova; 
	private JButton btnPrekini;
	
	public GUI(){
		
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("Prijemni");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#ffffb3"));
		
		lblRbr = new JLabel("Rbr");
		lblRbr.setBounds(10, 11, 111, 14);
		frame.getContentPane().add(lblRbr);
		
		JLabel lblIme = new JLabel("Ime");
		lblIme.setBounds(10, 36, 111, 14);
		frame.getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime");
		lblPrezime.setBounds(10, 61, 111, 14);
		frame.getContentPane().add(lblPrezime);
		
		JLabel lblJmbg = new JLabel("Jmbg");
		lblJmbg.setBounds(10, 86, 111, 18);
		frame.getContentPane().add(lblJmbg);
		
		JLabel lblIdSmer = new JLabel("Id smer 1");
		lblIdSmer.setBounds(10, 111, 94, 14);
		frame.getContentPane().add(lblIdSmer);
		
		textField = new JTextField();
		textField.setBounds(141, 8, 162, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 33, 162, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 58, 162, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		
		textField_3 = new JTextField();
		textField_3.setBounds(141, 83, 162, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		textField_3.setEditable(false);
		
		textField_4 = new JTextField();
		textField_4.setBounds(141, 108, 162, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		textField_4.setEditable(false);
		
		JLabel lblIdSmer_1 = new JLabel("Id smer 2");
		lblIdSmer_1.setBounds(10, 136, 106, 14);
		frame.getContentPane().add(lblIdSmer_1);
		
		
		JLabel lblIdSmer_2 = new JLabel("Id smer 3");
		lblIdSmer_2.setBounds(10, 161, 106, 14);
		frame.getContentPane().add(lblIdSmer_2);
		
		JLabel lblProsekIGod = new JLabel("Prosek I god");
		lblProsekIGod.setBounds(10, 186, 106, 18);
		frame.getContentPane().add(lblProsekIGod);
		
		textField_5 = new JTextField();
		textField_5.setBounds(141, 133, 162, 20);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		textField_5.setEditable(false);
		
		textField_6 = new JTextField();
		textField_6.setBounds(141, 158, 162, 20);
		frame.getContentPane().add(textField_6);
		textField_6.setColumns(10);
		textField_6.setEditable(false);
		
		textField_7 = new JTextField();
		textField_7.setBounds(141, 183, 162, 20);
		frame.getContentPane().add(textField_7);
		textField_7.setColumns(10);
		textField_7.setEditable(false);
		
		JLabel lblProsekIiGod = new JLabel("Prosek II god");
		lblProsekIiGod.setBounds(10, 211, 106, 18);
		frame.getContentPane().add(lblProsekIiGod);
		
		JLabel lblProsekIiiGod = new JLabel("Prosek III god");
		lblProsekIiiGod.setBounds(10, 236, 106, 18);
		frame.getContentPane().add(lblProsekIiiGod);
		
		JLabel lblProsekIvGod = new JLabel("Prosek IV god");
		lblProsekIvGod.setBounds(10, 261, 106, 18);
		frame.getContentPane().add(lblProsekIvGod);
		
		JLabel lblPrijemni = new JLabel("Prijemni");
		lblPrijemni.setBounds(10, 286, 94, 14);
		frame.getContentPane().add(lblPrijemni);
		
		JLabel lblUkupno = new JLabel("Ukupno");
		lblUkupno.setBounds(10, 311, 71, 14);
		frame.getContentPane().add(lblUkupno);
	
		textField_8 = new JTextField();
		textField_8.setBounds(141, 208, 162, 20);
		frame.getContentPane().add(textField_8);
		textField_8.setColumns(10);
		textField_8.setEditable(false);
		
		textField_9 = new JTextField();
		textField_9.setBounds(141, 233, 162, 20);
		frame.getContentPane().add(textField_9);
		textField_9.setColumns(10);
		textField_9.setEditable(false);
		
		textField_10 = new JTextField();
		textField_10.setBounds(141, 258, 162, 20);
		frame.getContentPane().add(textField_10);
		textField_10.setColumns(10);
		textField_10.setEditable(false);
		
		textField_11 = new JTextField();
		textField_11.setBounds(141, 283, 162, 20);
		frame.getContentPane().add(textField_11);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setBounds(141, 308, 162, 20);
		frame.getContentPane().add(textField_12);
		textField_12.setColumns(10);
		textField_12.setEditable(false);
		
	    btnUnesi = new JButton("Unesi");
		btnUnesi.setBounds(124, 339, 89, 23);
		frame.getContentPane().add(btnUnesi);
		
		textField_13 = new JTextArea();
		textField_13.setBounds(10, 365, 315, 136);
		frame.getContentPane().add(textField_13);
		textField_13.setFont(new Font("SansSerif",Font.BOLD,15));
		textField_13.setForeground(Color.RED);
		textField_13.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textField_13.setEditable(false);
		
		rdbtnSortirajPoRednom = new JRadioButton("Sortiraj po rednom broju");
		rdbtnSortirajPoRednom.setBounds(381, 57, 225, 23);
		frame.getContentPane().add(rdbtnSortirajPoRednom);
		
		rdbtnSortirajPoBrojuBodova = new JRadioButton("Sortiraj po broju poena");
		rdbtnSortirajPoBrojuBodova.setBounds(381, 82, 225, 23);
		frame.getContentPane().add(rdbtnSortirajPoBrojuBodova);
		
		btnProcitajPodatke = new JButton("Procitaj Podatke");
		btnProcitajPodatke.setBounds(394, 132, 180, 23);
		frame.getContentPane().add(btnProcitajPodatke);
		
		textField_14 = new JTextArea();
		textField_14.setBounds(381, 186, 400, 317);
		//
		JScrollPane sp = new JScrollPane(textField_14);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setBounds(381, 186, 400, 317);
		//
		frame.getContentPane().add(sp);
		textField_14.setFont(new Font("SansSerif",Font.BOLD,20));
		textField_14.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textField_14.setEditable(false);
		
		
		
		btnPrekini = new JButton("Prekini");
		btnPrekini.setBounds(316, 528, 89, 23);
		frame.getContentPane().add(btnPrekini);
		
		JLabel lblIspisPodatakaO = new JLabel("Ispis podataka o kandidatima");
		lblIspisPodatakaO.setBounds(381, 11, 255, 14);
		frame.getContentPane().add(lblIspisPodatakaO);
	
		frame.setVisible(true);
		
	}

	public JButton btnUnesi(){
		return btnUnesi;
	}
	
	public JButton btnProcitaj(){
		
		return btnProcitajPodatke; 
	}

	
	public JTextField textField(int i){
		
		switch(i){
		 case 0 : return textField;
		 case 1: return textField_1; 
		 case 2: return textField_2;
		 case 3: return textField_3;
		 case 4: return textField_4;
		 case 5: return textField_5;
		 case 6: return textField_6;
		 case 7: return textField_7;
		 case 8: return textField_8;
		 case 9: return textField_9;
		 case 10: return textField_10;
		 case 11: return textField_11;
		 case 12: return textField_12;
		 
		 default: return textField_1;
		}
 
	}
	
	public JTextArea AreaUnos(){
		
		return textField_13;
	}
	
	public JTextArea AreaProcitaj(){
		
		return textField_14;
	}
	
	public JRadioButton radioRbr(){
		
		return rdbtnSortirajPoRednom;
		
	}
	
	public JRadioButton radioUkupno(){
		
		return rdbtnSortirajPoBrojuBodova;
		
	}
	
	public JButton btnPrekini(){
		
		return btnPrekini;
		
	}

}




