package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.*;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo{

	private Map<String, Customer> data; 
	public WalletRepoImpl(Map<String, Customer> data) 
	{
		super();
		this.data = data;
	}

	@Override
	public boolean save(Customer customer) 
	{
		try(Connection connection = DBUtil.getConnection())
		{
			
			PreparedStatement pre=connection.prepareStatement("select * from paytm_account_details where mobile_number=?");
			pre.setString(1, customer.getMobileNo());
			
			ResultSet result=pre.executeQuery();
			
			if(result.next())
			{
				
				PreparedStatement pre1=connection.prepareStatement("update paytm_account_details set balance=? where mobile_number=?");
				
				pre1.setString(2, customer.getMobileNo());				
				pre1.setBigDecimal(1, customer.getWallet().getBalance());
				pre1.execute();
			}
			else
			{
				PreparedStatement pre2=connection.prepareStatement("Insert into paytm_account_details values(?,?,?)");
				pre2.setString(1, customer.getMobileNo());
				pre2.setString(2, customer.getName());
				pre2.setBigDecimal(3, customer.getWallet().getBalance());
				pre2.execute();
			}
				
			
			
		}
		catch(ClassNotFoundException | SQLException e)
		{
			throw new InvalidInputException(e.getMessage());
		}
		return true;
	}
	
    @Override
	public Customer findOne(String mobileNo) 
	{
		Customer customer=new Customer();
		customer=null;
		
		try(Connection connection=DBUtil.getConnection())
		{
			PreparedStatement pre3=connection.prepareStatement("select * from paytm_account_details where mobile_number=?");
			pre3.setString(1, mobileNo);
			
			ResultSet result = pre3.executeQuery();
			
			while(result.next() == true)
			{
				customer=new Customer();	
			customer.setMobileNo(result.getString(1));
			customer.setName(result.getString(2));
			customer.setWallet(new Wallet(result.getBigDecimal(3)));
			}
		}
		
		catch(ClassNotFoundException | SQLException e)
		{
			
			throw new InvalidInputException(e.getMessage());
		}
						
		
		return customer;
	}

	@Override
	public void saveTransactions(String mobileNo, Date date, String type, BigDecimal amount) 
	{
		try(Connection connetion=DBUtil.getConnection())
		{
			PreparedStatement pre=connetion.prepareStatement("Insert into transaction_details values(?,?,?,?)");
			pre.setString(1, mobileNo);
			pre.setDate(2, new java.sql.Date(date.getTime()));
			pre.setString(3, type);
			pre.setBigDecimal(4, amount);
			pre.execute();
		}
		catch(ClassNotFoundException | SQLException e)
		{
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	
	public List printTransactions(String mobileNo)
	{
		List<String> transHistory=new ArrayList<String>();
		
		try(Connection connetion=DBUtil.getConnection())
		{
			String entry = null;
			
			PreparedStatement pre=connetion.prepareStatement("select * from transaction_details where mobile_number=?");
			pre.setString(1, mobileNo);
			ResultSet result=pre.executeQuery();
			while(result.next())
			{
				String type=result.getString(3);
				Date date=result.getDate(2);
				BigDecimal amount=result.getBigDecimal(4);
				if(type.equals("d"))
					 entry = "The Amount of Rupees "+amount+" is deposited on "+date;
				else
					entry = "The Amount of Rupees "+amount+" is withdrawn on "+date;
				
			    transHistory.add(entry);
			}
			if(transHistory.isEmpty())
				throw new InvalidInputException("No Transactions made in this Account");
		}
		catch(ClassNotFoundException | SQLException e)
		{
			throw new InvalidInputException(e.getMessage());
		}
		
		return transHistory;
	}
	
	
}
