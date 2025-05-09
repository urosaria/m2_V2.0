package jungjin.estimate.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class InsulationTypeCodeConverter implements AttributeConverter<InsulationTypeCode, String> {
    @Override
    public String convertToDatabaseColumn(InsulationTypeCode code) {
        return code != null ? code.name() : null;
    }

    @Override
    public InsulationTypeCode convertToEntityAttribute(String dbData) {
        return InsulationTypeCode.fromCode(dbData);
    }
}