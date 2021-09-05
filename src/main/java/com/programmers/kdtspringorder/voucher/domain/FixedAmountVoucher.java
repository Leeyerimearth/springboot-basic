package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this(voucherId, null, amount, VoucherType.FIXED, false, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long amount, VoucherType type, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        super(voucherId, customerId, amount, type, used, createdAt, expirationDate);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - getDiscountValue();
        if (discountedAmount < 0) {
            throw new IllegalArgumentException("디스카운트된 금액은 마이너스가 될 수 없다");
        }
        return discountedAmount;
    }
}