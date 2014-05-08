import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;


public class Menu {

		private String title;
		private int selected=0;
		private MenuOption[] options;
		
		public MenuOption[] getOptions() {
			return options;
		}

		public void setOptions(MenuOption[] options) {
			this.options = options;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		public void render(int x, int y, Window window) {
			BufferStrategy bs = window.getBufferStrategy();
			Graphics g = bs .getDrawGraphics();
			try {
				BufferedImage image = ImageIO.read(Game.class
						.getResource("/Menu.png"));
				g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			for (int i = 0; i < options.length; i++) {
				if (selected == i) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.ORANGE);
				}
				g.drawString(options[i].getName(), x, y + i * 44);
			}
			
			window.display();
		}

		public int getSelected() {
			return selected;
		}

		public void setSelected(int selected) {
			this.selected = selected;
		}

		public void update() {
			if (Keyboard.keyTyped(KeyEvent.VK_UP) && selected > 0) {
				selected--;
			} else if (Keyboard.keyTyped(KeyEvent.VK_DOWN) && selected <  options.length-1) {
				selected++;
			}
			if (Keyboard.keyTyped(KeyEvent.VK_ENTER) || Keyboard.keyTyped(KeyEvent.VK_SPACE)) {
				options[selected].action();
			}
			
		}
		
		
}
