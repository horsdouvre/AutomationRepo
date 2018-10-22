package com.albertsons.portal.automation.common;


import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class JdbcOracleConnection {


   public  Connection con = null;
    public  String connString = null;

    public enum DB_CONNECTION {
        DB_QI {
            @Override
            public String toString() {
                return "jdbc:oracle:thin:tsqieo/tsqieo$Q2Q4@167.146.155.17:20001/tseoqi";
            }
        },
        DB_QA1 {
            @Override
            public String toString() {
                return "jdbc:oracle:thin:tsqieo/tsqieo$Q2Q4@167.146.155.17:20001/tseoqi";
            }
        },
        DB_QA2 {
            @Override
            public String toString() {
                return "jdbc:oracle:thin:RPANG03/Safeway08132017@167.146.155.13:20001/tseoq2_app";
            }
        }
    }
    public JdbcOracleConnection(String testEnv) {
        if ("qa1".contains(testEnv.toLowerCase())) {

            this.connString = DB_CONNECTION.DB_QA1.toString();
        } else if ("qa2".contains(testEnv.toLowerCase())) {

            this.connString = DB_CONNECTION.DB_QA2.toString();
        }
        else if ("qi".contains(testEnv.toLowerCase())) {

            this.connString = DB_CONNECTION.DB_QI.toString();
        }
    }


    public Connection getConnection(String dbURL) {

        try {


            if (con == null) {
                Class.forName("oracle.jdbc.OracleDriver");

                con = DriverManager.getConnection(dbURL);
                System.out.println("Connected with connection #1");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return con;
    }


    public Map<Integer, Map<String, String>> execute(String sql)
            throws Exception {

        ResultSet rs = null;
        Map<Integer, Map<String, String>> recordSet = new LinkedHashMap<Integer, Map<String, String>>();
        Statement stmt = null;

        try {
            String dbURL = "jdbc:oracle:thin:ilq2os/ilq2os_234@167.146.155.14:20001/iaspeq2";
            con = getConnection(dbURL);
            stmt = con.createStatement();

            rs = stmt.executeQuery(sql);
            recordSet = convertRSToHashMap(rs);


        } catch (SQLException se) {
            throw new Exception("Cannot execute SQL query: "
                    + se.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }


            } catch (Exception e) {
                throw new Exception("Can not close recordset "
                        + e.getMessage());
            }
        }

        return recordSet;


    }

    public void closeConnection() throws Exception{
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    public Map<Integer, Map<String, String>> convertRSToHashMap(ResultSet rs)
            throws SQLException {
        Map<Integer, Map<String, String>> recordSet = new LinkedHashMap<Integer, Map<String, String>>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int counter = 1;
        while (rs.next()) {
            Map<String, String> record = new LinkedHashMap<String, String>();
            int i = 1;
            while (i <= rsmd.getColumnCount()) {
                record.put(rsmd.getColumnName(i), rs.getString(i));
                System.out.println("Record:"+i+"Column: " +rsmd.getColumnName(i) + "Value"+rs.getString(i));
                i++;
            }
            recordSet.put(counter, record);
            counter++;
        }
        return recordSet;
    }


    public  Map<Integer, Map<String, String>> executeQueryWithTimer(String selectStr,
                                        String fromStr, String whereStr) throws  Exception {

        Map<Integer, Map<String, String>> recordSet = null;
        long start = System.currentTimeMillis();
        long end = start + 15*60*1000; // 60 seconds * 1000 ms/sec
         while (System.currentTimeMillis() < end)
        {

            recordSet =  this.selectQuery(selectStr, fromStr, whereStr);
            if (recordSet.size() > 0){
                closeConnection();
                return recordSet;
            }

        }

        closeConnection();
        return recordSet;
    }



    public Map<Integer, Map<String, String>> selectQuery(String selectStr,
                                                         String fromStr, String whereStr)
            throws Exception {

        String sql = null;
        Map<Integer, Map<String, String>> recordSet = null;

        if (whereStr == null) {
            sql = "SELECT " + selectStr + " FROM " + fromStr.trim();
        } else {
            sql = "SELECT " + selectStr + " FROM " + fromStr.trim() + "   WHERE "
                    + whereStr;
        }
        recordSet = execute(sql);

       //if (recordSet.size() == 0)
          //  throw new Exception("Query returned no results. SQL:" + sql);
        return recordSet;
    }


    public  boolean verifyDBUpdate(String strEmailID) throws Exception
    {

            Map<Integer, Map<String, String>> recordSet = null;
            boolean flag = false;
            Map<String, String> custMap = null;
            String custId = null;

            recordSet = executeQueryWithTimer("CUST_GUID", "IASPE.ldap_customer","EMAIL_ID = '"+strEmailID+"'");

          if (recordSet.size() > 0)
            {
                for (int key: recordSet.keySet()) {

                    custMap =  recordSet.get(key);
                }
                for (String custKey: custMap.keySet()) {

                    custId =  custMap.get(custKey);
                }
                if ( custId.toString().length() != 0)
                    flag = true;
            }
           return flag;

       }

    public String  getEmailToken(String strEmailID) throws Exception
    {

        Map<Integer, Map<String, String>> recordSet = null;
        String emailToken = null;
        Map<String, String> custMap = null;
        String custId = null;



        recordSet = executeQueryWithTimer("CUST_GUID", "IASPE.ldap_customer","EMAIL_ID = '"+strEmailID+"'");

        if (recordSet.size() > 0)
        {
            for (int key: recordSet.keySet()) {

                custMap =  recordSet.get(key);
            }
            for (String custKey: custMap.keySet()) {

                custId =  custMap.get(custKey);
            }
            if ( custId.toString().length() != 0)
                emailToken = custId.toString();
        }
        return emailToken;

    }


    public static void main(String[] args) throws Exception {

       // System.out.println(verifyDBUpdate("s88@example.com"));
        }

}