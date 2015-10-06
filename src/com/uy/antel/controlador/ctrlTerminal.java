package com.uy.antel.controlador;

import java.net.*;

import com.uy.antel.util.util;

import java.io.*;

public class ctrlTerminal {

	private static ctrlTerminal instance = null;

	public static ctrlTerminal getInstance() {
		if (instance == null) {
			instance = new ctrlTerminal();
		}
		return instance;
	}

	ServerSocket server;

	public void listenSocket() {
		try {
			server = new ServerSocket(util.getPuertoTerminal());
		} catch (IOException e) {
			System.out.println("Could not listen on port "+util.getPuertoTerminal());
			System.exit(-1);
		}
		while (true) {
			atenderTerminal w;
			try {
				// server.accept returns a client connection
				System.out.println("Se abrio el puerto: "+util.getPuertoTerminal());
				w = new atenderTerminal(server.accept());
				Thread t = new Thread(w);
				t.start();
			} catch (IOException e) {
				System.out.println("Accept failed: "+util.getPuertoTerminal());
				System.exit(-1);
			}
		}
	}

}
