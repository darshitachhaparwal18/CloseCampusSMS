<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Open+Sans:600'/>
  <link href="https://fonts.googleapis.com/css?family=Lobster+Two" rel="stylesheet"/>
  <link href="<s:url value='/resources/css/style.css'/>"  rel="stylesheet" type="text/css"/>

</head>
<body bgcolor="#6a6f8c" style="color: white;">
<center><h1>Violated Users Details</h1></center>

<div style="100px;overflow:auto;color: white;" >
 <%@page language="java"%>
					<%@page import="java.sql.*"%> 
					 <table border="1" align="center">
					<tr>
						<th>Owner Name</th>
						<th>Vehicle no</th>
						<th>Mobile no</th>
						<th>Mac Address</th>
						<th>Entry Time</th>
						
						
					</tr>
						<%
							try {
								Class.forName("com.mysql.jdbc.Driver").newInstance();
								Connection conn = DriverManager.getConnection(
										"jdbc:mysql://localhost:3307/closecampusdb", "root", "root");
								String query = "select p.owner_name,p.vehicle_no ,p.mob_no,pv.mac_addr,pv.Entry_time from person p,person_violation_details pv where p.mac_addr=pv.mac_addr";
								Statement st = conn.createStatement();
								ResultSet rs = st.executeQuery(query);
								
								while (rs.next()) {
						%>
						<tr >
						
							<td><input type="text" name="owner_name"
								value="<%=rs.getString(1)%>" style="color: red;"></td>
														
						
						<td><input type="text" name="vehicle_no"
								value="<%=rs.getString(2)%>" style="color: red;"></td>
						
							<td><input type="text" name="mob_no"
								value="<%=rs.getString(3)%>" style="color: red;"></td>
						
							<td><input type="text" name="mac_addr"
								value="<%=rs.getString(4)%>" style="color: red;"></td>
						
						<td><input type="text" name="Entry_time"
								value="<%=rs.getString(5)%>" style="color: red;"></td>
						
						</tr>
						<%
}
}
catch(Exception e){}
%>
					</table> </div>
</body>
</html>