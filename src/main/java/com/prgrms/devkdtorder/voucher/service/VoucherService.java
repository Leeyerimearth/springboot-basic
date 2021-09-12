package com.prgrms.devkdtorder.voucher.service;

import com.prgrms.devkdtorder.exception.ErrorType;
import com.prgrms.devkdtorder.exception.NotFoundException;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ErrorType.VOUCHER_NOT_FOUND.with("for " + voucherId)));
    }

    public UUID saveVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
        return voucher.getVoucherId();
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public UUID updateVoucher(Voucher voucher) {
        Voucher updatedVoucher = voucherRepository.update(voucher);
        return updatedVoucher.getVoucherId();
    }
}
