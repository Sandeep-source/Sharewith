import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
//import javax.swing.Border.*;
import javax.imageio.*;
import java.io.*;
public class Sharewith {
    JLabel info=new JLabel();
   
public Sharewith(){
     JFrame jfr=new JFrame("Share With");
     jfr.setSize(900,700);
    jfr.add(info);
    File logo=new File("logo.png");
   try{
       jfr.setIconImage(ImageIO.read(logo));
        }catch(IOException ex){
       info.setText("Icon can't load.");
            }
   jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   JMenuBar mb=new JMenuBar();
   UIManager.put("MenuBar.background",Color.WHITE);
   mb.setSize(250,300);
    UIManager.put("Menu.selectionBackground",Color.RED);
   UIManager.put("Menu.borderPainted",false);
   jfr.setLayout(new BorderLayout());
   //CardLayout crdl=new CardLayout();
   //JPanel card=new JPanel();
   //JPanel mpan=new JPanel();
   //mpan.setLayout(crdl);
   JPanel homepage=new JPanel();
   homepage.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));						
  //mpan.add(homepage,"home");
   JMenu Home=new JMenu("Home");
   Home.setSelected(true);
   JMenu Send=new JMenu("Send");
   JMenu ab=new JMenu("About Us");
   JMenu co=new JMenu("Contact Us");
   JMenu help=new JMenu("Help");
   mb.add(Home);
   mb.add(Send);
   mb.add(ab);
   mb.add(co);
   mb.add(help);
   JButton btn=new JButton(new ImageIcon("CONNECT (5).png"));
   btn.setBackground(Color.WHITE);
   btn.setPressedIcon(new ImageIcon("connect.png"));
   btn.setMargin(new Insets(0,0,0,0));
   btn.setBounds(10,40,50,20);
   btn.setPreferredSize(new Dimension(200,142));
   btn.setBorderPainted(false);
   btn.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent ae){
            new Thread(()->{
              JFileChooser ch=new JFileChooser();
              ch.setMultiSelectionEnabled(true);
              ch.setAcceptAllFileFilterUsed(true);
              ch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
              if(ch.showOpenDialog(jfr)==JFileChooser.APPROVE_OPTION)
                {
                  send(ch.getSelectedFiles()); 
                }}).start();
             }
        });
   JButton rec=new JButton(new ImageIcon("CONNECT (3).png"));
   rec.setBackground(Color.WHITE);
   rec.setMargin(new Insets(0,0,0,0));
   rec.setBorderPainted(false);
   rec.setPressedIcon(new ImageIcon("CONNECT (3).png"));

   rec.addActionListener(new ActionListener()
     {
          public void actionPerformed(ActionEvent ae){
             new Thread(()->{
        try(Socket socket=new Socket(InetAddress.getByName("127.0.0.1"),40)) 
           {
                 ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
               while(true)
                {
                   File rfl=(File)in.readObject();
                   File flin=new File(rfl.getName());
             if(!flin.exists())
                  {
                   flin.createNewFile();
                  }
                try(FileInputStream fin=new FileInputStream(rfl);
                            FileOutputStream fout=new FileOutputStream(flin);)
                 {
                 	int av=fin.available();
                  for(int i=0;i<av;i++)
                   {
                   fout.write(fin.read());
                   }
                  }catch(Exception e)
                       {  info.setText("Error in file reading.");  }
     
              }
          }catch(Exception e){ info.setText("Error in Socket.");  }
       }).start();
      }
    });
    JPanel footer=new JPanel();
    JLabel lb=new JLabel(new ImageIcon("share.png"));
    footer.add(lb);
    footer.add(info);
    footer.setBackground(Color.WHITE);
homepage.add(btn,BorderLayout.CENTER);
   homepage.setBackground(Color.WHITE);
  // crdl.show(homepage,"hm"); 
   homepage.add(rec,BorderLayout.CENTER); 
   jfr.add(homepage,BorderLayout.CENTER);
   jfr.add(footer,BorderLayout.SOUTH);
   jfr.setJMenuBar(mb);
   jfr.setVisible(true);
}
private void send(File[] file)
{
    try(ServerSocket sersok=new ServerSocket(40,5,InetAddress.getLocalHost())){
       Socket socket=sersok.accept();
       ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream()); 
       for(File fl : file){
         out.writeObject(fl);
       }
     }catch(IOException io){
       info.setText("Error Accured.");
       }
   }
public static void main(String[] args){
SwingUtilities.invokeLater(()->new Sharewith());
}


}
