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
		try {// todo: mejorar las excepciones
			initContext = new InitialContext();

			DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MySqlAGENCIA");
			Connection conn = ds.getConnection();
			PreparedStatement ps_auto = conn.prepareStatement("select idAuto from auto where matricula=?");
			ps_auto.setString(1, matricula);
			ResultSet rs_auto = ps_auto.executeQuery();
			if (rs_auto.next()) {
				// Existe el auto
				conn.setAutoCommit(false);
				PreparedStatement ps_insert_ticket = conn.prepareStatement(
						"insert into ticket (nroTicket,fechaVenta,fechaIniE,cantMinutos,ImporteTotal,fk_auto) values (?,?,?,?,?,?)");
				ps_insert_ticket.setInt(1, nroTicket);
				ps_insert_ticket.setDate(2, new java.sql.Date(fechaVenta.getTime()));
				ps_insert_ticket.setDate(3, new java.sql.Date(fechaIniE.getTime()));
				ps_insert_ticket.setInt(4, cantMinutos);
				ps_insert_ticket.setInt(5, importeTotal);
				ps_insert_ticket.setInt(6, rs_auto.getInt("idAuto"));
				ps_insert_ticket.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
				rs_auto.close();
				ps_auto.close();
				ps_insert_ticket.close();
				conn.close();
			} else {
				rs_auto.close();
				ps_auto.close();
				conn.close();
				// ERROR
				throw new Exception("NO existe un auto con esa matricula en el sistema");

			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			throw new Exception("Error del sistema.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Error del sistema.");
		}

	}

	public void altaAuto(String matricula) throws Exception {

		InitialContext initContext;
		try {// todo: mejorar las excepciones
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
			} else {
				conn.setAutoCommit(false);
				PreparedStatement ps_secuencia = conn.prepareStatement("select secuencia from secuencias where nombre='auto'");
				ResultSet rs_secuencia = ps_secuencia.executeQuery();
				int secuencia;
				if (rs_secuencia.next()) {
					secuencia = rs_secuencia.getInt("secuencia");
					secuencia++;
				} else {
					throw new SQLException("No se pudo conseguir la secuencia");
				}
				PreparedStatement ps_update_secuencia = conn
						.prepareStatement("update secuencias set secuencia=? where nombre='auto'");
				ps_update_secuencia.setInt(1, secuencia);
				ps_update_secuencia.executeUpdate();
				PreparedStatement ps_insert_auto = conn.prepareStatement("insert into auto (matricula, idAuto) values (?,?)");
				ps_insert_auto.setString(1, matricula);
				ps_insert_auto.setInt(2, secuencia);
				ps_insert_auto.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
				rs_secuencia.close();
				ps_secuencia.close();
				ps_update_secuencia.close();
				rs_auto.close();
				ps_auto.close();
				ps_insert_auto.close();
				conn.close();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Error del sistema.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Error del sistema.");
		}

	}

	public boolean login(String usuario, String password, int nroTerminal) throws Exception {

		InitialContext initContext;
		try {// todo: mejorar las excepciones
			initContext = new InitialContext();

			DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MySqlAGENCIA");
			Connection conn = ds.getConnection();
			PreparedStatement ps_usuario = conn
					.prepareStatement("select * from usuario where usuario=? and password=?");
			ps_usuario.setString(1, usuario);
			ps_usuario.setString(2, password);
			ResultSet rs_usuario = ps_usuario.executeQuery();
			if (rs_usuario.next()) {
				// Existe el usuario

				PreparedStatement ps_rol_terminal = conn.prepareStatement(
						"select t.nroTerminal from (((rol_usuario ru join rol ro on ru.fk_rol = ro.rol) join rol_terminal rt on rt.fk_rol = ro.rol) join on terminal t on rt.fk_terminal = t.nroTerminal) where ru.fk_usuario = ? and t.nroTerminal = ?");
				ps_rol_terminal.setString(1, usuario);
				ps_rol_terminal.setInt(2, nroTerminal);
				ResultSet rs_rol_terminal = ps_rol_terminal.executeQuery();

				if (rs_rol_terminal.next()) {
					// El usuario se puede loguear en la terminal
					rs_usuario.close();
					ps_usuario.close();
					rs_rol_terminal.close();
					ps_rol_terminal.close();
					conn.close();
					return true;
				}
				//el usuario no se puede loguear en la terminal
				rs_rol_terminal.close();
				ps_rol_terminal.close();
				rs_usuario.close();
				ps_usuario.close();
				conn.close();
				throw new Exception("El usuario no se puede loguear en la terminal");
			}
			//El usuario no existe
			rs_usuario.close();
			ps_usuario.close();
			conn.close();
			throw new Exception("Usuario o password incorrecta");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
}
