package steady;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;





public class ServerwithListener {
	private static Socket socket;
	private static ObjectInputStream OIS ;
	private static ObjectOutputStream OOS;
	private static  JLabel imag_lab	;
	private static ImageIcon IIC ;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		try{
		OpenServer();
		showUI();
		
		while(true){
		
			reveivePic();
		}
		}
		catch(Exception ee){
			OIS.close();
			socket.close();
		}
		
	}
	public static void OpenServer() throws IOException, ClassNotFoundException{
		System.out.println("ServerStart.....");
		ServerSocket server = new ServerSocket(7777);
		socket = server.accept();
		System.out.println("连接上...\n"+socket);
		OIS = new ObjectInputStream(socket.getInputStream());
		OOS=new ObjectOutputStream(socket.getOutputStream());
		}
	
	public static void reveivePic() throws ClassNotFoundException, IOException{
		Message g = (Message)OIS.readObject();
		FileOutputStream FOS = new FileOutputStream("D:\\OUT\\"+g.getFileName());
		FOS.write(g.getFileContent(),0,(int)g.getFileLength());
		FOS.flush();
		
		FileInputStream FIS= new FileInputStream("D:\\OUT\\"+g.getFileName());
		BufferedImage BI = ImageIO.read(FIS);
		IIC=new ImageIcon(BI);
		
        Image img = IIC.getImage();
        Toolkit tk = Toolkit.getDefaultToolkit() ;
            Dimension d =tk.getScreenSize();
         
            int w = d.width;
            int h =d.height;
               BufferedImage bi = resize(img,800,600);
              

               imag_lab.setIcon(new ImageIcon(bi));
               imag_lab.repaint();//销掉以前画的背景
		}
	
	
	
	public static void showUI(){
		//控制台标题
	       JFrame jf = new JFrame("控制台");setListener(jf);
	       //控制台大小
	       jf.setSize(500, 400);
	       //imag_lab用于存放画面
	       imag_lab = new JLabel();
	       jf.add(imag_lab);
	       //设置控制台可见
	       jf.setVisible(true);
	       //控制台置顶
	       jf.setAlwaysOnTop(true);
	       jf.setResizable(true);
	       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
	       
	}
	
	
	 private static BufferedImage resize(Image img, int newW, int newH) {
         int w = img.getWidth(null);
         int h = img.getHeight(null);
         BufferedImage dimg = new BufferedImage(newW, newH,BufferedImage.TYPE_INT_BGR);
         Graphics2D g = dimg.createGraphics();
         g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                       RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
         g.dispose();
         return dimg;
  }
	 
	  public static void setListener( JFrame frame){
		   //panel设置监听器
	         frame.addKeyListener(new KeyAdapter(){
	               public void keyPressed(KeyEvent e) {
	                       sendEventObject(e);
	               }

	               @Override
	               public void keyReleased(KeyEvent e) {
	                      sendEventObject(e);
	               }

	               @Override
	               public void keyTyped(KeyEvent e) {
	                
	               }
	 });
	         frame.addMouseWheelListener(new MouseWheelListener(){
	               public void mouseWheelMoved(MouseWheelEvent e) {
	                     sendEventObject(e);       
	               }
	        });
	         frame.addMouseMotionListener(new MouseMotionListener(){

	               public void mouseDragged(MouseEvent e) {
	                                                 
	                      sendEventObject(e);
	               }

	               public void mouseMoved(MouseEvent e) {
	                     
	                     
	                      sendEventObject(e);
	                     
	               }
	        });
	         frame.addMouseListener(new MouseListener(){
	         public void mouseClicked(MouseEvent e) {
	                         sendEventObject(e);
	                     
	               }
	         public void mouseEntered(MouseEvent e) {
	                                          
	                      sendEventObject(e);
	               }
	         public void mouseExited(MouseEvent e) {
	                                                 
	                      sendEventObject(e);
	               }
	         public void mousePressed(MouseEvent e) {
	              
	                      sendEventObject(e);
	               }
	    public void mouseReleased(MouseEvent e) {
	              
	                      sendEventObject(e);
	               }
	              
	        });
	  }
	  
	  private static void sendEventObject(InputEvent event){
	         try{  System.out.println("send");
	           OOS.writeObject(event);
	           OOS.flush();
	           
	         }catch(Exception ef){
	                  ef.printStackTrace();
	           }
		
	         
	         
	          
	    }
	 
	 
	 
	 
	 
}
