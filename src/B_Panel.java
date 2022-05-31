import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

class B_Panel extends JPanel implements A_GraphicSystem {
	// constants
	private static final long serialVersionUID = 1L;
	private static final Font font = new Font("Arial", Font.PLAIN, 24);

	// InputSystem is an external instance
	private B_InputSystem inputSystem = new B_InputSystem();

	// GraphicsSystem variables
	//
	private GraphicsConfiguration graphicsConf = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice().getDefaultConfiguration();
	private BufferedImage imageBuffer;
	private Graphics graphics;

	public B_Panel() {
		this.setSize(A_Const.WIDTH, A_Const.HEIGHT);
		imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
		graphics = imageBuffer.getGraphics();

		// initialize Listeners
		this.addMouseListener(inputSystem);
		this.addMouseMotionListener(inputSystem);
		this.addKeyListener(inputSystem);
	}

	public void clear() {
		graphics.setColor(new Color(153,255,153));
		graphics.fillRect(0, 0, A_Const.WIDTH, A_Const.HEIGHT);
	}

	public final void draw(A_GameObject dot) {
		B_Shape shape = (B_Shape) dot.shape;

		int x = (int) (dot.x - shape.radius());
		int y = (int) (dot.y - shape.radius());
		int d = (int) (shape.radius() * 2);

		graphics.setColor(shape.color);
		graphics.fillOval(x, y, d, d);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawOval(x, y, d, d);
	}

	public final void draw(A_TextObject text) {
		B_Shape shape = (B_Shape) text.shape;

		graphics.setFont(font);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawString(text.toString(), (int) text.x + 1, (int) text.y + 1);
		graphics.setColor(shape.color);
		graphics.drawString(text.toString(), (int) text.x, (int) text.y);
	}

	public final void draw(A_Square sq) {
		if(sq.isStart()) {
			graphics.setColor(new Color(255,0,0));
			graphics.fillRect((int)sq.getsX(), (int)sq.getsY(), 40, 40);
		}
		if(sq.isEnd()) {
			graphics.setColor(new Color(0,0,255));
			graphics.fillRect((int)sq.getsX(), (int)sq.getsY(), 40, 40);
		}
		graphics.setColor(Color.BLACK);
		graphics.drawLine((int)sq.getsX(),(int)sq.getsY(),(int)sq.geteX(),(int)sq.getsY());
		graphics.drawLine((int)sq.getsX(),(int)sq.geteY(),(int)sq.geteX(),(int)sq.geteY());
		graphics.drawLine((int)sq.getsX(), (int)sq.getsY(), (int)sq.getsX(), (int)sq.geteY());
		graphics.drawLine((int)sq.geteX(), (int)sq.getsY(), (int)sq.geteX(), (int)sq.geteY());
	}
	public void redraw() {
		this.getGraphics().drawImage(imageBuffer, 0, 0, this);
	}

	public final A_InputSystem getInputSystem() {
		return inputSystem;
	}
}
