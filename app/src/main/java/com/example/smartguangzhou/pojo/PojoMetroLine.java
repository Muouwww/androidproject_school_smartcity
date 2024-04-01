package com.example.smartguangzhou.pojo;

import java.util.List;

public class PojoMetroLine {

    /**
     * msg
     */
    private String msg;
    /**
     * code
     */
    private Integer code;
    /**
     * data
     */
    private Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id
         */
        private Integer id;
        /**
         * name
         */
        private String name;
        /**
         * first
         */
        private String first;
        /**
         * end
         */
        private String end;
        /**
         * startTime
         */
        private String startTime;
        /**
         * endTime
         */
        private String endTime;
        /**
         * cityId
         */
        private Integer cityId;
        /**
         * stationsNumber
         */
        private Object stationsNumber;
        /**
         * km
         */
        private Integer km;
        /**
         * runStationsName
         */
        private String runStationsName;
        /**
         * metroStepList
         */
        private List<MetroStepList> metroStepList;
        /**
         * remainingTime
         */
        private Integer remainingTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Object getStationsNumber() {
            return stationsNumber;
        }

        public void setStationsNumber(Object stationsNumber) {
            this.stationsNumber = stationsNumber;
        }

        public Integer getKm() {
            return km;
        }

        public void setKm(Integer km) {
            this.km = km;
        }

        public String getRunStationsName() {
            return runStationsName;
        }

        public void setRunStationsName(String runStationsName) {
            this.runStationsName = runStationsName;
        }

        public List<MetroStepList> getMetroStepList() {
            return metroStepList;
        }

        public void setMetroStepList(List<MetroStepList> metroStepList) {
            this.metroStepList = metroStepList;
        }

        public Integer getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(Integer remainingTime) {
            this.remainingTime = remainingTime;
        }

        public static class MetroStepList {
            /**
             * searchValue
             */
            private Object searchValue;
            /**
             * createBy
             */
            private Object createBy;
            /**
             * createTime
             */
            private String createTime;
            /**
             * updateBy
             */
            private Object updateBy;
            /**
             * updateTime
             */
            private String updateTime;
            /**
             * remark
             */
            private Object remark;
            /**
             * params
             */
            private Params params;
            /**
             * id
             */
            private Integer id;
            /**
             * name
             */
            private String name;
            /**
             * seq
             */
            private Integer seq;
            /**
             * lineId
             */
            private Integer lineId;
            /**
             * firstCh
             */
            private String firstCh;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Params getParams() {
                return params;
            }

            public void setParams(Params params) {
                this.params = params;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSeq() {
                return seq;
            }

            public void setSeq(Integer seq) {
                this.seq = seq;
            }

            public Integer getLineId() {
                return lineId;
            }

            public void setLineId(Integer lineId) {
                this.lineId = lineId;
            }

            public String getFirstCh() {
                return firstCh;
            }

            public void setFirstCh(String firstCh) {
                this.firstCh = firstCh;
            }

            public static class Params {
            }
        }
    }
}
