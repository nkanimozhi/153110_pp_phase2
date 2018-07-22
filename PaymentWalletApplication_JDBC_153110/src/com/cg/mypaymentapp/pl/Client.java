package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.*;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	private WalletService walletService;
	private Map<String,Customer> data=new HashMap<String, Customer>();
	public Client() 
	{
		System.out.println("Welcome to Payment Wallet Application");
		walletService=new WalletServiceImpl(data);
	}
		
	public void Operations()
	{
		System.out.println("1) Create New Paytm Account");
		System.out.println("2) Check Your Balance");
		System.out.println("3) Transfer Funds");
		System.out.println("4) Deposit Amount");
		System.out.println("5) Withdraw Amount");
		System.out.println("6) Print Transactions History");
		System.out.println("7) Exit Application");
		System.out.println();
		System.out.println("Enter Your Choice");
		
		Scanner console=new Scanner(System.in);
		
		String mobileNo;
		String mobileNo1;
		BigDecimal amount;
		String name;
		Customer customer;
		switch (console.nextInt()) 
		{
			case 1: 
				
					System.out.println("Enter Your Name          : ");
					name=console.next();
					
					System.out.println("Enter Your Mobile Number : ");
					mobileNo=console.next();
					
					System.out.println("Enter Balance            : ");
					amount=console.nextBigDecimal();
				
			        try {
			        	Customer customer1=walletService.createAccount(name, mobileNo, amount);
			        	
			        	System.out.println("Thank you, "+customer1.getName()+" Your Payment wallet account has been created successfully with Balance "+amount);
			            }
			        catch (InvalidInputException e) 
			            {
			        	System.out.println(e.getMessage());
			            }
				 
					break;
			case 2: 
				
					
				
				  System.out.println("Enter the Mobile Number : ");
				  mobileNo=console.next();
				
			    try 
			    {
			    	customer=walletService.showBalance(mobileNo);
			    	System.out.println("Your Current Balance is "+customer.getWallet().getBalance());
			    	
			    } 
			    catch (InvalidInputException | InsufficientBalanceException e)
			    {
			    	System.out.println(e.getMessage());				
			    }
			
			break;
			
					
			
        case 3: 
				String source,target;
				System.out.println("Enter the Source Mobile Number : ");
				source=console.next();
				
				System.out.println("Enter the Destination mobile number : ");
				target=console.next();
				
				System.out.println("Enter the amount  : ");
				amount=console.nextBigDecimal();
			    try 
			    {
			    	customer=walletService.fundTransfer(source, target, amount);
			    	System.out.println("Your transaction is successfully done.. ");
			    	System.out.println("Now Your Account Balance is "+customer.getWallet().getBalance());
			    } 
			    catch (InvalidInputException | InsufficientBalanceException e)
			    {
			    	System.out.println(e.getMessage());				
			    }
			
			break;
			
        case 4: 
			
			System.out.println("Enter the Mobile Number : ");
			mobileNo=console.next();
			
			System.out.println("Enter the amount to be deposited : ");
			amount=console.nextBigDecimal();
		    try 
		    {
		    	customer=walletService.depositAmount(mobileNo, amount);
		    	System.out.println("Your have successfully deposited... ");
		    	System.out.println("Now Your Account Balance is "+customer.getWallet().getBalance());
		    } 
		    catch (InvalidInputException | InsufficientBalanceException e)
		    {
		    	System.out.println(e.getMessage());				
		    }
		    break;
     case 5: 
			
			System.out.println("Enter the Mobile Number : ");
			mobileNo=console.next();
			
			System.out.println("Enter the amount to be withdrawn : ");
			amount=console.nextBigDecimal();
		    try 
		    {
		    	customer=walletService.withdrawAmount(mobileNo, amount);
		    	System.out.println("Your have successfully withdrawn... ");
		    	System.out.println("Now Your Account Balance is "+customer.getWallet().getBalance());
		    } 
		    catch (InvalidInputException | InsufficientBalanceException e)
		    {
		    	System.out.println(e.getMessage());				
		    }
		
		break;
		
     case 6: System.out.println("Enter the Mobile Number");
             String mobile=console.next();
             List<String> list = new ArrayList<>();
             
             try {
				list=walletService.printTransactions(mobile);
				 
				 for (String string : list) 
				 {
					System.out.println(string); 				
				 }
				 System.out.println("Your current Balance is  + Rupees "+walletService.showBalance(mobile));
			} catch (InvalidInputException e)  
             {
				System.out.println(e.getMessage());
			 }
  
             break;
		
     case 7: System.out.println("Thank you for using Payment Wallet Application");
             System.exit(0);
             break;


		default: System.out.println("You Entered an Invalid Option");
			break;
		}
	}
	   public static void main( String[] args )
	   {
		   Client client=new Client();
		   while(true)
			   client.Operations();
	   }
}
