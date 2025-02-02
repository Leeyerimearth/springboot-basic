package org.prgrms.kdt.controller;

import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.function.VoucherProgramFunctions;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String showVoucherList(Model model) {
        VoucherMap voucherMap = voucherService.getVoucherList();
        Map<UUID, Voucher> vouchers = voucherMap.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(Model model, VoucherDto voucherDto) {
        Voucher createdVoucher = voucherService.createVoucher(UUID.randomUUID(), voucherDto.voucherType, voucherDto.discountAmount);
        model.addAttribute(createdVoucher);
        return "home";
    }

    @GetMapping("/vouchers/voucherCreateForm")
    public String createVoucherForm() {
        return "vouchers/voucherCreateForm";
    }

    @GetMapping("/vouchers/detail/{voucherId}")
    public String showVoucherDetail(@PathVariable String voucherId, Model model) {
        UUID convertedVoucherId = UUID.fromString(voucherId);
        Voucher voucher = voucherService.getVoucherById(convertedVoucherId).get();
        model.addAttribute("voucher", voucher);
        return "vouchers/voucherDetail";
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
        return "home";
    }

}
