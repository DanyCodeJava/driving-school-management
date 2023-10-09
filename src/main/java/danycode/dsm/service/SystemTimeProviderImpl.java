package danycode.dsm.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SystemTimeProviderImpl implements TimeProvider{
    @Override
    public LocalDate localDate() {
        return LocalDate.now();
    }
}
