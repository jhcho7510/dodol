package com.dodol.excel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dodorian")
public class ExcelController {
    @Autowired
    private ExcelDownload download;

    @GetMapping("/excel/download")
    public String excelDownload(HttpServletResponse response) {
        download.excelDownload(response);
        return "";
    }

}
