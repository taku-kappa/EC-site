package backend.dto.response;

import lombok.Data;

@Data
public class MonthlySalesResponse {

    // 対象月
    private String yearMonth;

    // 月間売上金額
    private Long totalSalesAmount;

}
