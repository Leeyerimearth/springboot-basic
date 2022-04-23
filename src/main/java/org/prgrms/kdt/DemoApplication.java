package org.prgrms.kdt;

import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.VoucherProgram;
import org.prgrms.kdt.function.FunctionOperator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(DemoApplication.class, args);
        Output output = new OutputConsole();
        FunctionOperator functionOperator = applicationContext.getBean(FunctionOperator.class);

        new VoucherProgram(output, functionOperator).run();
    }
}
