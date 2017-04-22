package com.psl.closecampus.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.psl.closecampus.controller.GMailSender;
import com.psl.closecampus.controller.Way2SMS;
import com.psl.closecampus.entity.Employee;
import com.psl.closecampus.entity.PersonViolation;

public class EmployeeRegisterDaoImpl implements IEmployeeRegisterDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Employee getEmployee(String mac_addr) {
		
	
		return jdbcTemplate.queryForObject("select * from person where mac_addr='"+mac_addr+"'", new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Employee employee = new Employee();
				employee.setMac_addr(rs.getString(1));
				employee.setOwner_name(rs.getString(2));
				employee.setVehicle_no(rs.getString(3));
				employee.setMob_no(rs.getString(4));
				
				return employee;
			}
			
		});
	}

	@Override
	public void registerEmployee(Employee employee) {
		String sql = "insert into person values('"+employee.getMac_addr()+"','"+employee.getOwner_name()+"','"+employee.getVehicle_no()+"','"+employee.getMob_no()+"')";
		jdbcTemplate.update(sql);
		System.out.println("Employee Registered Successfully");
	}

	@Override
	public void sendSmsOnEmployeeNo(Employee employee) {
		
	}

	@Override
	public void sendEmployeeDetailsToAdmin(Employee employee) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getViolatedPerson(String mac_addr) {
		return jdbcTemplate.queryForObject("select * from person where mac_addr='"+mac_addr+"'", new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Employee emp = new Employee();
				emp.setMac_addr(rs.getString(1));
				emp.setOwner_name(rs.getString(2));
				emp.setVehicle_no(rs.getString(3));
				emp.setMob_no(rs.getString(3));
				return emp;
			}
			
		});
		
	}

	@Override
	public void addViolatedPerson(PersonViolation personViolation) throws IOException {
		System.out.println("Gonna add record into db");
		String sql = "insert into person_violation_details values('"+personViolation.getMac_addr()+"',sysdate())";
		jdbcTemplate.update(sql);
		System.out.println(personViolation.getMac_addr());
		System.out.println("Record Added");
		
		Employee employee = jdbcTemplate.queryForObject("select * from person where mac_addr='"+personViolation.getMac_addr()+"'", new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Employee emp = new Employee();
				emp.setMac_addr(rs.getString(1));
				emp.setOwner_name(rs.getString(2));
				emp.setVehicle_no(rs.getString(3));
				emp.setMob_no(rs.getString(4));
								
				return emp;
			}
			
		});
		
		System.out.println("Moving to sms and email services");
		

		String mobileNo = employee.getMob_no();
		
		System.out.println("Mobile no : "+mobileNo);
		
		GMailSender gs=new GMailSender("technothon2k17", "1234@pass");
		try {
			gs.sendMail("Technothon", "Mail part is working.", "technothon2k17@gmail.com", "patilsurendra16@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         final String USERNAME = "8149422856";//REQUIRED
        final String PASSWORD = "5121994";//REQUIRED
        final String ACTION = "/smstoss.action";//REQUIRED : In order to understand ACTION value please read the blog
        Way2SMS sms = new Way2SMS();
        String phoneNumber = mobileNo;
        String message = "ProductRest SMS is working..";

        String cookie = sms.loginWay2SMS(USERNAME, PASSWORD);
        String textMessage = message.toString();
        String strPhoneNumber = phoneNumber.toString();
        String arrPhoneNUmber[] = strPhoneNumber.split(";");
        for (int i = 0; i < arrPhoneNUmber.length; i++)
        {
            sms.sendSMS(arrPhoneNUmber[i], textMessage);
        }

        sms.logoutWay2SMS();
        
        System.out.println("Message send");

		

		
	}



}
