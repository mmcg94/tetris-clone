
public class State {

		public final int MAINMENU = 1;
		public final int GAME = 2;
		private int state = GAME;
		
		public State() {
			setState(GAME);
		}
		
		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
		
		
}
