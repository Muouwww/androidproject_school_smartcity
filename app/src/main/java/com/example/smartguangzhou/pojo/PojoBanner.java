package com.example.smartguangzhou.pojo;

import java.util.List;

public class PojoBanner {
    private int total;
    private List<RowsDTO> rows;
    private int code;
    private String msg;

    public List<RowsDTO> getRows() {
        return rows;
    }

    public static class RowsDTO{
        private String advTitle;
        private String advImg;
        private String servModule;

        public String getAdvImg() {
            return advImg;
        }
    }
}
