
SQL> desc paytm_account_details;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------

 MOBILE_NUMBER                             NOT NULL NUMBER(10)
 CUSTOMER_NAME                                      VARCHAR2(30)
 BALANCE                                            NUMBER(7,2)

 
 SQL> desc transaction_details;
 Name                                      Null?    Type
 ----------------------------------------- -------- ---------------------

 MOBILE_NUMBER                                      NUMBER(10)
 TRANS_DATETIME                                     DATE
 TRANSACTION_TYPE                                   VARCHAR2(20)
 AMOUNT                                             NUMBER(7,2)
 