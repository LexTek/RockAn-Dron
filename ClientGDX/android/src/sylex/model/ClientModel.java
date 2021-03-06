package sylex.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.upmc.lexteksylux.radclient.interfaces.ServerGameEndEventReceiver;
import sylex.androidClient.interfaces.ConnectEventReceiver;
import sylex.androidClient.interfaces.RoomEventReceiver;
import sylex.controller.ServerHandler;

public class ClientModel {
	
	public static boolean inGame = false;
//	public static ArrayList<String> games = new ArrayList<String>();
	public static ArrayList<String> clientsInGame = new ArrayList<String>();
//	public static String gameName = new String("");
	public static int idClient = -1;
	private static int idLevel = -1;
	private static ServerHandler handler = null;

	//events receiver
	private static ConnectEventReceiver ceo;
	//private static Object celock = new Object();
	private static RoomEventReceiver reo;
	private static Object relock = new Object();
	private static ServerGameEndEventReceiver sgeo;
	public static int nbSec;


	public static void setHandler(ServerHandler handler) {
		ClientModel.handler = handler;
	}

	//BIND ACTIVITY EVENTS
	public static void bindConnectEvent(ConnectEventReceiver ceo) {
		ClientModel.ceo = ceo;
	}


	public static void bindServerGameEndEvent(ServerGameEndEventReceiver sgeo) {
		ClientModel.sgeo = sgeo;
	}

	public static void bindRoomEvent(RoomEventReceiver reo) {
		ClientModel.reo = reo;
		Log.d("D", "\n\ndone\n\n");
	}

	//ACTIONS QUI IMPLIQUENT UN ENVOI DE PAQUET :

	public static void Connect(String username) {
		handler.CsendConnect(username);
	}

	public static void StartGameOK() {
		handler.CsendStartGameOK();
	}

	//ACTIONS APPLIQUEES A LA RECEPTION DES PAQUETS :

	public static void registerConnection(int idClient) {
		ClientModel.idClient = idClient;
		if(ceo != null)
			ceo.onConnectOK();
		else
			Log.d("D", "\n\n CEO == null !!!!!!!!!!!!!!");
	}

	public static void updateGameUserList(List<String> gameList) {
		ClientModel.clientsInGame.removeAll(ClientModel.clientsInGame);
		ClientModel.clientsInGame.addAll(gameList);
		Log.d("D", "\n\n Received Game User List : " + clientsInGame.toString() + "\n\n");
		if(reo != null) {//ce message peut être reçu avant la création de son receveur
			Log.d("D", "\n\n reo != null");
			reo.onGameUserListChanged();
		}
	}

	public static void init() {
		handler.init();
	}

	public static void notifScoreTick(byte score) {
		handler.CsendScoreTick(score);
	}


	public static void showBadConnect() {
		//mettre a jour l'affichage
		ceo.onConnectBAD();
	}


	public static void gameEnd(String winnerName) {
		sgeo.onGameEnd(winnerName);
	}

	public static void updateGameUserList(String game, List<String> userList) {
//		ClientModel.gameName = game;
		ClientModel.clientsInGame = new ArrayList<String>(userList);
	}


	public static void showBadJoin() {
		//mettre a jour l'affichage
	}


	public static void registerJoin(int idLevel) {
		ClientModel.idLevel = idLevel;
	}


	public static void showBadNew() {
		//mettre a jour l'affichage
	}


	public static void registerNew() {
		//Peut être rien ici
	}

	public static void sendNew(String gameName, byte playerCount, int level) {
		handler.CsendNewGame(gameName, playerCount, level, 15000);
	}


	public static void startGame(int nbSec) {
		ClientModel.nbSec = nbSec;
		reo.onStartGame(nbSec);
	}

	public static void disconnect() {
		handler.CsendDisconnect();
	}
}
