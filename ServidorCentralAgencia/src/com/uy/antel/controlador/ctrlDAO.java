package com.uy.antel.controlador;

import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ctrlDAO {

	private static ctrlDAO instance = null;

	public static ctrlDAO getInstance() {
		if (instance == null) {
			instance = new ctrlDAO();
		}
		return instance;
	}

	public void altaTicket(int nroTicket, Date fechaVenta, Date fechaIniE, int cantMinutos, int importeTotal,
			String matricula, int nroTerminal) throws Exception {

		InitialContext initContext;
		try {//todo: mejorar las excepciones
			initContext = new InitialContext();

			DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MySqlAGENCIA");
			Connection conn = ds.getConnection();
			PreparedStatement ps_auto = conn.prepareStatement("select * from auto where matricula=?");
			ps_auto.setString(1, matricula);
			ResultSet rs_auto = ps_auto.executeQuery();
			if (rs_auto.next()) {
				// Existe el auto
				conn.setAutoCommit(false);
				PreparedStatement ps_insert_ticket = conn.prepareStatement(
						"insert into ticket (nroTicket,fechaVenta,fechaIniE,cantMinutos,ImporteTotal,fk_anulacion,fk_auto) values (?,?,?,?,?,?,?)");
				ps_insert_ticket.setInt(1, nroTicket);
				ps_insert_ticket.setDate(2, new java.sql.Date(fechaVenta.getTime()));
				ps_insert_ticket.setDate(3, new java.sql.Date(fechaIniE.getTime()));
				ps_insert_ticket.setInt(4, cantMinutos);
				ps_insert_ticket.setInt(5, importeTotal);
				ps_insert_ticket.setString(7, matricula);
				ps_insert_ticket.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
				rs_auto.close();
				ps_auto.close();
				ps_insert_ticket.close();
				conn.close();
			}
			else{
				rs_auto.close();
				ps_auto.close();
				conn.close();
				//ERROR
				throw new Exception("NO existe un auto con esa matricula en el sistema");
				
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void altaAuto(String matricula) throws Exception {

		InitialContext initContext;
		try {//todo: mejorar las excepciones
			initContext = new InitialContext();

			DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MySqlAGENCIA");
			Connection conn = ds.getConnection();
			PreparedStatement ps_auto = conn.prepareStatement("select * from auto where matricula=?");
			ps_auto.setString(1, matricula);
			ResultSet rs_auto = ps_auto.executeQuery();
			if (rs_auto.next()) {
				// Existe el auto
				rs_auto.close();
				ps_auto.close();
				conn.close();
			}
			else{
				conn.setAutoCommit(false);
				PreparedStatement ps_insert_auto = conn.prepareStatement(
						"insert into auto (matricula) values (?)");
				ps_insert_auto.setString(1, matricula);
				ps_insert_auto.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
				rs_auto.close();
				ps_auto.close();
				ps_insert_auto.close();
				conn.close();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
