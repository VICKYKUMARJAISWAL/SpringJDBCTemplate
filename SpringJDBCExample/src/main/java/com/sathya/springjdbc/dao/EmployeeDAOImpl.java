package com.sathya.springjdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sathya.springjdbc.model.Employee;

public class EmployeeDAOImpl implements EmployeeDao {
	@SuppressWarnings("unused")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(Employee employee) {
		// TODO Auto-generated method stub
		String sql = "insert into employee(empid,name,dept,sal,hiredate) values(?,?,?,?,?)";
		Connection con=null;
		PreparedStatement pstmt =null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, employee.getEmpId());
			pstmt.setString(2, employee.getName());
			pstmt.setString(3, employee.getDept());
			pstmt.setInt(4, employee.getSal());
			pstmt.setDate(5, (Date) employee.getHiredate());
			int i=pstmt.executeUpdate();
			con.commit();
			if(i!=0) {
				System.out.println("Record inserted successfully"+employee.getEmpId());
			}
			else {
				System.out.println("Record insertion failed"+employee.getEmpId());
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
				pstmt.close();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}

	}

	public Employee getById(int id) {
		// TODO Auto-generated method stub
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		Employee emp=null;
		String sql = "select empid,name,dept,sal,hiredate from employee where empid=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs = (ResultSet) pstmt.executeQuery();
			if(rs!=null && rs.next()) {
				emp= new Employee();
				emp.setEmpId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setDept(rs.getString(3));
				emp.setSal(rs.getInt(4));
				emp.setHiredate(rs.getDate(5));
				if(emp!=null) {
					System.out.println("Id found in database !!");
				}
			}
			else {
				System.out.println("Id does not exits in database");
			}
		}catch(SQLException sl) {
			sl.printStackTrace();
		}
		finally {
			try {
				con.close();
				pstmt.close();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
		return emp;
	}

	public void update(Employee employee) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql = "update employee set name=? ,dept=? where empid = ?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee.getName());
			pstmt.setString(2, employee.getDept());
			pstmt.setInt(3, employee.getEmpId());
			int out = pstmt.executeUpdate();
			if (out != 0) {
				System.out.println("Employee updated with id=" + employee.getEmpId());
			} else
				System.out.println("No Employee found with id=" + employee.getEmpId());
		}catch(SQLException s) {
			s.printStackTrace();
		}
	}

	public void deleteById(int id) {
		// TODO Auto-generated method stub
		Connection con =null;
		PreparedStatement pstmt = null;
		@SuppressWarnings("unused")
		String sql = "delete from employee where empid =?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			int out =pstmt.executeUpdate();
			if(out !=0){
				System.out.println("Employee deleted with id="+id);
			}else System.out.println("No Employee found with id="+id);
		}catch(SQLException s) {
			s.printStackTrace();
		}
		finally
		{
			try {
				con.close();
				pstmt.close();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
		

	}

	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		Employee emp = null;
		List<Employee> listEmp = new ArrayList<Employee>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from employee";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					emp = new Employee();
					emp.setEmpId(rs.getInt(1));
					emp.setName(rs.getString(2));
					emp.setDept(rs.getString(3));
					emp.setSal(rs.getInt(4));
					emp.setHiredate(rs.getDate(5));
					listEmp.add(emp);
				}
			}else {
				System.out.println("Record is not in database");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(!(listEmp.isEmpty())) {
			return listEmp;
		}
		else {
			System.out.println("Employee List object has null value even record in in database !Serious problems");
			
			return listEmp;
		}
		
	}

}
