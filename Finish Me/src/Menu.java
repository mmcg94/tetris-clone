
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
		
		public void render() {
			
		}

		public int getSelected() {
			return selected;
		}

		public void setSelected(int selected) {
			this.selected = selected;
		}
		
		
}
