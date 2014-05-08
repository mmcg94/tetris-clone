public class Main implements Runnable{

	private boolean isRunning;
	private Thread thread;
	
	private State state;
	private Game game;
	private Menu mainMenu;
	private Window window;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	public Main() {
		game = new Game();
		mainMenu = new Menu();
		state = new State();
		window = new Window();
		window.addKeyListener(new Keyboard());
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this, "Tetcopy Display");
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public void render() {
		if (state.getState() == state.MAINMENU) {
			if (mainMenu.getOptions() == null) {
				setUpMainMenu();
			}
			mainMenu.render(window.getWidth() / 2 - 30, window.getHeight() / 4, window);
		} else if (state.getState() == state.GAME) {
			game.render(window);
		}
	}

	private void setUpMainMenu() {
		MenuOption[] options = { new MenuOption("Play", new Action() {
			public void action() {
				state.setState(state.GAME);
			}
		}), new MenuOption("Options", null), new MenuOption("About", null), new MenuOption("Quit", new Action() {
			public void action() {
				System.exit(0);
			}
		}) };
		mainMenu.setOptions(options );
		
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(updates);
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.setTitle(frames, updates);
				updates = 0;
				frames = 0;
			}
		}
	}

	private void update(int updates) {
		if (state.getState() == state.MAINMENU) {
			mainMenu.update();
		} else if (state.getState() == state.GAME) {
			game.update(updates);
		}
		
	}
	
}