package com.sx.common;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sx.sxflow.common.config.SubFreeTable;
import com.sx.sxflow.common.sxflow.config.WorkFlowEnumInterface;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

public enum WorkflowJGEnum implements WorkFlowEnumInterface {
    //    w88("1", 88, 88, null),  //优秀创业服务机构认定申请
    _w(null, 0, null, null);    //默认工作流
    @Schema(description = "初始化类型：2 录入数据多条汇总页面字段初始化， 1 录入数据单条页面字段初始化， null 非录入数据页面字段初始化（普通初始化）")
    private String type;
    @Schema(description = "工作流id")
    private Integer workflow_id;
    @Schema(description = "源数据工作流id（针对新增和编辑页面使用不同的模板。编辑时，此处为新增使用的工作流id）")
    private Integer source_workflow_id;
    private String name;
    @Schema(description = "采集数据列表回显字段交互类")
    private Class<?> flowListVO;
    @Schema(description = "是否使用标准字段")
    private Boolean free_table;
    @Schema(description = "不使用标准字段时，指定表里的主键字段")
    private String table_id;
    @Schema(description = "主表不使用标准字段时，指定表里的表示数据无效的字段")
    private String invalid_field;

    @Schema(description = "自由附表自定义信息")
    private SubFreeTable[] sub_table_free_config;

    @Schema(description = "自由附表自定义信息Map")
    private Map<String, SubFreeTable> sub_table_free_config_map;

    private WorkflowJGEnum(String type, Integer workflow_id, Integer source_workflow_id, Class<?> flowListVO) {
        this.type = type;
        this.workflow_id = workflow_id;
        this.source_workflow_id = source_workflow_id;
        this.flowListVO = flowListVO;
        this.free_table = false;
        this.table_id = null;
        this.invalid_field = null;
        this.sub_table_free_config = null;
    }

    private WorkflowJGEnum(String type, Integer workflow_id, Integer source_workflow_id, Class<?> flowListVO, boolean free_table, String table_id, String invalid_field, SubFreeTable... sub_table_free_config) {
        this.type = type;
        this.workflow_id = workflow_id;
        this.source_workflow_id = source_workflow_id;
        this.flowListVO = flowListVO;
        this.free_table = free_table;
        this.table_id = table_id;
        this.invalid_field = invalid_field;
        this.sub_table_free_config = sub_table_free_config ;
        if (sub_table_free_config == null) {
            return;
        }
        sub_table_free_config_map = new HashMap<>(sub_table_free_config.length);

        for (SubFreeTable freeTable : sub_table_free_config) {
            String tableName = freeTable.getTableName();
            sub_table_free_config_map.put(tableName,freeTable);
        }
    }

    public String getInvalid_field() {
        return invalid_field;
    }

    public void setInvalid_field(String invalid_field) {
        this.invalid_field = invalid_field;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Integer workflow_id) {
        this.workflow_id = workflow_id;
    }

    public Integer getSource_workflow_id() {
        return source_workflow_id;
    }

    public void setSource_workflow_id(Integer source_workflow_id) {
        this.source_workflow_id = source_workflow_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getFlowListVO() {
        return flowListVO;
    }

    public Boolean getFree_table() {
        return free_table;
    }

    public void setFree_table(Boolean free_table) {
        this.free_table = free_table;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public SubFreeTable[] getSub_table_free_config() {
        return sub_table_free_config;
    }

    public void setSub_table_free_config(SubFreeTable[] sub_table_free_config) {
        this.sub_table_free_config = sub_table_free_config;
    }

    public Map<String, SubFreeTable> getSub_table_free_config_map() {
        return sub_table_free_config_map;
    }

    public void setSub_table_free_config_map(Map<String, SubFreeTable> sub_table_free_config_map) {
        this.sub_table_free_config_map = sub_table_free_config_map;
    }

    /**
     * 根据workflowid获取对应dutyvo
     */
    private static final Map<Integer, Class<?>> idToVO = new HashMap<>();

    static {
        for (WorkflowJGEnum workflowEnum : WorkflowJGEnum.values()) {
            idToVO.put(workflowEnum.getWorkflow_id(), workflowEnum.getFlowListVO());
        }
    }

    public static Class getFlowListVOByWorkflowId(int workflow_id) {
        return idToVO.get(workflow_id);
    }

    @JsonValue
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", getType());
        jsonObject.put("workflow_id", getWorkflow_id());
        jsonObject.put("source_workflow_id", getSource_workflow_id());
        jsonObject.put("free_table", getFree_table());
        jsonObject.put("table_id", getTable_id());
        jsonObject.put("invalid_field", getInvalid_field());
        jsonObject.put("sub_table_free_config", getSub_table_free_config());
        jsonObject.put("sub_table_free_config_map", getSub_table_free_config_map());
        return jsonObject;
    }
}
