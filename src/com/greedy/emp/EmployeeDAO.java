package com.greedy.emp;

import static com.greedy.common.JDBCTemplate.close;
import static com.greedy.common.JDBCTemplate.getConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.greedy.model.dto.EmployeeDTO;

public class EmployeeDAO {

	/* 구현 조건
	 * 1. Connection 생성은 JDBC 템플릿 사용
	 * 2. query문은 xml 파일로 분리
	 * 3. 쿼리문에 값을 전달해야 하는 경우는 PreparedStatement, 아닌 경우는 Statment 사용
	 * 4. 한 행 정보는 DTO에 담아 출력, 여러 행 정보는 ArrayList 에 담아 출력
	 * */




	public void findOneEmpByEmpId(String EmpId) {

		/* 사번으로 직원정보 조회 후 EmployeeDTO에 담아 출력 */

//		Connection con = getConnection();
//
//		Statement stmt = null;
//		ResultSet rset = null;
//
//		EmployeeDTO selectedEmp = null;
//
//		String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = '" + EmpId + "'";
//		try {
//			stmt = con.createStatement();
//			rset = stmt.executeQuery(query);
//
//			if(rset.next()) {
//				selectedEmp = new EmployeeDTO();
//
//				selectedEmp.setEmpId(rset.getString("EMP_ID"));
//				selectedEmp.setEmpName(rset.getString("EMP_NAME"));
//				selectedEmp.setEmpNo(rset.getString("EMP_NO"));
//				selectedEmp.setEmail(rset.getString("EMAIL"));
//				selectedEmp.setPhone(rset.getString("PHONE"));
//				selectedEmp.setDeptCode(rset.getString("DEPT_CODE"));
//				selectedEmp.setJobCode(rset.getString("JOB_CODE"));
//				selectedEmp.setSalLevel(rset.getString("SAL_LEVEL"));
//				selectedEmp.setSalary(rset.getInt("SALARY"));
//				selectedEmp.setBonus(rset.getDouble("BONUS"));
//				selectedEmp.setManagerId(rset.getString("MANAGER_ID"));
//				selectedEmp.setHireDate(rset.getDate("HIRE_DATE"));
//				selectedEmp.setEntDate(rset.getDate("ENT_DATE"));
//				selectedEmp.setEntYn(rset.getString("ENT_YN"));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			close(rset);
//			close(stmt);
//			close(con);
//		}
//
//		System.out.println(selectedEmp);
//	}

			Connection con = getConnection();
	
			PreparedStatement pstmt = null;
			ResultSet rset = null;
	
			List<EmployeeDTO> empList = null;
	
			Properties prop = new Properties();
	
			try {
				prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));
	
				String query = prop.getProperty("selectEmpByFamilyName");
	
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, EmpId);
	
				rset = pstmt.executeQuery();
	
				empList = new ArrayList<>();
	
				while(rset.next()) {
	
					EmployeeDTO row = new EmployeeDTO();
	
					row.setEmpId(rset.getString("EMP_ID"));
					row.setEmpName(rset.getString("EMP_NAME"));
					row.setEmpNo(rset.getString("EMP_NO"));
					row.setEmail(rset.getString("EMAIL"));
					row.setPhone(rset.getString("PHONE"));
					row.setDeptCode(rset.getString("DEPT_CODE"));
					row.setJobCode(rset.getString("JOB_CODE"));
					row.setSalary(rset.getInt("SALARY"));
					row.setBonus(rset.getDouble("BONUS"));
					row.setManagerId(rset.getString("MANAGER_ID"));
					row.setHireDate(rset.getDate("HIRE_DATE"));
					row.setEntDate(rset.getDate("ENT_DATE"));
					row.setEntYn(rset.getString("ENT_YN"));
	
					empList.add(row);
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
				close(con);
			}
	
			for(EmployeeDTO emp : empList) {
				System.out.println(emp);
			}
	}
	
	
	public void findOneEmpByEvery() {
		Connection con = getConnection();

		Statement stmt= null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		String query = "SELECT * FROM EMPLOYEE";

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			close(stmt);
			close(rset);
			close(con);

			for(EmployeeDTO emp : empList) {
				System.out.println(emp);

			}
		}
	}
	public void findOneEmpBygender() {

		Connection con = getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Scanner sc = new Scanner(System.in);
		System.out.println("조회를 할 성별을 입력하세요 : ");
		int empNo = sc.nextInt();

		String query = "SELECT * FROM EMPLOYEE WHERE EMP_NO LIKE ? || '%'";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, empNo);

			rset = pstmt.executeQuery();

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}

	}

}







