package jungjin.estimate.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CityCodeConverter implements AttributeConverter<CityCode, String> {

    @Override
    public String convertToDatabaseColumn(CityCode attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public CityCode convertToEntityAttribute(String dbData) {
        return dbData != null ? CityCode.fromCode(dbData) : null;
    }
}