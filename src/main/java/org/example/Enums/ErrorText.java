package org.example.Enums;

public enum ErrorText {
    FILE_READ_ERROR("Ошибка чтения файла"),
    FILTER_PARSE_ERROR("Ошибка парсинга фильтра"),
    FILE_ACCESS_ERROR("Ошибка доступа к файлу");

    private final String text;

    ErrorText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
