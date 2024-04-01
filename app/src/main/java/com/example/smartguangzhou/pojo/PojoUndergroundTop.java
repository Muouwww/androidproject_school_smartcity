package com.example.smartguangzhou.pojo;

import java.util.List;

public class PojoUndergroundTop {

    private String msg;
    private Integer code;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private Integer lineId;
        private String lineName;
        private PreStep preStep;
        private NextStep nextStep;
        private String currentName;
        private Integer reachTime;

        public Integer getLineId() {
            return lineId;
        }

        public void setLineId(Integer lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public PreStep getPreStep() {
            return preStep;
        }

        public void setPreStep(PreStep preStep) {
            this.preStep = preStep;
        }

        public NextStep getNextStep() {
            return nextStep;
        }

        public void setNextStep(NextStep nextStep) {
            this.nextStep = nextStep;
        }

        public String getCurrentName() {
            return currentName;
        }

        public void setCurrentName(String currentName) {
            this.currentName = currentName;
        }

        public Integer getReachTime() {
            return reachTime;
        }

        public void setReachTime(Integer reachTime) {
            this.reachTime = reachTime;
        }

        public static class PreStep {
            private String name;
            private List<Lines> lines;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<Lines> getLines() {
                return lines;
            }

            public void setLines(List<Lines> lines) {
                this.lines = lines;
            }

            public static class Lines {
                private Integer lineId;
                private String lineName;

                public Integer getLineId() {
                    return lineId;
                }

                public void setLineId(Integer lineId) {
                    this.lineId = lineId;
                }

                public String getLineName() {
                    return lineName;
                }

                public void setLineName(String lineName) {
                    this.lineName = lineName;
                }
            }
        }

        public static class NextStep {
            private String name;
            private List<Lines> lines;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<Lines> getLines() {
                return lines;
            }

            public void setLines(List<Lines> lines) {
                this.lines = lines;
            }

            public static class Lines {
                private Integer lineId;
                private String lineName;

                public Integer getLineId() {
                    return lineId;
                }

                public void setLineId(Integer lineId) {
                    this.lineId = lineId;
                }

                public String getLineName() {
                    return lineName;
                }

                public void setLineName(String lineName) {
                    this.lineName = lineName;
                }
            }
        }
    }
}
