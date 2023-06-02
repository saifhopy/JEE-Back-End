package com.bank.Digitale_Banking;


import com.bank.Digitale_Banking.dtos.BankAccountDTO;
import com.bank.Digitale_Banking.dtos.CurrentBankAccountDTO;
import com.bank.Digitale_Banking.dtos.CustomerDTO;
import com.bank.Digitale_Banking.dtos.SavingBankAccountDTO;
import com.bank.Digitale_Banking.entities.AccountOperation;
import com.bank.Digitale_Banking.entities.CurrentAccount;
import com.bank.Digitale_Banking.entities.Customer;
import com.bank.Digitale_Banking.entities.SavingAccount;
import com.bank.Digitale_Banking.enums.AccountStatus;
import com.bank.Digitale_Banking.enums.OperationType;
import com.bank.Digitale_Banking.exceptions.BalanceNotSufficientException;
import com.bank.Digitale_Banking.exceptions.BankAccountNotFoundException;
import com.bank.Digitale_Banking.exceptions.CustomerNotFoundException;
import com.bank.Digitale_Banking.repositories.AccountOperationRepository;
import com.bank.Digitale_Banking.repositories.BankAccountRepository;
import com.bank.Digitale_Banking.repositories.CustomerRepository;
import com.bank.Digitale_Banking.security.entities.AppRole;
import com.bank.Digitale_Banking.security.entities.AppUser;
import com.bank.Digitale_Banking.security.services.AccountService;
import com.bank.Digitale_Banking.security.services.AccountServiceImpl;
import com.bank.Digitale_Banking.services.BankAccountService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Stream;


@SpringBootApplication
public class DigitaleBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitaleBankingApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService,AccountService accountService){
		return args -> {

			Faker faker = new Faker(new Random(12));

			for (int i = 0; i < 12; i++) {
				{
					String fname = faker.name().firstName();
					String lname = faker.name().lastName();
					CustomerDTO customer = new CustomerDTO();
					customer.setName(fname + " " + lname);
					customer.setEmail(fname + "." + lname + "@gmail.com");
					bankAccountService.saveCustomer(customer);
				}
			}
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 2000, 2000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 10000, 3.5, customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDTO bankAccount : bankAccounts) {
				for (int i = 0; i < 10; i++) {
					String accountId;
					if (bankAccount instanceof SavingBankAccountDTO) {
						accountId = ((SavingBankAccountDTO) bankAccount).getId();
					} else {
						accountId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId, 10001 + Math.random() * 10000, "Credit");
					bankAccountService.debit(accountId, 1001 + Math.random() * 2000, "Debit");
				}
			}
			accountService.addNewRole(new AppRole(null,"ADMIN"));
			accountService.addNewRole( new AppRole(null,"USER"));
			accountService.addNewUser(new AppUser(null,"Saif","SaifSaif",null));
			accountService.addNewUser(new AppUser(null,"Saiff","SaifSaif",null));
			accountService.addRoleToUser("Saif","USER");
			accountService.addRoleToUser("Saif","ADMIN");
			accountService.addRoleToUser("Saiff","USER");
		};
	}

}
