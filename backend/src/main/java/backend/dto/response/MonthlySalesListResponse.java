package backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MonthlySalesListResponse {

    // 月別売上金額
    private List<MonthlySalesResponse> monthlySales;

    // 対象月（始め）
    private String startMonth;

    // 対象月（終わり）
    private String endMonth;

}
