package com.goylik.cleverbank.entity.config;

import com.goylik.cleverbank.entity.Account;
import com.goylik.cleverbank.entity.Bank;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The class calculates and updates interest on a given account.
 */
public class InterestCalculator implements Observer {
    private final Account account;
    private final BigDecimal interestRate;

    /**
     * Constructs an InterestCalculator for the specified account.
     *
     * @param account The account for which interest will be calculated.
     */
    public InterestCalculator(Account account) {
        this.account = account;
        this.interestRate = new BigDecimal(getInterestRateFromYml());
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::update, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * Checks if it's the end of the month and triggers interest calculation asynchronously.
     */
    @Override
    public void update() {
        if (isEndOfMonth()) {
            calculateInterestAsync();
        }
    }

    /**
     * Check if the current date is the end of the month.
     *
     * @return true if it's the end of the month, false otherwise.
     */
    private boolean isEndOfMonth() {
        return LocalDate.now().getDayOfMonth() == LocalDate.now().lengthOfMonth();
    }

    /**
     * Calculate interest asynchronously and update the account balance.
     */
    private void calculateInterestAsync() {
        new Thread(() -> {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance = currentBalance.add(currentBalance.multiply(interestRate));
            account.setBalance(newBalance);
        }).start();
    }

    /**
     * Load the interest rate for the account's bank from a YAML configuration file.
     *
     * @return The interest rate as a string.
     */
    private String getInterestRateFromYml() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("interest-rates.yml");
        Map<String, Object> interestRates = yaml.load(inputStream);
        Bank bank = account.getBank();
        String bankCode = bank.getBankCode();
        return interestRates.get(bankCode).toString();
    }
}