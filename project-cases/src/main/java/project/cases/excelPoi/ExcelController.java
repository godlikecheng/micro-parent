package project.cases.excelPoi;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *  ExcelTest控制类
 *  请求测试地址 : localhost/tableToxls
 *  JSON数据: {"data":[[{"property":"header_empty","data":null},{"property":"header_key","data":"共同作案"},{"property":"header_key","data":"单独作案"},{"property":"header_key","data":"0"}],[{"property":"header_key","column_header_header":true,"data":"作案方式"},{"property":"header_key","data":"数量"},{"property":"header_key","data":"数量"},{"property":"header_key","data":"数量"}],[{"property":"column_key","data":"恐怖组织"},{"property":"data","data":""},{"property":"data","data":"1","raw":"1"},{"property":"data","data":"3","raw":"3"}],[{"data":"0","rowSpan":"row","property":"column_key"},{"property":"data","data":"850","raw":"850"},{"property":"data","data":"19950","raw":"19950"},{"property":"data","data":"2108130","raw":"2108130"}],[{"data":"集团","rowSpan":"row","property":"column_key"},{"property":"data","data":"215","raw":"215"},{"property":"data","data":"42","raw":"42"},{"property":"data","data":"2877","raw":"2877"}],[{"data":"黑社会性质组织","rowSpan":"row","property":"column_key"},{"property":"data","data":""},{"property":"data","data":""},{"property":"data","data":"25","raw":"25"}]],"type":"table"}
 */

@RestController
public class ExcelController {

    @Autowired
    private XlsProcessService xlsProcessService;

    @RequestMapping(value = "/tableToxls")
    public ResponseEntity<byte[]> tableToxls(@RequestParam(name = "data") String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        System.out.println("转换成JSON对象:" + jsonObject);
        HSSFWorkbook wb = xlsProcessService.tableToxls(jsonObject);  // XlsProcessService.java
        System.out.println("执行了...");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            wb.write(out);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "table.xls");
            System.out.println("已正常执行...");
            return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println("执行出现异常...");
            e.printStackTrace();
        }


        return null;
    }

}
