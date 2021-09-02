package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ExceptionMessage;
import org.prgrms.kdt.exception.VoucherPercentRangeException;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validate(voucherId, percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void validate(UUID voucherId, long percent) {
        if(percent < 0 || percent > 100){
            throw new VoucherPercentRangeException(ExceptionMessage.INVALID_VOUCHER_PERCENT_RANGE.getMessage());
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }

    @Override
    public String toString() {
        return MessageFormat.format("[PercentDiscountVoucher - 할인률 : {0}%]", percent);
    }

    public long getPercent() {
        return percent;
    }
}