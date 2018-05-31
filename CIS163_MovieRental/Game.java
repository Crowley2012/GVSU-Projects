import java.util.GregorianCalendar;

public class Game extends DVD {

	private static final long serialVersionUID = 1L;

	private PlayerType player;

	public Game() {
		super();
	}

	public PlayerType getPlayer() {
		return player;
	}

	public void setPlayer(PlayerType p) {
		player = p;
	}

	public String toString() {
		return "Name:  " + nameOfRenter + ", Title:  " + title + ", Rented On:  "
			+ bought.get(GregorianCalendar.MONTH) + "/"
			+ bought.get(GregorianCalendar.DAY_OF_MONTH) + "/" + bought.get(GregorianCalendar.YEAR)
			+ ", Due Back On:  " + dueBack.get(GregorianCalendar.MONTH) + "/"
			+ dueBack.get(GregorianCalendar.DAY_OF_MONTH) + "/"
			+ dueBack.get(GregorianCalendar.YEAR) + ", Game Player:  " + playerName();
	}

	private String playerName() {
		String type;

		if (player == PlayerType.PS4) {
			type = "PlayStation 4";
		} else if (player == PlayerType.XBOX360) {
			type = "Xbox 360";
		} else {
			type = "Xbox 720";
		}

		return type;
	}
}
