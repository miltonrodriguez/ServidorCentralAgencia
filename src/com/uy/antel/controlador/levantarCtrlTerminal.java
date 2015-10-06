package com.uy.antel.controlador;

public class levantarCtrlTerminal implements Runnable {
	@Override
	public void run() {
		ctrlTerminal ter = ctrlTerminal.getInstance();
		ter.listenSocket();
	}
}
