package com.fithub.services.membership.test.suites;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.test.configuration.BasicTestConfiguration;

public class MembershipServiceTest extends BasicTestConfiguration {

    @BeforeMethod
    public void beforeMethod() {
    }

    @Test
    public void testPaymentRecord_ValidPaymentStatusIsProvided_ReturnTrue() {
        try {
            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setId(1L);
            paymentRecordEntity.setYear(2024);
            paymentRecordEntity.setMonth(Month.JANUARY.toString());
            paymentRecordEntity.setPaid(true);

            Assertions.assertThat(paymentRecordEntity.getPaid()).isTrue();
        } catch (Exception exception) {
            Assert.fail();
        }

    }

    @Test
    public void testPaymentRecord_ValidIdIsProvided_ReturnTrue() {
        try {
            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setId(1L);
            paymentRecordEntity.setYear(2024);
            paymentRecordEntity.setMonth(Month.JANUARY.toString());
            paymentRecordEntity.setPaid(true);

            Assertions.assertThat(paymentRecordEntity.getId()).isNotEqualTo(null);
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

}