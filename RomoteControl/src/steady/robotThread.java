package steady;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

public class robotThread implements Runnable{
private ObjectInputStream OIS;
	public robotThread( ObjectInputStream OIS){
		this.OIS =OIS;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			action();
		} catch (ClassNotFoundException | AWTException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("robot fail...");
		}
	}
	
	
	public  void action() throws AWTException, ClassNotFoundException, IOException{
		Robot robot= new Robot();
		while(true){
		
		InputEvent e =(InputEvent)OIS.readObject();
				if(e!=null){
		handleEvents(robot,e);}
		}
		
	}
	
	public static void handleEvents(Robot action,InputEvent event){
		MouseEvent mevent = null ; //鼠标事件
        MouseWheelEvent mwevent = null ;//鼠标滚动事件
        KeyEvent kevent = null ; //键盘事件
        int mousebuttonmask = -100; //鼠标按键
        
        switch (event.getID()){
        case MouseEvent.MOUSE_MOVED :                       //鼠标移动
            mevent = ( MouseEvent )event ;
            action.mouseMove( mevent.getX() , mevent.getY() );
            break ;
        case MouseEvent.MOUSE_PRESSED :                      //鼠标键按下
            mevent = ( MouseEvent ) event;
            action.mouseMove( mevent.getX() , mevent.getY() );
            mousebuttonmask = getMouseClick(mevent.getButton() );
            if(mousebuttonmask != -100)
                action.mousePress(mousebuttonmask);
            break;
         case MouseEvent.MOUSE_RELEASED :              //鼠标键松开
            mevent = ( MouseEvent ) event;
            action.mouseMove( mevent.getX() , mevent.getY() );
            mousebuttonmask = getMouseClick( mevent.getButton() );//取得鼠标按键
            if(mousebuttonmask != -100)
                action.mouseRelease( mousebuttonmask );
            break ;
        case MouseEvent.MOUSE_WHEEL :                  //鼠标滚动
            mwevent = ( MouseWheelEvent ) event ;
            action.mouseWheel(mwevent.getWheelRotation());
            break ;
         case MouseEvent.MOUSE_DRAGGED :                      //鼠标拖拽
            mevent = ( MouseEvent ) event ;
            action.mouseMove( mevent.getX(), mevent.getY() );
            break ;
         case KeyEvent.KEY_PRESSED :                     //按键
            kevent = ( KeyEvent ) event;
            action.keyPress( kevent.getKeyCode() );
            break ;
         case KeyEvent.KEY_RELEASED :                    //松键
            kevent= ( KeyEvent ) event ;
            action.keyRelease( kevent.getKeyCode() );
            break ;
        default: break ;
        
        
        
        }
        
        
	}
	  
    private static int getMouseClick(int button) {    //取得鼠标按键
        if (button == MouseEvent.BUTTON1) //左键 ,中间键为BUTTON2
            return InputEvent.BUTTON1_MASK;
        if (button == MouseEvent.BUTTON3) //右键
            return InputEvent.BUTTON3_MASK;
        return -100;
    }	

}
