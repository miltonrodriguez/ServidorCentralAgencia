package com.uy.antel.controlador;

import java.net.*;
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
	public void listenSocket(){
		  try{
		    server = new ServerSocket(8082);
		  } catch (IOException e) {
		    System.out.println("Could not listen on port 8082");
		    System.exit(-1);
		  }
		  while(true){
		    atenderTerminal w;
		    try{
		//server.accept returns a client connection
		      w = new atenderTerminal(server.accept());
		      Thread t = new Thread(w);
		      t.start();
		    } catch (IOException e) {
		      System.out.println("Accept failed: 8082");
		      System.exit(-1);
		    }
		  }
		}

	

}
