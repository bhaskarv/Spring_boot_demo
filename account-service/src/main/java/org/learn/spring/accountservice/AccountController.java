package org.learn.spring.accountservice;

import org.learn.spring.accountservice.domain.Account;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AccountController {

//    private List<Account> accounts = Arrays.asList(new Account(1, "Savings", 2000000.10),
//            new Account(2, "Savings", 3000000.10));

    private static List<Account> accounts = Stream.of(new Account(1, "Savings", 2000000.10),
            new Account(2, "Savings", 3000000.10)).collect(Collectors.toList());

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public @ResponseBody
    List<Account> getAccounts() {
        return accounts;
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Account getAccount(@PathVariable String id) {
        Optional<Account> optionalAccount = accounts.stream().filter(account ->  Integer.parseInt(id) == account.getId())
                .findFirst();

        return optionalAccount.isPresent()?optionalAccount.get():new Account(0, "DEFAULT", 0);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public @ResponseBody List<Account> addAccount(@RequestBody Account account) {
        System.out.println(account);
        accounts.add(account);
        return accounts;
    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.DELETE)
    public @ResponseBody List<Account> deleteAccount(@PathVariable String id) {
        accounts.removeIf(account -> Integer.parseInt(id) == account.getId());
        return  accounts;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.PUT)
    public @ResponseBody List<Account> updateAccount(@RequestBody Account account) {
        if (accounts.removeIf(account1 ->  account.getId() == account1.getId())) {
            accounts.add(account);
        }

        return  accounts;

    }
}
