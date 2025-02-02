package org.prgrms.kdt.service;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherWalletService {

    private final JdbcWalletRepository jdbcWalletRepository;

    public VoucherWalletService(JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public VoucherMap getVoucherListByCustomerEmail(String customerEmail) {
        Map<UUID, Voucher> vouchers = jdbcWalletRepository.getVoucherListByCustomerId(customerEmail);
        VoucherMap voucherMap = new VoucherMap(vouchers);
        return voucherMap;
    }

    public Vouchers getVoucherWalletList() {
        List<Voucher> voucherWalletList = jdbcWalletRepository.getVoucherWalletList();
        return new Vouchers(voucherWalletList);
    }

    public Optional<Voucher> getVoucherWalletById (UUID voucherId) {
        return jdbcWalletRepository.selectJoinVoucherCustomerByVoucherId(voucherId);
    }

}
