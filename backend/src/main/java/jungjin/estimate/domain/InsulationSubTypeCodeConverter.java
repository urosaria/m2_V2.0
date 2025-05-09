package jungjin.estimate.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class InsulationSubTypeCodeConverter implements AttributeConverter<InsulationSubTypeCode, String> {
    @Override
    public String convertToDatabaseColumn(InsulationSubTypeCode code) {
        return code != null ? code.name() : null;
    }

    @Override
    public InsulationSubTypeCode convertToEntityAttribute(String dbData) {
        return InsulationSubTypeCode.fromCode(dbData);
    }
}