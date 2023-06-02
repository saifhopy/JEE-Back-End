package com.bank.Digitale_Banking.repositories;

import com.bank.Digitale_Banking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
