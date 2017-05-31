 
package Prijemni;
import java.awt.EventQueue;

import java.sql.*;
import sqlj.runtime.ref.*;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Canvas;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.TextField;
import javax.swing.JRadioButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class main {
		
public static void main(String[] args) {
	
	

try{
			
			String url = "jdbc:db2://localhost:50001/VSTUD";
			
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			final Connection con = DriverManager.getConnection(url,"student","abcdef");
			con.setAutoCommit(false);
			
			//final DefaultContext ctx = new DefaultContext(con);
			//DefaultContext.setDefaultContext(ctx);
			System.out.println("---Connected---");
			
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						final GUI window = new GUI();
						final Global postoji_kandidat = new Global();
						final Global prazan_skup = new Global();
						final Global baza = new Global();
					//	final Global lock = new Global();
						final String lock_mode = "lock table prijavljeni in exclusive mode "; // komanda kojom cemo zakljucati nas objekat
						final Statement stmLock = con.createStatement();	// sa ovom naredbom cemo izvrsiti zakljucavanje
						///citanje rbr
						
						//kada se unese podatak u polje rbr ova metoda ispod se pokrece
						window.textField(0).addKeyListener(new KeyAdapter(){
							
							public void keyReleased(KeyEvent keyEvent){
								
								try{
									
									window.AreaUnos().setText("");		//cistimo polje unos
									window.AreaUnos().setBackground(Color.WHITE);
									baza.bazaTrue();			//postavljano globalne promenljive na pocetne vrednosti
									prazan_skup.False();		// one ce nam kasnije biti nekakvi indikatori
									//lock.prosao_lockFalse();
									
									
									 String x="";			// 	String u kome cemo cuvati kasnije podatke iz polja redni_broj( text_Field(0) ) 
									 
									 int rbr_broj_je = 0;
									 if(CeoBroj(window.textField(0) ) == 1 ){		//ako je redni broj upisan ispravno
										 
										 window.textField(0).setBackground(Color.decode("#80ff80"));	// polje postane zeleno
										 rbr_broj_je = 1;
										x = window.textField(0).getText().trim();		//uzimamo podatke iz polja za redni broj
									 }	 
									 else{
										 window.textField(0).setBackground(Color.decode("#ffcccc"));      // inace polje postaje crvene boje
										
									 }
									 
									 Short rbr;				// ovde cemo cuvati redni broj koji smo uneli u polje
									
									 if(rbr_broj_je == 1)			// ako je ovaj indikator 1 to je znak da mozemo ispravno procitati podatak
										 rbr = Short.parseShort(x);	// konvertujemo procitani string u tip short
									 else
										 rbr = -1;					// u slucaju da redni broj nije ispravno unet , stavicemo rbr na -1
									 								// to ce nam kasnije biti znak da nije ispravno unet redni broj
									 
									 // pripremeamo naredbu za izvrsavanje , to je upit koji nam vraca podatke o kandidatu sa datim rednim brojem
									 // Ukljucicemo i HOLD_CURSOR_OVER_COMMIT da se ne bi zatvorio kursor posle commita
									final PreparedStatement ps = con.prepareStatement("select * " +
									 		"from prijavljeni where rbr = ?" , ResultSet.HOLD_CURSORS_OVER_COMMIT);
									 
									 ps.setShort(1, rbr);		// postavimo vrednost rednog broja na mesto prvog upitnika u ps
									
									 
									 try{
										
										
	
									
										// Ovde je koriscena nit koja se izvrsava paralelno sa nasim programom , ona ce imati zadatak da prati da li
										// smo uspeli da stavimo katanac na objekat , u slucaju da je proslo 3 sekunde a da to nismo uspeli da uradimo
										// znaci da neko drugi koristi objekat, i moramo da prijavimo da je baza zauzeta
										Thread t1 = kreirajNit(window,baza,stmLock,con);
										 
												
										t1.start();		// pre izvrsavanja naredbe koja zakljucava objekat pokrecemo nit da prati sta se desava
											
										stmLock.execute(lock_mode);		// pokusavamo da zakljucamo objekat nad kojim radimo
									 
									    t1.stop();						// ako je proslo zakljucavanje sticicemo do ove naredbe, i onda vise nema 
									    								// potrebe da nit prati desavanje , jer je uspesno zakljucan objekat
									    								// stopiramo nit 
									    ResultSet rs = ps.executeQuery();  // izvrsava se upit baze , i rezultat se smesta u ResultSet
									    
										try{
											
													// iteriramo kroz nas ResultSet i ispisujemo podatke o kandidatu
													while(rs.next()){
														
														 postoji_kandidat.IndTrue();	// cim smo unutar while znaci postoji kandidat
														 prazan_skup.True();			// i rezultat nije prazan skup
														 for(int i=1;i<=10;i++)			//za svako polje u formularu postavljamo podatke
															window.textField(i).setText(rs.getString(i+1));
														 
														 window.textField(12).setText(rs.getString(13));
														 
														 window.AreaUnos().setText("");
													 }
										con.commit();			//ako smo sve uradili kako treba vrsimo commit
										
										ps.close();
													}
													catch(SQLException e){
															// ako je doslo do problema konkurentnog izvrsavanja
														
														if(e.getErrorCode() == -913 || e.getErrorCode() == -911){
															
															zauzetaBaza(con,window);
															rs.close();
															ps.close();
															return;
															
														}		
													}
										
												
												
												
										
									 }
									
									 catch(SQLException e){
										e.printStackTrace();
									 }														
								
											
									if(prazan_skup.vrednost() == 0 && baza.bazaVrednost() == 1 ){	// ako nema takvog rednog broj i baza je slobodna
										for(int i=1;i<=10;i++)
											window.textField(i).setText(""); // postavi sva polja da budu prazna i prijavi da nema takvog rednog broja
										postoji_kandidat.IndFalse();
										window.AreaUnos().setText("Uneti redni broj ne postoji");
										window.textField(12).setText("");
										
										
										}
									
					
									int  prijemni_broj_je = 0;		// indikator koji ce nam govoriti da li su dobro uneti poeni za prijemni
									 
									 if(DecimalniBroj(window.textField(11)) == 1){
										 
										 window.textField(11).setBackground(Color.decode("#80ff80"));	// ako su dobro uneti poeni polje pozeleni
										 prijemni_broj_je = 1;
									 }	 
									 else{
										 window.textField(11).setBackground(Color.decode("#ffcccc"));	// inace je polje crvene boje	
									 }
									 
									 
									if(prijemni_broj_je != 0 && prazan_skup.vrednost() != 0 ){ // ukoliko su poeni ispravno uneti i kandidat postoji
									
										ispisiUkupnePoene(window);		// ispisi ukupne poene u polje ukupno
										
										}
									
									}
							
								catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							
						});
						
						/////// prijemni polje unos
						// kada se unese podatak u polje prijemni pokrene se metoda dole
						window.textField(11).addKeyListener(new KeyAdapter(){ 
							
							public void keyReleased(KeyEvent keyEvent){
							try{
								
								 if( IspravniPrijemniBodovi(window.textField(11)) == 1 ){	//ako su ispravno uneti podaci polje je zelene boje
									 
									 window.textField(11).setBackground(Color.decode("#80ff80"));
									
								 }	 
								 else{	// polje je inace crvene boje
									 window.textField(11).setBackground(Color.decode("#ffcccc"));
									
								 }
								
								 if( CeoBroj(window.textField(0)) == 1  && 	// ako suispravno uneti podaci u polja onda se ispisuju ukupni bodovi
										 IspravniPrijemniBodovi(window.textField(11)) == 1 
										 )
									ispisiUkupnePoene(window);
								 
								}
								catch (Exception e1) {
									e1.printStackTrace();
								}
							}
							
						});
						
						//////btn unesi
						// kada se klikne na polje unesi ova metoda se aktivira
					window.btnUnesi().addActionListener(new ActionListener(){
							
							public void actionPerformed(ActionEvent e){
								try{
									window.AreaUnos().setBackground(Color.WHITE);
									if( CeoBroj(window.textField(0)) == 1  && 		// ako su ispravno uneti svi podaci u polja 
											IspravniPrijemniBodovi(window.textField(11)) == 1 &&
											 DecimalniBroj(window.textField(12)) == 1 
											 ){
										//citaju se podaci iz polja i konvertuju se iz String tipa u odgovarajuci tip
										double pr=Double.parseDouble( window.textField(11).getText().toString().trim());
										double uk=Double.parseDouble( window.textField(12).getText().toString().trim());
										short rbr = Short.parseShort( window.textField(0).getText().toString().trim());
										PreparedStatement ps = con.prepareStatement("update prijavljeni " +
												"set prijemni = ?, ukupno = ? where rbr = ?", ResultSet.HOLD_CURSORS_OVER_COMMIT );
										try{
											
											Thread t1 = kreirajNit(window,baza,stmLock,con);
											t1.start();
											stmLock.execute(lock_mode);
											t1.stop();
											// pripremamo naredbu za update
											
											
											//popunimo na mesto upitnika odgovarajuce vrednosti
											ps.setDouble(1,pr);	
											ps.setDouble(2,uk);	
											ps.setShort(3,rbr);
											
											int i = 0;
											// ako postoji kandidat sa rednim brojem izvrsavamo upit
											if(postoji_kandidat.getInd() == 1 )
												i = ps.executeUpdate();
											// ukoliko je uspesan update vrsimo commit
											if(i == 1){
												con.commit();
												window.AreaUnos().setBackground(Color.decode("#80ff80"));
												window.AreaUnos().setText("Uspesno uneti podaci!");
												ps.close();
											}
											else
												window.AreaUnos().setText("Doslo je do greske pri unosu!");
										}
										catch(SQLException e1){
											
											//proveravamo da li ima problema sa pristupom objektu
											if(e1.getErrorCode() == -913 || e1.getErrorCode() == -911){
												
												zauzetaBaza(con,window);
											
												ps.close();
												return;
												
											}	
											e1.printStackTrace();
											
										 }
										
										
									}
									else{	
										window.AreaUnos().setText("Neispravno uneti podaci");
										
									}
								 
								}
								catch (SQLException e1) {
									e1.printStackTrace();
								}
								catch (Exception e2) {
									e2.printStackTrace();
								}
							}
							
						});	
							///////////////btn procitaj
							// kada se pritisne taster pritisni pokrece se ovaj kod ispod
					window.btnProcitaj().addActionListener(new ActionListener(){
						
						public void actionPerformed(ActionEvent e){
							ResultSet rs= null;
							
							Statement st = null;
							
							try{
								
								Thread t1 = kreirajNit(window,baza,stmLock,con);
								t1.start();
								stmLock.execute(lock_mode);
								t1.stop();
								
								window.AreaProcitaj().setText("");
								String sql = "select * from prijavljeni order by ";
								
								
								
								int selektovan = 0;
								if(window.radioRbr().isSelected()){
									 selektovan = 1;
									 sql = sql + "rbr";
								}
								else if(window.radioUkupno().isSelected()){
									 sql = sql + "ukupno desc";
									 selektovan = 1;
								}
								if(selektovan == 1){
									st = con.createStatement();
									rs = st.executeQuery(sql);
									int ima_kandidata = 0;
									
									while(rs.next()){
										ima_kandidata = 1;
										window.AreaProcitaj().append(rs.getShort(1) + " " +rs.getString(2).trim()+
												" " + rs.getString(3).trim() + " " + rs.getDouble(13) + "\n" );
											
									}
									if(ima_kandidata == 0){
										con.rollback();
										st.close();
										window.AreaProcitaj().setText("U bazi podataka nema kandidata!");
										
									}
									else{
										con.commit();
										st.close();
									}
									
								}
								else{
									window.AreaProcitaj().setBackground(Color.decode("#ffcccc"));
									window.AreaProcitaj().setText("Morate selektovati opciju!");
								}
									
								
							 
							}
							catch (SQLException e1) {
								
								if(e1.getErrorCode() == -913 || e1.getErrorCode() == -911){
									
									zauzetaBaza(con,window);
									try{
										st.close();
										rs.close();
									}
									catch(SQLException e2){
										e2.printStackTrace();
									}
									return;
									
								}	
								e1.printStackTrace();
								
							}
							catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						
					});
						
						///////////radio button////////////
						// ukoliko se kline jedan radio button drugi se blokira
					window.radioRbr().addActionListener(new ActionListener(){
						
						public void actionPerformed(ActionEvent e){
							try{
								window.AreaProcitaj().setText("");
								window.AreaProcitaj().setBackground(Color.WHITE);
								window.radioUkupno().setSelected(false);
								
							}
							
							catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						
					});
					//radio button
					// ukoliko se kline jedan radio button drugi se blokira
					window.radioUkupno().addActionListener(new ActionListener(){
						
						public void actionPerformed(ActionEvent e){
							try{
								window.AreaProcitaj().setText("");
								window.AreaProcitaj().setBackground(Color.WHITE);
								window.radioRbr().setSelected(false);
								
							}
							
							catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						
					});
					/////////////btnPrekini
					// ukoliko se klikne na prekini prekida se konekcija i izlazi se iz programa
					window.btnPrekini().addActionListener(new ActionListener(){
						
						public void actionPerformed(ActionEvent e){
							try{
								//jedna nit zatvara konekciju 
								Thread t1 = new Thread(){
								public void run(){
									try{
											
											//ctx.close();
											con.close();
											System.out.println("---Disconnected---");
											window.AreaProcitaj().setText("Diskonekcija uspesna!" +
													"\nProgram ce se uskoro zatvoriti...");
									}
									catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
								
								};
								//druga nit nakon 2 sekunde zatvara program
								Thread t2 = new Thread(){
									public void run(){
										
										
										long t1 = System.currentTimeMillis()/1000l;
										
										while(true){
											
											if( System.currentTimeMillis()/1000l - t1 >= 2) 
													System.exit(0);
										
										
											}
									}
								};
								
								t1.start();
								t2.start();
								
							
							}
							
							
							catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						
					});
					
				
					
						
						
					}
					
					catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			
		
			
			
			
		}
	
		catch(Exception e2){
			
			e2.printStackTrace();
			
		}

	

}
	

//metoda koja ispisuje u polja u formularu podatke
public static void ispisiUkupnePoene(final GUI window){
	
	String s = window.textField(11).getText().trim();
	Double prijemni_bod = Double.parseDouble(s);
	
	s = window.textField(7).getText().trim();
	Double prva_bod = Double.parseDouble(s);
	
	s = window.textField(8).getText().trim();
	Double druga_bod = Double.parseDouble(s);
	
	s = window.textField(9).getText().trim();
	Double treca_bod = Double.parseDouble(s);
	
	s = window.textField(10).getText().trim();
	Double cetvrta_bod = Double.parseDouble(s);
	
	Double ukupno = (prva_bod + druga_bod + treca_bod + cetvrta_bod ) * 2 + prijemni_bod; 
	
	BigDecimal b1 = new BigDecimal(ukupno);
	BigDecimal b2 = b1.setScale(2,RoundingMode.FLOOR);
	s = String.valueOf(b2);
	window.textField(12).setText(s);
	
	
}
//ispituje se da li sadrzaj polja  decimalan broj ili ceo broj
public static int DecimalniBroj(final JTextField field){
	
	if(Pattern.matches("[0-9]+[\\.]?[0-9]*",field.getText().toString().trim() ) )
		return 1;
	else
		return 0;
	
	
	} 
//ispituje se da li je sadrzaj polja ceo broj
public static int CeoBroj(final JTextField field){
	
	if(Pattern.matches("[0-9]+",field.getText().toString().trim() ) )
			return 1;
	else
		return 0;
	
	
	} 
//proverava se da li je ispravno unet broj bodova na prijemnom
public static int IspravniPrijemniBodovi(final JTextField field){
	
	if(Pattern.matches("[0-9]+",field.getText().toString().trim() ) ){
		double broj = Double.parseDouble(field.getText().toString().trim());
		if(broj >= 0 && broj <= 60)
			return 1;
	}
	
	return 0;
	
}


// ovom metodom se prekida izvrsavanje naredbe
public static void prekini(Statement s){
	try{
		s.cancel();
		
	}
	catch(Exception e){}

}

// kreira se nit koja nakon 3 sekunde ako se ne zakljuca tabela  treba da ispise da je baza zauzeta

public static Thread kreirajNit(final GUI window, final Global baza , final Statement stmLock,final Connection con){
	
	Thread t = new Thread(){
		public void run(){
			
				//f(ps);
			
			long t1 = System.currentTimeMillis()/1000l;	// trenutni broj sekundi 
			
			while(true){
				
				if( System.currentTimeMillis()/1000l - t1 >= 3){ //proveravamo da li proslo 3 sec
				
						baza.bazaFalse();		// postavljamo indikator da je baza zauzeta
						prekini(stmLock);		// prekidamo izvrsavanje naredbe koja pokusava da zakljuca
						try{
							con.rollback();		// radimo rollback 
						}
						catch(SQLException e){ e.printStackTrace();}
						
						window.textField(0).setText("");		
						window.AreaUnos().setText("Baza je zauzeta,sacekajte..."); // postavljamo sadrzaj polja Unos
						window.AreaUnos().setBackground(Color.decode("#ffcccc"));
						return;		//izlazimo iz funkcije koja osluskuje polje za redni broj
					
				}
			
			
				}
				
		}};
	
	
		return t;

	
}
// ukoliko je baza zauzeta radimo rollback i postavljamo sadrzaj polja
public static void zauzetaBaza(final Connection con, final GUI window){
	try{
	con.rollback();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	 // postavljamo sadrzaj polja Unos
	window.textField(0).setText("");		
	window.AreaUnos().setText("Baza je zauzeta,sacekajte...");
	window.AreaUnos().setBackground(Color.decode("#ffcccc"));
	
}

}