package applikation.client.states;

import ui.lobby.LobbyView;
import dienste.statemachine.State;

public class LobbyStart extends PassiveClientState {

	@Override
	protected State getNextState() {
		return automat.getState(Lobby.class);
	}
	
	protected void init() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
	}

}
