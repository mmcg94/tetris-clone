
public class MenuOption {

	private String name;
	private Action action;

	public MenuOption(String name, Action action) {
		this.name = name;
		this.action = action;
	}

	public void action() {
		if (action != null) action.action();
	}

	public String getName() {
		return name;
	}

}
