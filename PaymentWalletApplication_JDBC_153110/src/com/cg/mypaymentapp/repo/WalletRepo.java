package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletRepo {

	public Customer findOne(String mobileNo);

	
	public void saveTransactions(String mobileNo, Date date, String type, BigDecimal amount);

	public List printTransactions(String mobileNo);

	public boolean save(Customer customer);


	
}
