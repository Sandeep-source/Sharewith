import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class SWmenu extends JMenu{
public SWmenu(){
super();
setBackground(Color.RED);
}
public SWmenu(String name)
{
super(name);
setBackground(Color.RED);
}

protected void paintComponent(Graphics g)
{
super.paintComponent(g);
setSize(55,23);

}



}