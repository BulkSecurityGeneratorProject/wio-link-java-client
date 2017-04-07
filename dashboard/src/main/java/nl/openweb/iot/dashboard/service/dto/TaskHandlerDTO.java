package nl.openweb.iot.dashboard.service.dto;

import javax.validation.constraints.NotNull;

import nl.openweb.iot.dashboard.domain.TaskHandler;
import nl.openweb.iot.dashboard.domain.enumeration.Language;

public class TaskHandlerDTO {

    public TaskHandlerDTO() {
    }

    public TaskHandlerDTO(TaskHandler taskHandler) {
        this.id = taskHandler.getId();
        this.name = taskHandler.getName();
        this.language = taskHandler.getLanguage();
        this.code = taskHandler.getCodeAsString();
    }

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Language language;

    @NotNull
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public TaskHandler toTaskHandler() {
        TaskHandler eventHandler = new TaskHandler();
        eventHandler.setId(id);
        eventHandler.setName(name);
        eventHandler.setLanguage(language);
        eventHandler.setCode(code.getBytes());
        return eventHandler;
    }
}
