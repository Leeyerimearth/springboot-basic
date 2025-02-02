package org.prgrms.kdt.service;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private final CustomerJdbcRepository customerRepository;

    private final JdbcWalletRepository jdbcWalletRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerJdbcRepository customerRepository, JdbcWalletRepository jdbcWalletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public Voucher createVoucher(UUID voucherId, int voucherTypeNumber, long discountAmount) {
        Voucher voucher = getVoucherTypeByNumber(voucherTypeNumber).createNewVoucher(voucherId, discountAmount, LocalDateTime.now());
        voucherRepository.insertVoucher(voucher);
        return voucher;
    }

    public VoucherMap getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    public void deleteVoucher(UUID voucherId, String email) {
        jdbcWalletRepository.getVoucherByVoucherIdAndEmail(voucherId, email)
                .ifPresent((voucher) -> {
                    voucherRepository.deleteVoucherById(voucherId);
                    OutputConsole.printMessage("voucher is deleted");
                });
    }

    public Vouchers getOwnableVoucherList() {
        return voucherRepository.getVoucherListOwnerIdIsEmpty();
    }

    public Optional<Voucher> provideVoucherToCustomer(String voucherId, String customerId) {
        try {
            voucherRepository.getVoucherNotProvided(UUID.fromString(voucherId)).orElseThrow();
            customerRepository.findCustomerById(UUID.fromString(customerId)).orElseThrow();
        } catch (Exception e) {
            return Optional.empty();
        }
        return voucherRepository.updateVoucherOwner(UUID.fromString(voucherId), UUID.fromString(customerId));
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.getByVoucherId(voucherId);
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteVoucherById(voucherId);
    }

    public Vouchers getVoucherListByVoucherType(int voucherType) {
        return voucherRepository.getVoucherListByVoucherType(voucherType);
    }
    public Vouchers getVoucherListByCreatedFromToDate(String fromDate, String toDate) {
        return voucherRepository.getVoucherListByCreatedFromToDate(fromDate, toDate);
    }

    private VoucherType getVoucherTypeByNumber(int voucherTypeNumber) {
        Optional<VoucherType> voucherType = VoucherType.getVoucherTypeByNumber(voucherTypeNumber);
        if (voucherType.isEmpty()) {
            throw new IllegalArgumentException("WRONG : input right voucher type");
        }
        return voucherType.get();
    }
}
